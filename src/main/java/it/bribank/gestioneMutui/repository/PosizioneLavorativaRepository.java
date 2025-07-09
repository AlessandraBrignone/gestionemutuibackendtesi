package it.bribank.gestioneMutui.repository;

import it.bribank.gestioneMutui.entity.PosizioneLavorativa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PosizioneLavorativaRepository extends JpaRepository<PosizioneLavorativa, Long> {
}
