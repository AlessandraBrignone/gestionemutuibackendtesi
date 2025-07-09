package it.bribank.gestioneMutui.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "cadenza_rata")
public class CadenzaRata {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descrizioneTipoRata;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescrizioneTipoRata() {
        return descrizioneTipoRata;
    }

    public void setDescrizioneTipoRata(String descrizioneTipoRata) {
        this.descrizioneTipoRata = descrizioneTipoRata;
    }
}
