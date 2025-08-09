package it.bribank.gestioneMutui.repository;

import it.bribank.gestioneMutui.entity.PianoAmmortamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PianoAmmortamentoRepository extends JpaRepository<PianoAmmortamento, Long> {
    List<PianoAmmortamento> findByIdMutuoOrderByNumeroRataAsc(Long richiestaId);
}
