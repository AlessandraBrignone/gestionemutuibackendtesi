package it.bribank.gestioneMutui.controller;

import it.bribank.gestioneMutui.dto.DocumentoDto;
import it.bribank.gestioneMutui.entity.*;
import it.bribank.gestioneMutui.repository.*;
import it.bribank.gestioneMutui.service.DocumentoService;

import org.docx4j.Docx4J;
import org.docx4j.model.fields.merge.DataFieldName;
import org.docx4j.model.fields.merge.MailMerger;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.ZoneId;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.docx4j.model.fields.merge.MailMerger.OutputField;

@RestController
@RequestMapping("/api/documenti")
@CrossOrigin(origins = "http://localhost:3000")
public class DocumentoController {

    private static final String TEMPLATE_CLASSPATH = "templates/Bozza_Contratto.docx";

    private final DocumentoRepository documentoRepository;
    private final TipoDocumentoRepository tipoDocumentoRepository;
    private final RichiestaMutuoRepository richiestaMutuoRepository;
    private final AnagraficaRepository anagraficaRepository;
    private final DocumentoService documentoService;
    private final PianoAmmortamentoRepository pianoAmmortamentoRepository;

    private static String formatPercent(Number rate) {
        if (rate == null) return "";
        NumberFormat fmt = NumberFormat.getPercentInstance(Locale.ITALY);
        fmt.setMinimumFractionDigits(2);
        fmt.setMaximumFractionDigits(2);

        if (rate instanceof BigDecimal) {
            BigDecimal bd = (BigDecimal) rate;
            BigDecimal fraction = bd.compareTo(BigDecimal.ONE) >= 0 ? bd.movePointLeft(2) : bd; // 4.2 -> 0.042
            return fmt.format(fraction);
        } else {
            double v = rate.doubleValue();
            double fraction = v >= 1.0 ? v / 100.0 : v; // 4.2 -> 0.042
            return fmt.format(fraction);
        }
    }

    public DocumentoController(DocumentoRepository documentoRepository,
                               TipoDocumentoRepository tipoDocumentoRepository,
                               RichiestaMutuoRepository richiestaMutuoRepository,
                               AnagraficaRepository anagraficaRepository,
                               DocumentoService documentoService,
                               PianoAmmortamentoRepository pianoAmmortamentoRepository) {
        this.documentoRepository = documentoRepository;
        this.tipoDocumentoRepository = tipoDocumentoRepository;
        this.richiestaMutuoRepository = richiestaMutuoRepository;
        this.anagraficaRepository = anagraficaRepository;
        this.documentoService = documentoService;
        this.pianoAmmortamentoRepository = pianoAmmortamentoRepository;
    }

    @GetMapping("/tipi")
    public ResponseEntity<?> getTipiDocumentoAttivi() {
        return ResponseEntity.ok(tipoDocumentoRepository.findByStato(1));
    }

    // Caricamento dei documenti e creazione cartella
    @PostMapping("/upload")
    public ResponseEntity<?> uploadDocumento(
            @RequestParam("file") MultipartFile file,
            @RequestParam("idMutuo") Long idMutuo,
            @RequestParam("idTipoDocumento") Long idTipoDocumento,
            @RequestParam(value = "perizia", required = false, defaultValue = "false") boolean isPerizia) {

        Optional<RichiestaMutuo> richiestaOpt = richiestaMutuoRepository.findById(idMutuo);
        Optional<TipoDocumento> tipoDocOpt = tipoDocumentoRepository.findById(idTipoDocumento);

        if (richiestaOpt.isEmpty() || tipoDocOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Richiesta mutuo o tipo documento non valido");
        }

        RichiestaMutuo richiesta = richiestaOpt.get();

        // Recupera il richiedente da idIntestatario
        Optional<Anagrafica> anagraficaOpt = anagraficaRepository.findById(richiesta.getIdIntestatario());
        if (anagraficaOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Utente non trovato");
        }

        Anagrafica utente = anagraficaOpt.get();
        String nome = utente.getNome().trim().replaceAll("\\s+", "_");
        String cognome = utente.getCognome().trim().replaceAll("\\s+", "_");
        String codiceFiscale = utente.getCodiceFiscale().trim().replaceAll("\\s+", "_");
        String userFolder = codiceFiscale + "_" + cognome + "_" + nome;
        String basePath = System.getProperty("user.dir") + "/uploads/" + userFolder + "/";

        try {
            String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
            String savedFileName = UUID.randomUUID() + "_" + originalFileName;

            File dir = new File(basePath);
            if (!dir.exists()) dir.mkdirs();

            File dest = new File(basePath + savedFileName);
            file.transferTo(dest);

            Documento doc = new Documento();
            doc.setRichiestaMutuo(richiesta);
            doc.setTipoDocumento(tipoDocOpt.get());
            doc.setUrlFile("uploads/" + userFolder + "/" + savedFileName);
            doc.setStato(1);

            documentoRepository.save(doc);

            return ResponseEntity.ok("Documento salvato");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Errore durante il caricamento");
        }
    }

    // Download singolo documento
    @GetMapping("/download")
    public ResponseEntity<Resource> downloadDocumento(@RequestParam Long idMutuo, @RequestParam Long idTipoDocumento) {

        System.out.println("Download richiesta per mutuo ID: " + idMutuo + ", tipo documento ID: " + idTipoDocumento);
        List<Documento> documenti = documentoRepository.findByRichiestaMutuoIdAndTipoDocumentoId(idMutuo, idTipoDocumento);

        System.out.println("Documenti trovati: " + documenti.size());
        documenti.forEach(d -> System.out.println(" -> " + d.getUrlFile()));

        if (documenti.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Documento doc = documenti.get(documenti.size() - 1);

        try {
            Path path = Paths.get(System.getProperty("user.dir")).resolve(doc.getUrlFile());
            Resource resource = new UrlResource(path.toUri());

            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            String fileName = doc.getTipoDocumento().getDescrizioneTipoDocumento().replaceAll("\\s+", "_") + ".pdf";

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .body(resource);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // Elenco documenti caricati
    @GetMapping("/caricati/{idRichiesta}")
    public ResponseEntity<List<DocumentoDto>> getDocumentiCaricati(@PathVariable Long idRichiesta) {
        List<DocumentoDto> documenti = documentoService.getDocumentiCaricati(idRichiesta);
        return ResponseEntity.ok(documenti);
    }

    // Scarica la cartella con i documenti (ZIP)
    @GetMapping("/download-zip/{idRichiesta}")
    public ResponseEntity<Resource> downloadZip(@PathVariable Long idRichiesta) throws IOException {
        List<Documento> documenti = documentoRepository.findByRichiestaMutuoId(idRichiesta);
        if (documenti.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Path zipPath = Files.createTempFile("documenti_richiesta_" + idRichiesta, ".zip");

        try (ZipOutputStream zos = new ZipOutputStream(Files.newOutputStream(zipPath))) {
            for (Documento doc : documenti) {
                Path filePath = Paths.get(System.getProperty("user.dir")).resolve(doc.getUrlFile());
                if (Files.exists(filePath)) {
                    zos.putNextEntry(new ZipEntry(filePath.getFileName().toString()));
                    Files.copy(filePath, zos);
                    zos.closeEntry();
                }
            }
        }

        Resource resource = new UrlResource(zipPath.toUri());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=documenti_richiesta_" + idRichiesta + ".zip")
                .body(resource);
    }

    // Genera il PDF finale da template DOCX
    @GetMapping("/pdf/{idRichiesta}")
    public ResponseEntity<byte[]> generaPdf(@PathVariable Long idRichiesta) {

        //Richiesta
        var richiestaOpt = richiestaMutuoRepository.findById(idRichiesta);
        if (richiestaOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var richiesta = richiestaOpt.get();

        //Anagrafica
        Long idIntestatario = richiesta.getIdIntestatario();
        if (idIntestatario == null) {
            return ResponseEntity.unprocessableEntity()
                    .body(bytes("Id intestatario mancante per richiesta " + idRichiesta));
        }
        var anagraficaOpt = anagraficaRepository.findById(idIntestatario);
        if (anagraficaOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(bytes("Utente non trovato"));
        }
        var anagrafica = anagraficaOpt.get();

        //Formattazione
        var zonaIT = ZoneId.of("Europe/Rome");
        var oggi = LocalDate.now(zonaIT);
        var dataOdierna = oggi.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        var fmtValuta = NumberFormat.getCurrencyInstance(Locale.ITALY);
        String importoStr = richiesta.getImporto() == null ? "" : fmtValuta.format(richiesta.getImporto());
        String interesseStr = formatPercent(richiesta.getInteresseAnnuo());

        //Template DOCX
        try (InputStream is = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(TEMPLATE_CLASSPATH);
             ByteArrayOutputStream pdf = new ByteArrayOutputStream()) {

            if (is == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(bytes("Template non trovato in classpath: " + TEMPLATE_CLASSPATH));
            }

            WordprocessingMLPackage wordML;
            try {
                wordML = WordprocessingMLPackage.load(is);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(500).body(bytes("Errore caricamento DOCX: " + e));
            }

            //MailMerge (MERGEFIELD nel DOCX)
            Map<DataFieldName, String> dati = new HashMap<>();
            dati.put(new DataFieldName("nome"), nn(anagrafica.getNome()));
            dati.put(new DataFieldName("cognome"), nn(anagrafica.getCognome()));
            dati.put(new DataFieldName("codice_fiscale"), nn(anagrafica.getCodiceFiscale()));
            dati.put(new DataFieldName("data_odierna"), dataOdierna);
            dati.put(new DataFieldName("id_mutuo"), nn(richiesta.getId()));
            dati.put(new DataFieldName("importo"), importoStr);
            dati.put(new DataFieldName("tasso_annuo"), interesseStr);
            dati.put(new DataFieldName("cadenza_rata"),
                    richiesta.getCadenzaRata() != null ? richiesta.getCadenzaRata().getDescrizioneTipoRata() : "");
            dati.put(new DataFieldName("durata"),
                    richiesta.getDurata() != null ? richiesta.getDurata().getDescrizioneDurata() : "");

            try {
                org.docx4j.model.fields.merge.MailMerger.setMERGEFIELDInOutput(
                        org.docx4j.model.fields.merge.MailMerger.OutputField.KEEP_MERGEFIELD);
                MailMerger.performMerge(wordML, dati, true);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(500).body(bytes("Errore mail-merge: " + e));
            }

            //Piano di ammortamento
            var rate = pianoAmmortamentoRepository.findByIdMutuoOrderByNumeroRataAsc(idRichiesta);
            rate.sort(java.util.Comparator.comparingInt(r -> safeInt(r.getNumeroRata())));
            if (rate == null || rate.isEmpty()) {
                return ResponseEntity.status(404).body(bytes("Piano di ammortamento assente per richiesta " + idRichiesta));
            }

            List<RigaPiano> piano = new ArrayList<>(rate.size());
            for (var r : rate) {
                int numero = safeInt(r.getNumeroRata());
                BigDecimal qc = nzBD(r.getQuotaCapitale());
                BigDecimal qi = nzBD(r.getQuotaInteressi());
                BigDecimal totale = qc.add(qi);
                BigDecimal residuo = nzBD(r.getCapitaleResiduo());
                piano.add(new RigaPiano(numero, qc, qi, totale, residuo));
            }

            //Inserimento nella tabella del DOCX
            try {
                var mdp = wordML.getMainDocumentPart();
                org.docx4j.wml.Tbl table = findTableByPlaceholder(mdp, "{{n}}");
                if (table != null) {

                    // trova la riga che contiene il placeholder
                    List<org.docx4j.wml.Tr> rows = getAllElements(table, org.docx4j.wml.Tr.class);
                    org.docx4j.wml.Tr templateRow = null;
                    for (org.docx4j.wml.Tr tr : rows) {
                        var texts = getAllElements(tr, org.docx4j.wml.Text.class);
                        for (org.docx4j.wml.Text tx : texts) {
                            if (tx.getValue() != null && tx.getValue().contains("{{n}}")) {
                                templateRow = tr;
                                break;
                            }
                        }
                        if (templateRow != null) break;
                    }

                    if (templateRow != null) {
                        var fmt2 = new java.text.DecimalFormat("#,##0.00");

                        for (RigaPiano r : piano) {
                            org.docx4j.wml.Tr newRow =
                                    (org.docx4j.wml.Tr) org.docx4j.XmlUtils.deepCopy(templateRow);

                            Map<String, String> repl = new HashMap<>();
                            repl.put("{{n}}", String.valueOf(r.numero));
                            repl.put("{{qc}}", fmt2.format(r.quotaCapitale));
                            repl.put("{{qi}}", fmt2.format(r.quotaInteressi));
                            repl.put("{{tot}}", fmt2.format(r.totaleRata));
                            repl.put("{{res}}", fmt2.format(r.residuo));

                            replacePlaceholdersInRow(newRow, repl); // sostituzione in-place sui run
                            table.getContent().add(newRow);
                        }

                        // rimozione riga template
                        table.getContent().remove(templateRow);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(500).body(bytes("Errore nella costruzione tabella piano: " + e));
            }

            //DOCX -> PDF
            try {
                Docx4J.toPDF(wordML, pdf);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(500).body(bytes("Errore conversione in PDF: " + e));
            }

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=riepilogo_richiesta_" + idRichiesta + ".pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdf.toByteArray());

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(bytes("Errore generazione PDF: " + e.getClass().getSimpleName() + " - " + e.getMessage()));
        }
    }

    // --- MODELLO RIGA ---
    static class RigaPiano {
        final int numero;
        final BigDecimal quotaCapitale;
        final BigDecimal quotaInteressi;
        final BigDecimal totaleRata;
        final BigDecimal residuo;

        RigaPiano(int numero,
                  BigDecimal quotaCapitale, BigDecimal quotaInteressi,
                  BigDecimal totaleRata, BigDecimal residuo) {
            this.numero = numero;
            this.quotaCapitale = quotaCapitale;
            this.quotaInteressi = quotaInteressi;
            this.totaleRata = totaleRata;
            this.residuo = residuo;
        }
    }

    // Trova la prima tabella che contenga un certo placeholder
    private static org.docx4j.wml.Tbl findTableByPlaceholder(
            org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart mdp, String placeholder) {
        List<org.docx4j.wml.Tbl> tables = getAllElements(mdp, org.docx4j.wml.Tbl.class);
        for (org.docx4j.wml.Tbl t : tables) {
            List<org.docx4j.wml.Text> texts = getAllElements(t, org.docx4j.wml.Text.class);
            for (org.docx4j.wml.Text tx : texts) {
                if (tx.getValue() != null && tx.getValue().contains(placeholder)) {
                    return t;
                }
            }
        }
        return null;
    }

    // Sostituisce i {{placeholder}} cella-per-cella (niente spostamenti tra colonne)
    private static void replacePlaceholdersInRow(Object row, Map<String, String> replacements) {
        List<org.docx4j.wml.Tc> cells = getAllElements(row, org.docx4j.wml.Tc.class);
        for (org.docx4j.wml.Tc cell : cells) {
            List<org.docx4j.wml.Text> texts = getAllElements(cell, org.docx4j.wml.Text.class);
            if (texts.isEmpty()) continue;

            StringBuilder sb = new StringBuilder();
            for (org.docx4j.wml.Text t : texts) {
                if (t.getValue() != null) sb.append(t.getValue());
            }
            String merged = sb.toString();
            for (var e : replacements.entrySet()) {
                merged = merged.replace(e.getKey(), e.getValue());
            }

            texts.get(0).setValue(merged);
            for (int i = 1; i < texts.size(); i++) texts.get(i).setValue("");
        }
    }

    // Utility ricorsiva per cercare elementi
    private static <T> List<T> getAllElements(Object obj, Class<T> type) {
        List<T> result = new ArrayList<>();
        if (obj == null) return result;

        if (obj instanceof jakarta.xml.bind.JAXBElement<?> jaxb) {
            obj = jaxb.getValue();
        }
        if (type.isInstance(obj)) {
            result.add(type.cast(obj));
        }
        if (obj instanceof org.docx4j.wml.ContentAccessor ca) {
            for (Object child : ca.getContent()) {
                result.addAll(getAllElements(child, type));
            }
        }
        return result;
    }

    // Converte in BigDecimal: accetta BigDecimal, Number o String (IT/EN)
    private static BigDecimal nzBD(Object v) {
        BigDecimal bd = toBD(v);
        return bd != null ? bd : BigDecimal.ZERO;
    }
    private static BigDecimal toBD(Object v) {
        if (v == null) return null;
        if (v instanceof BigDecimal bd) return bd;
        if (v instanceof Number n) {
            try { return new BigDecimal(n.toString()); }
            catch (NumberFormatException e) { return BigDecimal.valueOf(n.doubleValue()); }
        }
        if (v instanceof String s) return parseBD(s);
        return null;
    }
    private static BigDecimal parseBD(String s) {
        if (s == null || s.isBlank()) return null;
        try {
            DecimalFormat df = (DecimalFormat) NumberFormat.getInstance(Locale.ITALY);
            df.setParseBigDecimal(true);
            return (BigDecimal) df.parse(s.trim());          // es. "1.234,56"
        } catch (ParseException e) {
            try {
                // fallback: rimuovi separatori migliaia e usa '.' come decimale
                return new BigDecimal(s.trim().replace(".", "").replace(",", "."));
            } catch (NumberFormatException ex) {
                return null;
            }
        }
    }
    // Converte numero rata se arriva come String/Number
    private static int safeInt(Object v) {
        if (v == null) return 0;
        if (v instanceof Number n) return n.intValue();
        if (v instanceof String s) {
            try { return Integer.parseInt(s.trim()); } catch (NumberFormatException ignored) {}
        }
        return 0;
    }

    // Helpers
    private static String nn(Object v) { return v == null ? "" : String.valueOf(v); }
    private static byte[] bytes(String s) { return s.getBytes(StandardCharsets.UTF_8); }
    private static BigDecimal nz(BigDecimal v) { return v == null ? BigDecimal.ZERO : v; }

}
