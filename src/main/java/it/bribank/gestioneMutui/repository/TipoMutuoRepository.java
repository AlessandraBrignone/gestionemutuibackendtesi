package it.bribank.gestioneMutui.repository;

import it.bribank.gestioneMutui.entity.TipoMutuo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoMutuoRepository extends JpaRepository<TipoMutuo, Long> {
}
