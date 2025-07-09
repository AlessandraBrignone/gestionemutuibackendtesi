package it.bribank.gestioneMutui.service;

import it.bribank.gestioneMutui.dto.PianoAmmortamentoDto;
import it.bribank.gestioneMutui.entity.PianoAmmortamento;
import it.bribank.gestioneMutui.entity.RichiestaMutuo;
import it.bribank.gestioneMutui.repository.PianoAmmortamentoRepository;
import it.bribank.gestioneMutui.repository.InserimentoRichiestaMutuoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PianoAmmortamentoService {

    @Autowired
    private PianoAmmortamentoRepository pianoAmmortamentoRepository;
    @Autowired
    private InserimentoRichiestaMutuoRepository inserimentoRichiestaMutuoRepository;

    public List<PianoAmmortamento> generaSalva(PianoAmmortamentoDto pianoAmmortamentoDto) {
        RichiestaMutuo richiestaMutuo = inserimentoRichiestaMutuoRepository.findById(pianoAmmortamentoDto.getRichiestaId())
                .orElseThrow(() -> new IllegalArgumentException("Richiesta non trovata"));

        double importo = richiestaMutuo.getImporto();
        double interesseAnnuo = richiestaMutuo.getInteresseAnnuo() / 100.0;
        String cadenza = richiestaMutuo.getCadenzaRata().getDescrizioneTipoRata().toLowerCase();
        int durataAnni = Integer.parseInt(richiestaMutuo.getDurata().getDescrizioneDurata());

        int rateAnnue;
        switch (cadenza){
            case "mensile":
                rateAnnue = 12;
                break;
            case "trimestrale":
                rateAnnue = 4;
                break;
            case "semestrale":
                rateAnnue = 2;
                break;
            default:
                throw new IllegalArgumentException("Tipo di cadenza non riconosciuta: " + cadenza);
        }

        int totaleRate = durataAnni * rateAnnue;
        double capitaleResiduo = importo;
        double quotaCapitale = importo / totaleRate;

        List<PianoAmmortamento> piano = new ArrayList<>();

        for (int i = 1; i <= totaleRate; i++) {
            double quotaInteressi = capitaleResiduo * (interesseAnnuo / rateAnnue);
            capitaleResiduo -= quotaCapitale;

            PianoAmmortamento rata = new PianoAmmortamento();
            rata.setIdMutuo(richiestaMutuo.getId());
            rata.setNumeroRata(String.valueOf(i));
            rata.setQuotaCapitale(String.format("%.2f", quotaCapitale));
            rata.setQuotaInteressi(String.format("%.2f", quotaInteressi));
            rata.setCapitaleResiduo(String.format("%.2f", Math.max(capitaleResiduo, 0)));

            piano.add(rata);
        }

        return pianoAmmortamentoRepository.saveAll(piano);
    }
}
