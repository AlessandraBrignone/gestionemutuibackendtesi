package it.bribank.gestioneMutui.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "spread")
public class Spread {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double descrizioneSpread;
    private Integer stato;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getDescrizioneSpread() {
        return descrizioneSpread;
    }

    public void setDescrizioneSpread(Double descrizioneSpread) {
        this.descrizioneSpread = descrizioneSpread;
    }

    public Integer getStato() {
        return stato;
    }

    public void setStato(Integer stato) {
        this.stato = stato;
    }
}
