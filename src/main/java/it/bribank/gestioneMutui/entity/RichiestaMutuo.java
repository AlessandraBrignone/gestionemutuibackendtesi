package it.bribank.gestioneMutui.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "richiesta_mutuo")
public class RichiestaMutuo {
    //Richiesta mutuo
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //utente, garante , cointestatario
    @JoinColumn(name = "id_intestatario")
    private Long idIntestatario;
    @JoinColumn(name = "id_cointestatario")
    private Long idCointestatario;
    @JoinColumn(name = "id_garante")
    private Long idGarante;

    @ManyToOne
    @JoinColumn(name = "id_durata")
    private Durata durata;

    private Double importo;
    @JoinColumn(name = "importo_rata")
    private Double importoRata;

    @ManyToOne
    @JoinColumn(name = "id_cadenza_rata")
    private CadenzaRata cadenzaRata;

    @ManyToOne
    @JoinColumn(name = "id_tipo_mutuo")
    private TipoMutuo tipoMutuo;
    @JoinColumn(name = "interesse_annuo")
    private Double interesseAnnuo;

    @JoinColumn(name = "stato_richiesta")
    private String statoRichiesta;
    @JoinColumn(name = "durata_richiesta")
    private Double durataRichiesta;
    @JoinColumn(name = "data_riscossione")
    private int dataRiscossione;
    @JoinColumn(name = "valore_immobile")
    private Double valoreImmobile;

    /*Dati finanziari
     ** Intestatrio/cointestatario
     */
    @Column(name = "reddito_familiare_ic")
    private Double redditoFamiliareIc;

    @ManyToOne
    @JoinColumn(name = "id_posizione_lavorativa_in")
    private PosizioneLavorativa posizioneLavorativaIn;

    @ManyToOne
    @JoinColumn(name = "id_posizione_lavorativa_co")
    private PosizioneLavorativa posizioneLavorativaCo;

    @Column(name = "componenti_nucleo_familiare_ic")
    private int componentiNucleoFamiliareIc;

    @Column(name = "valore_beni_immobili_ic")
    private Double valoreBeniImmobiliIc;

    @Column(name = "valore_partecipazione_ic")
    private Double valorePartecipazioneIc;

    @Column(name = "ultimo_isee_ic")
    private Double ultimoIseeIc;

    /*Dati finanziari
     ** Garante
     */
    @Column(name = "reddito_familiare_ga")
    private Double redditoFamiliareGa;

    @ManyToOne
    @JoinColumn(name = "id_posizione_lavorativa_ga")
    private PosizioneLavorativa posizioneLavorativaGa;

    @Column(name = "componenti_nucleo_familiare_ga")
    private int componentiNucleoFamiliareGa;

    @Column(name = "valore_beni_immobili_ga")
    private Double valoreBeniImmobiliGa;

    @Column(name = "valore_partecipazione_ga")
    private Double valorePartecipazioneGa;

    @Column(name = "ultimo_isee_ga")
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

    public Durata getDurata() {
        return durata;
    }

    public void setDurata(Durata durata) {
        this.durata = durata;
    }

    public Double getImporto() {
        return importo;
    }

    public void setImporto(Double importo) {
        this.importo = importo;
    }

    public Double getImportoRata() {
        return importoRata;
    }

    public void setImportoRata(Double importoRata) {
        this.importoRata = importoRata;
    }

    public CadenzaRata getCadenzaRata() {
        return cadenzaRata;
    }

    public void setCadenzaRata(CadenzaRata cadenzaRata) {
        this.cadenzaRata = cadenzaRata;
    }

    public TipoMutuo getTipoMutuo() {
        return tipoMutuo;
    }

    public void setTipoMutuo(TipoMutuo tipoMutuo) {
        this.tipoMutuo = tipoMutuo;
    }

    public Double getInteresseAnnuo() {
        return interesseAnnuo;
    }

    public void setInteresseAnnuo(Double interesseAnnuo) {
        this.interesseAnnuo = interesseAnnuo;
    }

    public String getStatoRichiesta() {
        return statoRichiesta;
    }

    public void setStatoRichiesta(String statoRichiesta) {
        this.statoRichiesta = statoRichiesta;
    }

    public Double getDurataRichiesta() {
        return durataRichiesta;
    }

    public void setDurataRichiesta(Double durataRichiesta) {
        this.durataRichiesta = durataRichiesta;
    }

    public int getDataRiscossione() {
        return dataRiscossione;
    }

    public void setDataRiscossione(int dataRiscossione) {
        this.dataRiscossione = dataRiscossione;
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

    public PosizioneLavorativa getPosizioneLavorativaIn() {
        return posizioneLavorativaIn;
    }

    public void setPosizioneLavorativaIn(PosizioneLavorativa posizioneLavorativaIn) {
        this.posizioneLavorativaIn = posizioneLavorativaIn;
    }

    public PosizioneLavorativa getPosizioneLavorativaCo() {
        return posizioneLavorativaCo;
    }

    public void setPosizioneLavorativaCo(PosizioneLavorativa posizioneLavorativaCo) {
        this.posizioneLavorativaCo = posizioneLavorativaCo;
    }

    public int getComponentiNucleoFamiliareIc() {
        return componentiNucleoFamiliareIc;
    }

    public void setComponentiNucleoFamiliareIc(int componentiNucleoFamiliareIc) {
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

    public PosizioneLavorativa getPosizioneLavorativaGa() {
        return posizioneLavorativaGa;
    }

    public void setPosizioneLavorativaGa(PosizioneLavorativa posizioneLavorativaGa) {
        this.posizioneLavorativaGa = posizioneLavorativaGa;
    }

    public int getComponentiNucleoFamiliareGa() {
        return componentiNucleoFamiliareGa;
    }

    public void setComponentiNucleoFamiliareGa(int componentiNucleoFamiliareGa) {
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

    public Integer getStato() {
        return stato;
    }

    public void setStato(Integer stato) {
        this.stato = stato;
    }

    public String getNoteRifiuto() {
        return noteRifiuto;
    }

    public void setNoteRifiuto(String noteRifiuto) {
        this.noteRifiuto = noteRifiuto;
    }
}
