package it.bribank.gestioneMutui.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class TipoDocumento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descrizioneTipoDocumento;
    private Integer stato;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescrizioneTipoDocumento() {
        return descrizioneTipoDocumento;
    }

    public void setDescrizioneTipoDocumento(String descrizioneTipoDocumento) {
        this.descrizioneTipoDocumento = descrizioneTipoDocumento;
    }

    public Integer getStato() {
        return stato;
    }

    public void setStato(Integer stato) {
        this.stato = stato;
    }
}
