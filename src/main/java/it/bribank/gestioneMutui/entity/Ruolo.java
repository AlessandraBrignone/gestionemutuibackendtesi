package it.bribank.gestioneMutui.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ruolo")
public class Ruolo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descrizioneRuolo;
    private Integer stato;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescrizioneRuolo() {
        return descrizioneRuolo;
    }

    public void setDescrizioneRuolo(String descrizioneRuolo) {
        this.descrizioneRuolo = descrizioneRuolo;
    }

    public Integer getStato() {
        return stato;
    }

    public void setStato(Integer stato) {
        this.stato = stato;
    }
}
