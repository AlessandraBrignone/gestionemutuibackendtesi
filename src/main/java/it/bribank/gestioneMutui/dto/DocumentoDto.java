package it.bribank.gestioneMutui.dto;

public class DocumentoDto {

    private Long richiestaMutuo;
    private Long tipoDocumento;
    private String urlFile;
    private Integer stato;

    public DocumentoDto(Long idTipoDocumento) {
        this.tipoDocumento = idTipoDocumento;
    }

    public Long getRichiestaMutuo() {
        return richiestaMutuo;
    }

    public void setRichiestaMutuo(Long richiestaMutuo) {
        this.richiestaMutuo = richiestaMutuo;
    }

    public Long getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(Long tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getUrlFile() {
        return urlFile;
    }

    public void setUrlFile(String urlFile) {
        this.urlFile = urlFile;
    }

    public Integer getStato() {
        return stato;
    }

    public void setStato(Integer stato) {
        this.stato = stato;
    }
}
