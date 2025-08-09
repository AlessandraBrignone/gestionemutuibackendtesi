package it.bribank.gestioneMutui.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "piano_ammortamento")
public class PianoAmmortamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idMutuo;
    private String numeroRata;
    private String quotaCapitale;
    private String quotaInteressi;
    private String totaleRata;
    private String capitaleResiduo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdMutuo() {
        return idMutuo;
    }

    public void setIdMutuo(Long idMutuo) {
        this.idMutuo = idMutuo;
    }

    public String getNumeroRata() {
        return numeroRata;
    }

    public void setNumeroRata(String numeroRata) {
        this.numeroRata = numeroRata;
    }

    public String getQuotaCapitale() {
        return quotaCapitale;
    }

    public void setQuotaCapitale(String quotaCapitale) {
        this.quotaCapitale = quotaCapitale;
    }

    public String getQuotaInteressi() {
        return quotaInteressi;
    }

    public void setQuotaInteressi(String quotaInteressi) {
        this.quotaInteressi = quotaInteressi;
    }

    public String getTotaleRata() {
        return totaleRata;
    }

    public void setTotaleRata(String totaleRata) {
        this.totaleRata = totaleRata;
    }

    public String getCapitaleResiduo() {
        return capitaleResiduo;
    }

    public void setCapitaleResiduo(String capitaleResiduo) {
        this.capitaleResiduo = capitaleResiduo;
    }
}
