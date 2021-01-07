package com.ensa.beans;

import java.util.Date;

public class TransactionDTO {
    private int idTransaction;
    private int idCompte;
    private double montant;
    private Date dateTransaction;

    public TransactionDTO(int idTransaction, int idCompte, double montant, Date dateTransaction) {
        this.idTransaction = idTransaction;
        this.idCompte = idCompte;
        this.montant = montant;
        this.dateTransaction = dateTransaction;
    }


    public int getIdTransaction() {
        return idTransaction;
    }

    public int getIdCompte() {
        return idCompte;
    }

    public double getMontant() {
        return montant;
    }

    public Date getDateTransaction() {
        return dateTransaction;
    }

}
