package it.bribank.gestioneMutui.dto;

public class RichiestaMutuoResponseDto {
    private Long id;
    private Long idIntestatario;
    private Long idCointestatario;
    private Long idGarante;
    //Dati utente
    private String nome;
    private String cognome;
    private String codiceFiscale;

    private String nomeCointestatario;
    private String cognomeCointestatario;
    private String codiceFiscaleCointestatario;

    private String nomeGarante;
    private String cognomeGarante;
    private String codiceFiscaleGarante;

    private Double importo;
    private Long durata; // solo l'id
    private Long cadenzaRata;
    private Integer dataRiscossione;
    private Long tipoMutuo;
    private Double interesseAnnuo;
    private Double spread;
    private String statoRichiesta;
    private Double valoreImmobile;

    // Dati finanziari intestatario/cointestatario
    private Double redditoFamiliareIc;
    private Integer componentiNucleoFamiliareIc;
    private Double valoreBeniImmobiliIc;
    private Double valorePartecipazioneIc;
    private Double ultimoIseeIc;

    // Dati finanziari garante
    private Double redditoFamiliareGa;
    private Integer componentiNucleoFamiliareGa;
    private Double valoreBeniImmobiliGa;
    private Double valorePartecipazioneGa;
    private Double ultimoIseeGa;

    private String noteRifiuto;
    private Integer stato;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdIntestatario() {
        return idIntestatario;
    }

    public void setIdIntestatario(Long idIntestatario) {
        this.idIntestatario = idIntestatario;
    }

    public Long getIdCointestatario() {
        return idCointestatario;
    }

    public void setIdCointestatario(Long idCointestatario) {
        this.idCointestatario = idCointestatario;
    }

    public Long getIdGarante() {
        return idGarante;
    }

    public void setIdGarante(Long idGarante) {
        this.idGarante = idGarante;
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

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public String getNomeCointestatario() {
        return nomeCointestatario;
    }

    public void setNomeCointestatario(String nomeCointestatario) {
        this.nomeCointestatario = nomeCointestatario;
    }

    public String getCognomeCointestatario() {
        return cognomeCointestatario;
    }

    public void setCognomeCointestatario(String cognomeCointestatario) {
        this.cognomeCointestatario = cognomeCointestatario;
    }

    public String getCodiceFiscaleCointestatario() {
        return codiceFiscaleCointestatario;
    }

    public void setCodiceFiscaleCointestatario(String codiceFiscaleCointestatario) {
        this.codiceFiscaleCointestatario = codiceFiscaleCointestatario;
    }

    public String getNomeGarante() {
        return nomeGarante;
    }

    public void setNomeGarante(String nomeGarante) {
        this.nomeGarante = nomeGarante;
    }

    public String getCognomeGarante() {
        return cognomeGarante;
    }

    public void setCognomeGarante(String cognomeGarante) {
        this.cognomeGarante = cognomeGarante;
    }

    public String getCodiceFiscaleGarante() {
        return codiceFiscaleGarante;
    }

    public void setCodiceFiscaleGarante(String codiceFiscaleGarante) {
        this.codiceFiscaleGarante = codiceFiscaleGarante;
    }

    public Double getImporto() {
        return importo;
    }

    public void setImporto(Double importo) {
        this.importo = importo;
    }

    public Long getDurata() {
        return durata;
    }

    public void setDurata(Long durata) {
        this.durata = durata;
    }

    public Long getCadenzaRata() {
        return cadenzaRata;
    }

    public void setCadenzaRata(Long cadenzaRata) {
        this.cadenzaRata = cadenzaRata;
    }

    public Integer getDataRiscossione() {
        return dataRiscossione;
    }

    public void setDataRiscossione(Integer dataRiscossione) {
        this.dataRiscossione = dataRiscossione;
    }

    public Long getTipoMutuo() {
        return tipoMutuo;
    }

    public void setTipoMutuo(Long tipoMutuo) {
        this.tipoMutuo = tipoMutuo;
    }

    public Double getInteresseAnnuo() {
        return interesseAnnuo;
    }

    public void setInteresseAnnuo(Double interesseAnnuo) {
        this.interesseAnnuo = interesseAnnuo;
    }

    public Double getSpread() {
        return spread;
    }

    public void setSpread(Double spread) {
        this.spread = spread;
    }

    public String getStatoRichiesta() {
        return statoRichiesta;
    }

    public void setStatoRichiesta(String statoRichiesta) {
        this.statoRichiesta = statoRichiesta;
    }

    public Double getValoreImmobile() {
        return valoreImmobile;
    }

    public void setValoreImmobile(Double valoreImmobile) {
        this.valoreImmobile = valoreImmobile;
    }

    public Double getRedditoFamiliareIc() {
        return redditoFamiliareIc;
    }

    public void setRedditoFamiliareIc(Double redditoFamiliareIc) {
        this.redditoFamiliareIc = redditoFamiliareIc;
    }

    public Integer getComponentiNucleoFamiliareIc() {
        return componentiNucleoFamiliareIc;
    }

    public void setComponentiNucleoFamiliareIc(Integer componentiNucleoFamiliareIc) {
        this.componentiNucleoFamiliareIc = componentiNucleoFamiliareIc;
    }

    public Double getValoreBeniImmobiliIc() {
        return valoreBeniImmobiliIc;
    }

    public void setValoreBeniImmobiliIc(Double valoreBeniImmobiliIc) {
        this.valoreBeniImmobiliIc = valoreBeniImmobiliIc;
    }

    public Double getValorePartecipazioneIc() {
        return valorePartecipazioneIc;
    }

    public void setValorePartecipazioneIc(Double valorePartecipazioneIc) {
        this.valorePartecipazioneIc = valorePartecipazioneIc;
    }

    public Double getUltimoIseeIc() {
        return ultimoIseeIc;
    }

    public void setUltimoIseeIc(Double ultimoIseeIc) {
        this.ultimoIseeIc = ultimoIseeIc;
    }

    public Double getRedditoFamiliareGa() {
        return redditoFamiliareGa;
    }

    public void setRedditoFamiliareGa(Double redditoFamiliareGa) {
        this.redditoFamiliareGa = redditoFamiliareGa;
    }

    public Integer getComponentiNucleoFamiliareGa() {
        return componentiNucleoFamiliareGa;
    }

    public void setComponentiNucleoFamiliareGa(Integer componentiNucleoFamiliareGa) {
        this.componentiNucleoFamiliareGa = componentiNucleoFamiliareGa;
    }

    public Double getValoreBeniImmobiliGa() {
        return valoreBeniImmobiliGa;
    }

    public void setValoreBeniImmobiliGa(Double valoreBeniImmobiliGa) {
        this.valoreBeniImmobiliGa = valoreBeniImmobiliGa;
    }

    public Double getValorePartecipazioneGa() {
        return valorePartecipazioneGa;
    }

    public void setValorePartecipazioneGa(Double valorePartecipazioneGa) {
        this.valorePartecipazioneGa = valorePartecipazioneGa;
    }

    public Double getUltimoIseeGa() {
        return ultimoIseeGa;
    }

    public void setUltimoIseeGa(Double ultimoIseeGa) {
        this.ultimoIseeGa = ultimoIseeGa;
    }

    public String getNoteRifiuto() {
        return noteRifiuto;
    }

    public void setNoteRifiuto(String noteRifiuto) {
        this.noteRifiuto = noteRifiuto;
    }

    public Integer getStato() {
        return stato;
    }

    public void setStato(Integer stato) {
        this.stato = stato;
    }
}
