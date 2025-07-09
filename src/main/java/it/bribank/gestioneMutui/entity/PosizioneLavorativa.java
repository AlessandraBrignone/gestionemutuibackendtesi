package it.bribank.gestioneMutui.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "posizione_lavorativa")
public class PosizioneLavorativa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descrizionePosizioneLavorativa;
    private Integer stato;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescrizionePosizioneLavorativa() {
        return descrizionePosizioneLavorativa;
    }

    public void setDescrizionePosizioneLavorativa(String descrizionePosizioneLavorativa) {
        this.descrizionePosizioneLavorativa = descrizionePosizioneLavorativa;
    }

    public Integer getStato() {
        return stato;
    }

    public void setStato(Integer stato) {
        this.stato = stato;
    }
}
