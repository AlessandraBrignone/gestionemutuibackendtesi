package it.bribank.gestioneMutui.repository;

import it.bribank.gestioneMutui.entity.Durata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DurataRepository extends JpaRepository<Durata, Long> {
}
