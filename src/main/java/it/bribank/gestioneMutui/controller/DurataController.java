package it.bribank.gestioneMutui.controller;

import it.bribank.gestioneMutui.entity.Durata;
import it.bribank.gestioneMutui.repository.DurataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/durata")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class DurataController {

    @Autowired
    private DurataRepository durataRepository;

    // Endpoint per ottenere tutte le durate
    @GetMapping
    public List<Durata> getAll() {
        return durataRepository.findAll(Sort.by(Sort.Direction.ASC, "descrizioneDurata"));
    }

}
