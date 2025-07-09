package it.bribank.gestioneMutui.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "documenti")
public class Documento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_mutuo")
    private RichiestaMutuo richiestaMutuo;

    @ManyToOne
    @JoinColumn(name = "id_tipo_documento")
    private TipoDocumento tipoDocumento;

    private String urlFile;
    private Integer stato;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RichiestaMutuo getRichiestaMutuo() {
        return richiestaMutuo;
    }

    public void setRichiestaMutuo(RichiestaMutuo idMutuo) {
        this.richiestaMutuo = idMutuo;
    }

    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
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
