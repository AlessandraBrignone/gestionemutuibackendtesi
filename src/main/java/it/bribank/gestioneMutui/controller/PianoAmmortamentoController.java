package it.bribank.gestioneMutui.controller;

import it.bribank.gestioneMutui.dto.PianoAmmortamentoDto;
import it.bribank.gestioneMutui.entity.PianoAmmortamento;
import it.bribank.gestioneMutui.entity.RichiestaMutuo;
import it.bribank.gestioneMutui.repository.InserimentoRichiestaMutuoRepository;
import it.bribank.gestioneMutui.service.PianoAmmortamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/genera_piano")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class PianoAmmortamentoController {

    @Autowired
    private InserimentoRichiestaMutuoRepository inserimentoRichiestaMutuoRepository;
    @Autowired
    private PianoAmmortamentoService pianoAmmortamentoService;

    // Endpoint per ottenere tutti i dati
    @GetMapping
    public List<RichiestaMutuo> getAll() {return inserimentoRichiestaMutuoRepository.findAll();}

    @PostMapping
    public List<PianoAmmortamento> generaPiano(@RequestBody PianoAmmortamentoDto pianoAmmortamentoDto) {
        return pianoAmmortamentoService.generaSalva(pianoAmmortamentoDto);
    }
}
