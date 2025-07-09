package it.bribank.gestioneMutui.repository;

import it.bribank.gestioneMutui.entity.TipoDocumento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipoDocumentoRepository extends JpaRepository<TipoDocumento, Long> {
    List<TipoDocumento> findByStato(int stato);
}