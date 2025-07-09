package it.bribank.gestioneMutui.repository;

import it.bribank.gestioneMutui.entity.Documento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Long> {
    List<Documento> findByRichiestaMutuoId(Long idMutuo);

    List<Documento> findByRichiestaMutuoIdAndTipoDocumentoId(Long idMutuo, Long idTipoDocumento);
}
