package it.bribank.gestioneMutui.controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import it.bribank.gestioneMutui.dto.DocumentoDto;
import it.bribank.gestioneMutui.entity.Documento;
import it.bribank.gestioneMutui.entity.RichiestaMutuo;
import it.bribank.gestioneMutui.entity.TipoDocumento;
import it.bribank.gestioneMutui.entity.Anagrafica;
import it.bribank.gestioneMutui.repository.DocumentoRepository;
import it.bribank.gestioneMutui.repository.RichiestaMutuoRepository;
import it.bribank.gestioneMutui.repository.TipoDocumentoRepository;
import it.bribank.gestioneMutui.repository.AnagraficaRepository;
import it.bribank.gestioneMutui.service.DocumentoService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
@RequestMapping("/api/documenti")
@CrossOrigin(origins = "http://localhost:3000")
public class DocumentoController {

    @Autowired
    private DocumentoRepository documentoRepository;

    @Autowired
    private TipoDocumentoRepository tipoDocumentoRepository;

    @Autowired
    private RichiestaMutuoRepository richiestaMutuoRepository;

    @Autowired
    private AnagraficaRepository anagraficaRepository;

    @Autowired
    private DocumentoService documentoService;

    @GetMapping("/tipi")
    public ResponseEntity<?> getTipiDocumentoAttivi() {
        return ResponseEntity.ok(tipoDocumentoRepository.findByStato(1));
    }

    //Caricamento dei documenti e creazione cartella
    @PostMapping("/upload")
    public ResponseEntity<?> uploadDocumento(
            @RequestParam("file") MultipartFile file,
            @RequestParam("idMutuo") Long idMutuo,
            @RequestParam("idTipoDocumento") Long idTipoDocumento,
             @RequestParam(value = "perizia", required = false, defaultValue = "false") boolean isPerizia)
        {

        Optional<RichiestaMutuo> richiestaOpt = richiestaMutuoRepository.findById(idMutuo);
        Optional<TipoDocumento> tipoDocOpt = tipoDocumentoRepository.findById(idTipoDocumento);

        if (richiestaOpt.isEmpty() || tipoDocOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Richiesta mutuo o tipo documento non valido");
        }

        RichiestaMutuo richiesta = richiestaOpt.get();

        // Recupera il richiedente da idUtente
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

    //Download documenti
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

    //Documenti caricati
    @GetMapping("/caricati/{idRichiesta}")
    public ResponseEntity<List<DocumentoDto>> getDocumentiCaricati(@PathVariable Long idRichiesta) {
        List<DocumentoDto> documenti = documentoService.getDocumentiCaricati(idRichiesta);
        return ResponseEntity.ok(documenti);
    }

    //Funzione per scaricare la cartella con i documenti
    @GetMapping("/download-zip/{idRichiesta}")
    public ResponseEntity<Resource> downloadZip(@PathVariable Long idRichiesta) throws IOException {
        List<Documento> documenti = documentoRepository.findByRichiestaMutuoId(idRichiesta);
        if (documenti.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Path zipPath = Files.createTempFile("documenti_richiesta_" + idRichiesta, ".zip");

        try (ZipOutputStream zos = new ZipOutputStream(Files.newOutputStream(zipPath))) {
            for (Documento doc : documenti) {
                Path filePath = Paths.get(doc.getUrlFile());
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

    //Funzione per scaricare il pdf finale
    @GetMapping("/pdf/{idRichiesta}")
    public ResponseEntity<byte[]> generaPdf(@PathVariable Long idRichiesta) throws IOException, DocumentException {
        Optional<RichiestaMutuo> opt = richiestaMutuoRepository.findById(idRichiesta);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        RichiestaMutuo richiesta = opt.get();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, out);
        document.open();

        document.add(new Paragraph("Riepilogo Richiesta Mutuo"));
        document.add(new Paragraph("ID Richiesta: " + richiesta.getId()));
        document.add(new Paragraph("Stato: " + richiesta.getStatoRichiesta()));
        document.add(new Paragraph("Importo richiesto: " + richiesta.getImporto() + " €"));
        // Aggiungi qui tutti i campi necessari

        document.close();

        byte[] pdfBytes = out.toByteArray();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=riepilogo_richiesta_" + idRichiesta + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }

}
