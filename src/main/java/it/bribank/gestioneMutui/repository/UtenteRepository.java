package it.bribank.gestioneMutui.repository;

import it.bribank.gestioneMutui.entity.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UtenteRepository extends JpaRepository<Utente, Long> {
    Optional<Utente> findByEmail(String email);
}