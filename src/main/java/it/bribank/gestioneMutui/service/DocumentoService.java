package it.bribank.gestioneMutui.service;

import it.bribank.gestioneMutui.dto.DocumentoDto;
import it.bribank.gestioneMutui.repository.DocumentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentoService {

    private final DocumentoRepository documentoRepo;

    public DocumentoService(DocumentoRepository documentoRepo) {
        this.documentoRepo = documentoRepo;
    }

    public List<DocumentoDto> getDocumentiCaricati(Long idRichiesta) {
        return documentoRepo.findByRichiestaMutuoId(idRichiesta).stream()
                .map(doc -> {
                    DocumentoDto dto = new DocumentoDto(doc.getTipoDocumento().getId());
                    dto.setRichiestaMutuo(doc.getRichiestaMutuo().getId());
                    dto.setUrlFile(doc.getUrlFile());
                    dto.setStato(doc.getStato());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
