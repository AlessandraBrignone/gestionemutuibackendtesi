package it.bribank.gestioneMutui.repository;

import it.bribank.gestioneMutui.entity.Anagrafica;
import it.bribank.gestioneMutui.entity.RichiestaMutuo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RichiestaMutuoRepository extends JpaRepository<RichiestaMutuo, Long> {

    @Query(value = "SELECT rm.* FROM richiesta_mutuo rm " +
            "LEFT JOIN anagrafica an1 ON rm.id_intestatario = an1.id " +
            "WHERE (:nome IS NULL OR an1.nome LIKE :nome) " +
            "AND (:cognome IS NULL OR an1.cognome LIKE :cognome) " +
            "AND (:codiceFiscale IS NULL OR an1.codice_fiscale LIKE :codiceFiscale) " +
            "AND (:statoRichiesta IS NULL OR rm.stato_richiesta = :statoRichiesta)" +
            "AND (:idMutuo IS NULL OR rm.id = :idMutuo)",
            nativeQuery = true)
    List<RichiestaMutuo> ricercaMutuo(
            @Param("nome") String nome,
            @Param("cognome") String cognome,
            @Param("codiceFiscale") String codiceFiscale,
            @Param("statoRichiesta") String statoRichiesta,
            @Param("idMutuo") Long idMutuo
    );

    List<RichiestaMutuo> findByStatoRichiesta(String statoRichiesta);
}