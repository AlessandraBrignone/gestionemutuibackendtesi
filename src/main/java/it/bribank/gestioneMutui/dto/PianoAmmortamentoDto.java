package it.bribank.gestioneMutui.dto;

import java.util.List;

public class PianoAmmortamentoDto {
    private Long richiestaId;
    private List<ListaPianoAmmortamentoDto> piano;

    public Long getRichiestaId() {
        return richiestaId;
    }

    public void setRichiestaId(Long richiestaId) {
        this.richiestaId = richiestaId;
    }

    public List<ListaPianoAmmortamentoDto> getPiano() {
        return piano;
    }

    public void setPiano(List<ListaPianoAmmortamentoDto> piano) {
        this.piano = piano;
    }

    public static class ListaPianoAmmortamentoDto {
        private Long idMutuo;
        private String numeroRata;
        private String quotaCapitale;
        private String quotaInteressi;
        private String capitaleResiduo;

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

        public String getCapitaleResiduo() {
            return capitaleResiduo;
        }

        public void setCapitaleResiduo(String capitaleResiduo) {
            this.capitaleResiduo = capitaleResiduo;
        }
    }
}
