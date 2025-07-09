package it.bribank.gestioneMutui.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Anagrafica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String cognome;
    private String sesso;
    private LocalDate dataNascita;

    @ManyToOne
    @JoinColumn(name = "id_comune_nascita")
    private Comune comuneNascita;

    private String codiceFiscale;
    private String email;
    private String telefono;
    private String cellulare;
    private String indirizzoResidenza;
    private String capResidenza;

    @ManyToOne
    @JoinColumn(name = "id_comune_residenza")
    private Comune comuneResidenza;

    private String provinciaResidenza;
    private String DomUgualeRes;
    private String indirizzoDomicilio;
    private String capDomicilio;

    @ManyToOne
    @JoinColumn(name = "id_comune_domicilio")
    private Comune comuneDomicilio;

    private String provinciaDomicilio;
    private Integer stato;

//    @OneToOne(mappedBy = "anagrafica", cascade = CascadeType.ALL)
//    private AnagraficaRichiedente anagraficaRichiedente;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getSesso() {
        return sesso;
    }

    public void setSesso(String sesso) {
        this.sesso = sesso;
    }

    public LocalDate getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(LocalDate dataNascita) {
        this.dataNascita = dataNascita;
    }

    public Comune getComuneNascita() {
        return comuneNascita;
    }

    public void setComuneNascita(Comune comuneNascita) {
        this.comuneNascita = comuneNascita;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCellulare() {
        return cellulare;
    }

    public void setCellulare(String cellulare) {
        this.cellulare = cellulare;
    }

    public String getIndirizzoResidenza() {
        return indirizzoResidenza;
    }

    public void setIndirizzoResidenza(String indirizzoResidenza) {
        this.indirizzoResidenza = indirizzoResidenza;
    }

    public String getCapResidenza() {
        return capResidenza;
    }

    public void setCapResidenza(String capResidenza) {
        this.capResidenza = capResidenza;
    }

    public Comune getComuneResidenza() {
        return comuneResidenza;
    }

    public void setComuneResidenza(Comune comuneResidenza) {
        this.comuneResidenza = comuneResidenza;
    }

    public String getProvinciaResidenza() {
        return provinciaResidenza;
    }

    public void setProvinciaResidenza(String provinciaResidenza) {
        this.provinciaResidenza = provinciaResidenza;
    }

    public String getDomUgualeRes() {
        return DomUgualeRes;
    }

    public void setDomUgualeRes(String domUgualeRes) {
        DomUgualeRes = domUgualeRes;
    }

    public String getIndirizzoDomicilio() {
        return indirizzoDomicilio;
    }

    public void setIndirizzoDomicilio(String indirizzoDomicilio) {
        this.indirizzoDomicilio = indirizzoDomicilio;
    }

    public String getCapDomicilio() {
        return capDomicilio;
    }

    public void setCapDomicilio(String capDomicilio) {
        this.capDomicilio = capDomicilio;
    }

    public Comune getComuneDomicilio() {
        return comuneDomicilio;
    }

    public void setComuneDomicilio(Comune comuneDomicilio) {
        this.comuneDomicilio = comuneDomicilio;
    }

    public String getProvinciaDomicilio() {
        return provinciaDomicilio;
    }

    public void setProvinciaDomicilio(String provinciaDomicilio) {
        this.provinciaDomicilio = provinciaDomicilio;
    }

    public Integer getStato() {
        return stato;
    }

    public void setStato(Integer stato) {
        this.stato = stato;
    }

}
