package it.bribank.gestioneMutui.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tipo_mutuo")
public class TipoMutuo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descrizioneTipoMutuo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_spread")
    private Spread spread;

    private Integer stato;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescrizioneTipoMutuo() {
        return descrizioneTipoMutuo;
    }

    public void setDescrizioneTipoMutuo(String descrizioneTipoMutuo) {
        this.descrizioneTipoMutuo = descrizioneTipoMutuo;
    }

    public Spread getSpread() {
        return spread;
    }

    public void setSpread(Spread spread) {
        this.spread = spread;
    }

    public Integer getStato() {
        return stato;
    }

    public void setStato(Integer stato) {
        this.stato = stato;
    }
}
