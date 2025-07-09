package it.bribank.gestioneMutui.repository;

import it.bribank.gestioneMutui.entity.Spread;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpreadRepository extends JpaRepository<Spread, Long> {
}
