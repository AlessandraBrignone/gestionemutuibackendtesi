package it.bribank.gestioneMutui.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "filiale")
public class Filiale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descrizioneFiliale;
    private String indirizzo;
    private Long idComune;
    private String cap;
    private String cabFiliale;
    private Integer stato;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescrizioneFiliale() {
        return descrizioneFiliale;
    }

    public void setDescrizioneFiliale(String descrizioneFiliale) {
        this.descrizioneFiliale = descrizioneFiliale;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public Long getIdComune() {
        return idComune;
    }

    public void setIdComune(Long idComune) {
        this.idComune = idComune;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getCabFiliale() {
        return cabFiliale;
    }

    public void setCabFiliale(String cabFiliale) {
        this.cabFiliale = cabFiliale;
    }

    public Integer getStato() {
        return stato;
    }

    public void setStato(Integer stato) {
        this.stato = stato;
    }
}
