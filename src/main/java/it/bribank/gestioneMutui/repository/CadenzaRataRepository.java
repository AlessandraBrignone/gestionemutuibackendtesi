package it.bribank.gestioneMutui.repository;

import it.bribank.gestioneMutui.entity.CadenzaRata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CadenzaRataRepository extends JpaRepository<CadenzaRata, Long> {
}
