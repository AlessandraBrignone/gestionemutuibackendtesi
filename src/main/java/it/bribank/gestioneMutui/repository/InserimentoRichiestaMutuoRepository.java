package it.bribank.gestioneMutui.repository;

import it.bribank.gestioneMutui.entity.RichiestaMutuo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface InserimentoRichiestaMutuoRepository extends JpaRepository<RichiestaMutuo, Long> {

    //Query per recuperare le descrizsioni
    @Query("""
        SELECT r FROM RichiestaMutuo r
        JOIN FETCH r.durata
        JOIN FETCH r.cadenzaRata
        JOIN FETCH r.tipoMutuo
        WHERE r.id = :id
    """)
    Optional<RichiestaMutuo> findDettaglioById(@Param("id") Long id);
}
