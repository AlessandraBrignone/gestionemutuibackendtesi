package it.bribank.gestioneMutui.controller;

import it.bribank.gestioneMutui.entity.CadenzaRata;
import it.bribank.gestioneMutui.repository.CadenzaRataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cadenza_rata")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class CadenzaRataController {

    @Autowired
    private CadenzaRataRepository cadenzaRataRepository;

    // Endpoint per ottenere tutte le cadenze
    @GetMapping
    public List<CadenzaRata> getAll() {
        return cadenzaRataRepository.findAll(Sort.by(Sort.Direction.ASC, "descrizioneTipoRata"));
    }

}
