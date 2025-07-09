package it.bribank.gestioneMutui.controller;

import it.bribank.gestioneMutui.entity.PosizioneLavorativa;
import it.bribank.gestioneMutui.repository.PosizioneLavorativaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posizione_lavorativa")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class PosizioneLavorativaController {

    @Autowired
    private PosizioneLavorativaRepository posizioneLavorativaRepository;

    // Endpoint per ottenere tutte le Posizoni lavorative
    @GetMapping
    public List<PosizioneLavorativa> getAll() {
        return posizioneLavorativaRepository.findAll(Sort.by(Sort.Direction.ASC, "descrizionePosizioneLavorativa"));
    }

}
