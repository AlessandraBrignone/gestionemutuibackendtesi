package it.bribank.gestioneMutui.repository;

import it.bribank.gestioneMutui.entity.Anagrafica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;

@Repository
public interface AnagraficaRepository extends JpaRepository<Anagrafica, Long> {

    @Query("SELECT a FROM Anagrafica a WHERE " +
            "(:nome IS NULL OR a.nome LIKE :nome) AND " +
            "(:cognome IS NULL OR a.cognome LIKE :cognome) AND " +
            "(:codiceFiscale IS NULL OR a.codiceFiscale LIKE :codiceFiscale) AND " +
            "(:dataNascita IS NULL OR a.dataNascita = :dataNascita)")
    List<Anagrafica> ricercaAnagrafica(
            @Param("nome") String nome,
            @Param("cognome") String cognome,
            @Param("codiceFiscale") String codiceFiscale,
            @Param("dataNascita") LocalDate dataNascita
    );

    // Trova un'anagrafica per codice fiscale
    Optional<Anagrafica> findByCodiceFiscale(String codiceFiscale);

    //Controlla se già non ci sia un'anagrafica uguale
    Optional<Anagrafica> findByNomeAndCognomeAndCodiceFiscale(String nome, String cognome, String codiceFiscale);

    List<Anagrafica> findByStato(int stato);
}
