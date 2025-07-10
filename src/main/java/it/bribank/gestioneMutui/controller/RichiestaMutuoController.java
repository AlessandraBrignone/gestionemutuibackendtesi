package it.bribank.gestioneMutui.controller;

import it.bribank.gestioneMutui.dto.RichiestaMutuoDto;
import it.bribank.gestioneMutui.dto.RichiestaMutuoResponseDto;
import it.bribank.gestioneMutui.entity.*;
import it.bribank.gestioneMutui.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/richiesta_mutuo")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})

public class RichiestaMutuoController {

    @Autowired
    private RichiestaMutuoRepository richiestaMutuoRepository;
    @Autowired
    private DurataRepository durataRepository;
    @Autowired
    private CadenzaRataRepository cadenzaRataRepository;
    @Autowired
    private TipoMutuoRepository tipoMutuoRepository;
    @Autowired
    private PosizioneLavorativaRepository posizioneLavorativaRepository;
    @Autowired
    private SpreadRepository spreadRepository;
    @Autowired
    private AnagraficaRepository anagraficaRepository;

    // Restituisce tutti gli elementi
    @GetMapping
    public List<RichiestaMutuo> getAll() {
        return richiestaMutuoRepository.findAll();
    }

    //Nuova richiesta di mutuo
    @PostMapping("/salva")
    public ResponseEntity<?> create(@RequestBody RichiestaMutuoDto richiestaMutuoDto) {
        RichiestaMutuo richiestaMutuo = new RichiestaMutuo();
        try {
            setCampi(richiestaMutuoDto, richiestaMutuo);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", ex.getMessage()));
        }
        //Settiamo il campo Stato=1 quando facciamo l'inserimento
        richiestaMutuo.setStato(1);

        RichiestaMutuo saved = richiestaMutuoRepository.save(richiestaMutuo);
        RichiestaMutuoResponseDto dto = convertToDto(saved);
        return ResponseEntity.ok(dto);
    }

    //Modifica di una richiesta esistente.
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody RichiestaMutuoDto dto) {
        Optional<RichiestaMutuo> existingOpt = richiestaMutuoRepository.findById(id);
        if (existingOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        RichiestaMutuo entity = existingOpt.get();
        try {
            setCampi(dto, entity);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", ex.getMessage()));
        }

        RichiestaMutuo saved = richiestaMutuoRepository.save(entity);
        return ResponseEntity.ok(saved);
    }

    //Eliminazione di una richiesta di mutuo
    @DeleteMapping("/elimina/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return richiestaMutuoRepository.findById(id)
            .map(richiesta -> {
                richiesta.setStato(0);                 // aggiungi se non esiste
                richiesta.setStatoRichiesta("ELIMINATA");  // opzionale
                richiestaMutuoRepository.save(richiesta);
                return ResponseEntity.ok().build();
            })
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    //Invio richiesta mutuo
    @PostMapping("/invia")
    public ResponseEntity<?> inviaRichiesta(@RequestBody Map<String, Long> payload) {
        Long richiestaId = payload.get("richiestaId");

        if (richiestaId == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "ID richiesta mancante"));
        }

        Optional<RichiestaMutuo> optional = richiestaMutuoRepository.findById(richiestaId);

        if (optional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Richiesta mutuo non trovata"));
        }

        RichiestaMutuo richiesta = optional.get();
        richiesta.setStatoRichiesta("INVIATA");

        richiestaMutuoRepository.save(richiesta);

        return ResponseEntity.ok(Map.of(
                "message", "Richiesta inviata con successo",
                "idRichiesta", richiesta.getId(),
                "stato", richiesta.getStatoRichiesta()
        ));
    }

    //Recupero richieste inviate
    @PostMapping("/ricerca")
    public ResponseEntity<?> getSearch(@RequestBody Map<String, String> params) {
        String nome = params.get("nomeRicercaRichiedente");
        String cognome = params.get("cognomeRicercaRichiedente");
        String codiceFiscale = params.get("codiceFiscaleRicercaRichiedente");
        String statoRichiesta = params.get("statoRichiesta");

        Long idMutuo = null;
        try {
            String idMutuoStr = params.get("idMutuo");
            if (idMutuoStr != null && !idMutuoStr.trim().isEmpty()) {
                idMutuo = Long.valueOf(idMutuoStr);
            }
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(Map.of("error", "ID mutuo non valido"));
        }

        List<RichiestaMutuo> risultati = richiestaMutuoRepository.ricercaMutuo(nome, cognome, codiceFiscale, statoRichiesta, idMutuo);

        Map<String, Object> response = new HashMap<>();
        if (!risultati.isEmpty()) {
            List<RichiestaMutuoResponseDto> risultatiDto = risultati.stream().map(richiesta -> {
                RichiestaMutuoResponseDto dto = convertToDto(richiesta);
                if (richiesta.getIdIntestatario() != null) {
                    anagraficaRepository.findById(richiesta.getIdIntestatario()).ifPresent(anag -> {
                        dto.setNome(anag.getNome());
                        dto.setCognome(anag.getCognome());
                        dto.setCodiceFiscale(anag.getCodiceFiscale());
                    });
                }
                return dto;
            }).collect(Collectors.toList());
            response.put("found", true);
            response.put("data", risultatiDto);
        } else {
            response.put("found", false);
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/dettagli/{id}")
    public ResponseEntity<?> getDettagliById(@PathVariable Long id) {
        Optional<RichiestaMutuo> richiestaOpt = richiestaMutuoRepository.findById(id);

        if (richiestaOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Richiesta mutuo non trovata"));
        }

        RichiestaMutuo richiesta = richiestaOpt.get();
        RichiestaMutuoResponseDto dto = convertToDto(richiesta);

        // Recupera anche anagrafiche collegate
        if (richiesta.getIdIntestatario() != null) {
            anagraficaRepository.findById(richiesta.getIdIntestatario()).ifPresent(anag -> {
                dto.setNome(anag.getNome());
                dto.setCognome(anag.getCognome());
                dto.setCodiceFiscale(anag.getCodiceFiscale());
            });
        }

        if (richiesta.getIdCointestatario() != null) {
            anagraficaRepository.findById(richiesta.getIdCointestatario()).ifPresent(anag -> {
                dto.setNomeCointestatario(anag.getNome());
                dto.setCognomeCointestatario(anag.getCognome());
                dto.setCodiceFiscaleCointestatario(anag.getCodiceFiscale());
            });
        }

        if (richiesta.getIdGarante() != null) {
            anagraficaRepository.findById(richiesta.getIdGarante()).ifPresent(anag -> {
                dto.setNomeGarante(anag.getNome());
                dto.setCognomeGarante(anag.getCognome());
                dto.setCodiceFiscaleGarante(anag.getCodiceFiscale());
            });
        }

        return ResponseEntity.ok(dto);
    }

    //Invio richiesta da parte del gestore
    @PutMapping("/richiesta/{richiestaId}")
    public ResponseEntity<?> inviaARichiestaValidazione(@PathVariable Long richiestaId) {
        Optional<RichiestaMutuo> opt = richiestaMutuoRepository.findById(richiestaId);
        if (opt.isEmpty()) {
            return ResponseEntity.badRequest().body("Richiesta non trovata");
        }

        RichiestaMutuo richiesta = opt.get();
        richiesta.setStatoRichiesta("VALIDAZIONE");
        richiesta.setNoteRifiuto(null); // pulizia eventuale
        richiestaMutuoRepository.save(richiesta);

        return ResponseEntity.ok("Richiesta inviata in validazione.");
    }

    //Rifiuto della richiesta da parte del gestore
    @PostMapping("/rifiuto")
    public ResponseEntity<?> rifiutaRichiesta(@RequestParam Long richiestaId, String noteRifiuto, @RequestBody RichiestaMutuoDto richiestaMutuoDto ) {
        Optional<RichiestaMutuo> opt = richiestaMutuoRepository.findById(richiestaId);
        if (opt.isEmpty()) {
            return ResponseEntity.badRequest().body("Richiesta non trovata");
        }

        RichiestaMutuo richiesta = opt.get();
        richiesta.setStatoRichiesta("RIFIUTATA");
        richiesta.setNoteRifiuto(noteRifiuto);
        richiestaMutuoRepository.save(richiesta);

        return ResponseEntity.ok("Richiesta rifiutata.");
    }

    @PutMapping("/approva/{richiestaId}")
    public ResponseEntity<?> approvaRichiesta(@PathVariable Long richiestaId) {
        Optional<RichiestaMutuo> opt = richiestaMutuoRepository.findById(richiestaId);
        if (opt.isEmpty()) {
            return ResponseEntity.badRequest().body("Richiesta non trovata");
        }

        RichiestaMutuo richiesta = opt.get();
        richiesta.setStatoRichiesta("VALIDATO");
        richiesta.setNoteRifiuto(null);
        richiestaMutuoRepository.save(richiesta);

        return ResponseEntity.ok("Richiesta approvata.");
    }

    //Funzione per il recupero dello stato delle richieste
//    @PostMapping("/stato_richiesta")
//    public ResponseEntity<?> getSearchStatoRichiesta() {
////        String statoRichiesta = params.get("stato_richiesta");
//
//        List<RichiestaMutuo> risultati = richiestaMutuoRepository.findByStatoRichiesta(statoRichiesta);
//
//        Map<String, Object> response = new HashMap<>();
//        if (!risultati.isEmpty()) {
//            List<RichiestaMutuoResponseDto> risultatiDto = risultati.stream().map(richiesta -> {
//                RichiestaMutuoResponseDto dto = convertToDto(richiesta);
//                if (richiesta.getIdIntestatario() != null) {
//                    anagraficaRepository.findById(richiesta.getIdIntestatario()).ifPresent(anag -> {
//                        dto.setNome(anag.getNome());
//                        dto.setCognome(anag.getCognome());
//                        dto.setCodiceFiscale(anag.getCodiceFiscale());
//                    });
//                }
//                return dto;
//            }).collect(Collectors.toList());
//            response.put("found", true);
//            response.put("data", risultatiDto);
//        } else {
//            response.put("found", false);
//        }
//
//        return ResponseEntity.ok(response);
//
//    }

    private RichiestaMutuoResponseDto convertToDto(RichiestaMutuo richiesta) {
        RichiestaMutuoResponseDto responseDto = new RichiestaMutuoResponseDto();
        responseDto.setId(richiesta.getId());
        responseDto.setIdIntestatario(richiesta.getIdIntestatario());
        responseDto.setIdCointestatario(richiesta.getIdCointestatario());
        responseDto.setIdGarante(richiesta.getIdGarante());
        responseDto.setImporto(richiesta.getImporto());
        responseDto.setDurata(richiesta.getDurata() != null ? richiesta.getDurata().getId() : null);
        responseDto.setCadenzaRata(richiesta.getCadenzaRata() != null ? richiesta.getCadenzaRata().getId() : null);
        responseDto.setDataRiscossione(richiesta.getDataRiscossione());
        responseDto.setTipoMutuo(richiesta.getTipoMutuo() != null ? richiesta.getTipoMutuo().getId() : null);
        responseDto.setInteresseAnnuo(richiesta.getInteresseAnnuo());
        responseDto.setSpread(richiesta.getTipoMutuo().getSpread() != null ? richiesta.getTipoMutuo().getSpread().getDescrizioneSpread() : null);
        responseDto.setDataRiscossione(richiesta.getDataRiscossione());
        responseDto.setStatoRichiesta(richiesta.getStatoRichiesta());
        responseDto.setValoreImmobile(richiesta.getValoreImmobile());
        responseDto.setRedditoFamiliareIc(richiesta.getRedditoFamiliareIc());
        responseDto.setPosizioneLavorativaIn(
                richiesta.getPosizioneLavorativaIn() != null
                        ? richiesta.getPosizioneLavorativaIn().getId()
                        : null);

        responseDto.setPosizioneLavorativaCo(
                richiesta.getPosizioneLavorativaCo() != null
                        ? richiesta.getPosizioneLavorativaCo().getId()
                        : null);

        responseDto.setComponentiNucleoFamiliareIc(richiesta.getComponentiNucleoFamiliareIc());
        responseDto.setValoreBeniImmobiliIc(richiesta.getValoreBeniImmobiliIc());
        responseDto.setValorePartecipazioneIc(richiesta.getValorePartecipazioneIc());
        responseDto.setUltimoIseeIc(richiesta.getUltimoIseeIc());
        responseDto.setRedditoFamiliareGa(richiesta.getRedditoFamiliareGa());
        responseDto.setPosizioneLavorativaGa(
                richiesta.getPosizioneLavorativaGa() != null
                        ? richiesta.getPosizioneLavorativaGa().getId()
                        : null);
        responseDto.setComponentiNucleoFamiliareGa(richiesta.getComponentiNucleoFamiliareGa());
        responseDto.setValoreBeniImmobiliGa(richiesta.getValoreBeniImmobiliGa());
        responseDto.setValorePartecipazioneGa(richiesta.getValorePartecipazioneGa());
        responseDto.setUltimoIseeGa(richiesta.getUltimoIseeGa());
        responseDto.setNoteRifiuto(richiesta.getNoteRifiuto());
        return responseDto;
    }

    private void setCampi(RichiestaMutuoDto richiestaMutuoDto, RichiestaMutuo richiestaMutuo) {
        // --- Richiesta mutuo ---
        richiestaMutuo.setIdIntestatario(richiestaMutuoDto.getIdIntestatario());
        richiestaMutuo.setIdCointestatario(richiestaMutuoDto.getIdCointestatario());
        richiestaMutuo.setIdGarante(richiestaMutuoDto.getIdGarante());
        if (richiestaMutuoDto.getImporto() < 50000) {
            throw new IllegalArgumentException("Importo troppo basso");
        } else
            richiestaMutuo.setImporto(richiestaMutuoDto.getImporto());
        if (richiestaMutuoDto.getDurata() != null) {
            Durata durata = durataRepository.findById(richiestaMutuoDto.getDurata()).orElse(null);
            richiestaMutuo.setDurata(durata);
        }
        if (richiestaMutuoDto.getCadenzaRata() != null) {
            CadenzaRata cadenzaRata = cadenzaRataRepository.findById(richiestaMutuoDto.getCadenzaRata()).orElse(null);
            richiestaMutuo.setCadenzaRata(cadenzaRata);
        }
        richiestaMutuo.setDataRiscossione(richiestaMutuoDto.getDataRiscossione());
        if (richiestaMutuoDto.getTipoMutuo() != null) {
            TipoMutuo tipoMutuo = tipoMutuoRepository.findById(richiestaMutuoDto.getTipoMutuo()).orElse(null);
            richiestaMutuo.setTipoMutuo(tipoMutuo);
        }
        richiestaMutuo.setInteresseAnnuo(richiestaMutuoDto.getInteresseAnnuo());
//        if(richiestaMutuoDto.getSpread() != null) {
//            Spread spread = spreadRepository.findById(richiestaMutuoDto.getSpread()).orElse(null);
//            richiestaMutuo.setSpread(spread);
//        }
        richiestaMutuo.setStatoRichiesta(richiestaMutuoDto.getStatoRichiesta());
        richiestaMutuo.setValoreImmobile(richiestaMutuoDto.getValoreImmobile());

        // --- Dati finanziari ---
        // --- Intestatario/cointestatario ---
        richiestaMutuo.setRedditoFamiliareIc(richiestaMutuoDto.getRedditoFamiliareIc());
        if (richiestaMutuoDto.getPosizioneLavorativaIn() != null) {
            PosizioneLavorativa posizioneLavorativaIn = posizioneLavorativaRepository.findById(
                    richiestaMutuoDto.getPosizioneLavorativaIn()).orElse(null);
            richiestaMutuo.setPosizioneLavorativaIn(posizioneLavorativaIn);
        }
        if (richiestaMutuoDto.getPosizioneLavorativaCo() != null) {
            PosizioneLavorativa posizioneLavorativaCo = posizioneLavorativaRepository.findById(
                    richiestaMutuoDto.getPosizioneLavorativaCo()).orElse(null);
            richiestaMutuo.setPosizioneLavorativaCo(posizioneLavorativaCo);
        }
        richiestaMutuo.setComponentiNucleoFamiliareIc(richiestaMutuoDto.getComponentiNucleoFamiliareIc());
        richiestaMutuo.setValoreBeniImmobiliIc(richiestaMutuoDto.getValoreBeniImmobiliIc());
        richiestaMutuo.setValorePartecipazioneIc(richiestaMutuoDto.getValorePartecipazioneIc());
        richiestaMutuo.setUltimoIseeIc(richiestaMutuoDto.getUltimoIseeIc());
        // --- Dati finanziari ---
        // --- Garante ---
        richiestaMutuo.setRedditoFamiliareGa(richiestaMutuoDto.getRedditoFamiliareGa());
        if (richiestaMutuoDto.getPosizioneLavorativaGa() != null) {
            PosizioneLavorativa posizioneLavorativaGa = posizioneLavorativaRepository.findById(
                    richiestaMutuoDto.getPosizioneLavorativaGa()).orElse(null);
            richiestaMutuo.setPosizioneLavorativaGa(posizioneLavorativaGa);
        }
        richiestaMutuo.setComponentiNucleoFamiliareGa(richiestaMutuoDto.getComponentiNucleoFamiliareGa());
        richiestaMutuo.setValoreBeniImmobiliGa(richiestaMutuoDto.getValoreBeniImmobiliGa());
        richiestaMutuo.setValorePartecipazioneGa(richiestaMutuoDto.getValorePartecipazioneGa());
        richiestaMutuo.setUltimoIseeGa(richiestaMutuoDto.getUltimoIseeGa());
    }

}