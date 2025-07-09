package it.bribank.gestioneMutui.dto;

public class AnagraficaDto {
    private String nome;
    private String cognome;
    private String sesso;
    private String dataNascita; // formato: "yyyy-MM-dd"
    private Long comuneNascita;
    private String codiceFiscale;
    private String email;
    private String telefono;
    private String cellulare;
    private String indirizzoResidenza;
    private String capResidenza;
    private Long comuneResidenza;
    private String provinciaResidenza;
    private String DomUgualeRes;
    private String indirizzoDomicilio;
    private String capDomicilio;
    private Long comuneDomicilio;
    private String provinciaDomicilio;
    private Integer stato;

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

    public String getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(String dataNascita) {
        this.dataNascita = dataNascita;
    }

    public Long getComuneNascita() {
        return comuneNascita;
    }

    public void setComuneNascita(Long comuneNascita) {
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

    public Long getComuneResidenza() {
        return comuneResidenza;
    }

    public void setComuneResidenza(Long comuneResidenza) {
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

    public Long getComuneDomicilio() {
        return comuneDomicilio;
    }

    public void setComuneDomicilio(Long comuneDomicilio) {
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
