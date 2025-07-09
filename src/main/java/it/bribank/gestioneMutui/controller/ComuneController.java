package it.bribank.gestioneMutui.controller;

import it.bribank.gestioneMutui.entity.Comune;
import it.bribank.gestioneMutui.repository.ComuneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comuni")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class ComuneController {

    @Autowired
    private ComuneRepository comuneRepository;

    // Endpoint per ottenere tutti i comuni ordinati per descrizione
    @GetMapping
    public List<Comune> getAll() {
        return comuneRepository.findAll(Sort.by(Sort.Direction.ASC, "descrizioneComune"));
    }

}

