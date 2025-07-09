package it.bribank.gestioneMutui.controller;

import it.bribank.gestioneMutui.dto.AnagraficaDto;
import it.bribank.gestioneMutui.entity.Anagrafica;
import it.bribank.gestioneMutui.entity.Comune;
import it.bribank.gestioneMutui.repository.AnagraficaRepository;
import it.bribank.gestioneMutui.repository.ComuneRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/anagrafica")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class AnagraficaController {

    @Autowired
    private AnagraficaRepository anagraficaRepository;

    @Autowired
    private ComuneRepository comuneRepository;

    // Restituisce tutti gli elementi con stato a 1
    @GetMapping
    public List<Anagrafica> getAll() {
        return anagraficaRepository.findByStato(1);
    }

    // Crea un nuovo record usando il DTO di input
    @PostMapping("/salva")
    public ResponseEntity<?> create(@RequestBody AnagraficaDto anagraficaDto) {

        // Controllo esistenza codice fiscale
        Optional<Anagrafica> existing = anagraficaRepository.findByCodiceFiscale(
                anagraficaDto.getCodiceFiscale());

        if (existing.isPresent()) {
            // Utente già esistente: ritorna errore 400 con messaggio
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Codice fiscale già presente nel sistema."));
        }

        Anagrafica anagrafica = new Anagrafica();

        setCampi(anagraficaDto, anagrafica);

        //Settaggio campo stato a 1
        anagrafica.setStato(1);

        Anagrafica saved = anagraficaRepository.save(anagrafica);
        return ResponseEntity.ok(saved);
    }

    // Aggiorna un record esistente tramite id
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateById(@PathVariable Long id, @RequestBody AnagraficaDto anagraficaDto) {
        Optional<Anagrafica> existingOpt = anagraficaRepository.findById(id);
        if (existingOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Anagrafica a = existingOpt.get();

        setCampi(anagraficaDto, a);

        Anagrafica saved = anagraficaRepository.save(a);
        return ResponseEntity.ok(saved);
    }

    // Elimina un record tramite id
    @DeleteMapping("/elimina/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        Optional<Anagrafica> found = anagraficaRepository.findById(id);
        if (found.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        //Cancellazione logica
        Anagrafica anagrafica = found.get();
        anagrafica.setStato(0);
        anagraficaRepository.save(anagrafica);

        return ResponseEntity.ok().build();
    }

    // Ricerca per nome, cognome, codice fiscale
    @PostMapping("/ricerca")
    public ResponseEntity<Map<String, Object>> ricercaAnagrafica(@RequestBody Map<String, String> params) {
        String nome = params.get("nomeRicerca");
        String cognome = params.get("cognomeRicerca");
        String codiceFiscale = params.get("codiceFiscaleRicerca");
        String dataNascitaRicerca = params.get("dataNascitaRicerca");
        LocalDate dataNascita = null;
        if (dataNascitaRicerca != null && !dataNascitaRicerca.isEmpty()) {
            dataNascita = LocalDate.parse(dataNascitaRicerca); // formato yyyy-MM-dd
        }

        List<Anagrafica> risultati = anagraficaRepository.ricercaAnagrafica(nome, cognome, codiceFiscale, dataNascita);

        Map<String, Object> response = new HashMap<>();
        if (!risultati.isEmpty()) {
            response.put("found", true);
            response.put("data", risultati);
        } else {
            response.put("found", false);
        }

        return ResponseEntity.ok(response);
    }

    //In caso di utente cancellato possibilità di ripristinarlo
    @PutMapping("/ripristina/{id}")
    public ResponseEntity<?> ripristinaAnagrafica(@PathVariable Long id) {
        Optional<Anagrafica> found = anagraficaRepository.findById(id);
        if (found.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Anagrafica anagrafica = found.get();
        anagrafica.setStato(1);
        anagraficaRepository.save(anagrafica);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Optional<Anagrafica> anagrafica = anagraficaRepository.findById(id);
        return anagrafica.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //Popolazione dei campi
    private void setCampi(AnagraficaDto anagraficaDto, Anagrafica anagrafica) {
        anagrafica.setNome(anagraficaDto.getNome());
        anagrafica.setCognome(anagraficaDto.getCognome());
        anagrafica.setCodiceFiscale(anagraficaDto.getCodiceFiscale());
        anagrafica.setSesso(anagraficaDto.getSesso());
        if (anagraficaDto.getDataNascita() != null &&
                !anagraficaDto.getDataNascita().isEmpty()) {
            anagrafica.setDataNascita(LocalDate.parse(anagraficaDto.getDataNascita()));
        }
        if (anagraficaDto.getComuneNascita() != null) {
            Comune c = comuneRepository.findById(anagraficaDto.getComuneNascita()).orElse(null);
            anagrafica.setComuneNascita(c);
        }
        anagrafica.setEmail(anagraficaDto.getEmail());
        anagrafica.setTelefono(anagraficaDto.getTelefono());
        anagrafica.setCellulare(anagraficaDto.getCellulare());
        anagrafica.setIndirizzoResidenza(anagraficaDto.getIndirizzoResidenza());
        anagrafica.setCapResidenza(anagraficaDto.getCapResidenza());
        anagrafica.setProvinciaResidenza(anagraficaDto.getProvinciaResidenza());
        if (anagraficaDto.getComuneResidenza() != null) {
            Comune c = comuneRepository.findById(anagraficaDto.getComuneResidenza()).orElse(null);
            anagrafica.setComuneResidenza(c);
        }

        //anagrafica.setDomUgualeRes(anagraficaDto.getDomUgualeRes());

        if(anagraficaDto.getDomUgualeRes() != null &&
                (anagraficaDto.getDomUgualeRes().equalsIgnoreCase("SI") ||
                anagraficaDto.getDomUgualeRes().equalsIgnoreCase("TRUE"))
        ){
            anagrafica.setIndirizzoDomicilio(anagraficaDto.getIndirizzoResidenza());
            anagrafica.setProvinciaDomicilio(anagraficaDto.getProvinciaResidenza());
            anagrafica.setCapDomicilio(anagraficaDto.getCapResidenza());
            Comune c = comuneRepository.findById(anagraficaDto.getComuneResidenza()).orElse(null);
            anagrafica.setComuneDomicilio(c);

        } else {
            anagrafica.setIndirizzoDomicilio(anagraficaDto.getIndirizzoDomicilio());
            anagrafica.setProvinciaDomicilio(anagraficaDto.getProvinciaDomicilio());
            anagrafica.setCapDomicilio(anagraficaDto.getCapDomicilio());

            if (anagraficaDto.getComuneDomicilio() != null) {
                Comune c = comuneRepository.findById(anagraficaDto.getComuneDomicilio()).orElse(null);
                anagrafica.setComuneDomicilio(c);
            }
        }
    }

}

