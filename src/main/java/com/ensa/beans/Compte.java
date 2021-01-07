package com.ensa.beans;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Compte {
    @Id
    private int idCompte;
    private double solde;

    public Compte() {
    }

    public int getIdCompte() {
        return idCompte;
    }

    public void setIdCompte(int idCompte) {
        this.idCompte = idCompte;
    }

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }
}
