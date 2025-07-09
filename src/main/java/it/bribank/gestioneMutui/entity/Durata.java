package it.bribank.gestioneMutui.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Durata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descrizioneDurata;
    private Integer stato;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescrizioneDurata() {
        return descrizioneDurata;
    }

    public void setDescrizioneDurata(String descrizioneDurata) {
        this.descrizioneDurata = descrizioneDurata;
    }

    public Integer getStato() {
        return stato;
    }

    public void setStato(Integer stato) {
        this.stato = stato;
    }
}
