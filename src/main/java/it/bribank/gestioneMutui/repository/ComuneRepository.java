package it.bribank.gestioneMutui.repository;

import it.bribank.gestioneMutui.entity.Comune;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ComuneRepository extends JpaRepository<Comune, Long> {
    // Se vuoi aggiungere metodi specifici per filtrare comuni, puoi definirli qui
//    List<Comune> findByProvincia(String provincia);
}
