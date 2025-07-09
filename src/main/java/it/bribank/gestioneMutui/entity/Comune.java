package it.bribank.gestioneMutui.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Comune {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long codiceComune;
    private String descrizioneComune;
    private String regione;
    private String descrizioneProvincia;
    private Long capoluogo;
    private String siglaAutomobilistica;
    private String codiceCatastale;
    private Integer stato;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCodiceComune() {
        return codiceComune;
    }

    public void setCodiceComune(Long codiceComune) {
        this.codiceComune = codiceComune;
    }

    public String getDescrizioneComune() {
        return descrizioneComune;
    }

    public void setDescrizioneComune(String descrizioneComune) {
        this.descrizioneComune = descrizioneComune;
    }

    public String getRegione() {
        return regione;
    }

    public void setRegione(String regione) {
        this.regione = regione;
    }

    public String getDescrizioneProvincia() {
        return descrizioneProvincia;
    }

    public void setDescrizioneProvincia(String descrizioneProvincia) {
        this.descrizioneProvincia = descrizioneProvincia;
    }

    public Long getCapoluogo() {
        return capoluogo;
    }

    public void setCapoluogo(Long capoluogo) {
        this.capoluogo = capoluogo;
    }

    public String getSiglaAutomobilistica() {
        return siglaAutomobilistica;
    }

    public void setSiglaAutomobilistica(String siglaAutomobilistica) {
        this.siglaAutomobilistica = siglaAutomobilistica;
    }

    public String getCodiceCatastale() {
        return codiceCatastale;
    }

    public void setCodiceCatastale(String codiceCatastale) {
        this.codiceCatastale = codiceCatastale;
    }

    public Integer getStato() {
        return stato;
    }

    public void setStato(Integer stato) {
        this.stato = stato;
    }
}

