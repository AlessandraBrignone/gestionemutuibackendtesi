package it.bribank.gestioneMutui.controller;

import it.bribank.gestioneMutui.entity.TipoMutuo;
import it.bribank.gestioneMutui.repository.TipoMutuoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipo_mutuo")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class TipoMutuoController {

    @Autowired
    private TipoMutuoRepository tipoMutuoRepository;

    // Endpoint per ottenere tutte le durate
    @GetMapping
    public List<TipoMutuo> getAll() {
        return tipoMutuoRepository.findAll(Sort.by(Sort.Direction.ASC, "descrizioneTipoMutuo"));
    }

}
