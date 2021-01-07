package com.ensa.config;

import com.ensa.beans.Compte;
import com.ensa.beans.Transaction;
import com.ensa.repositories.CompteRepository;
import com.ensa.repositories.TransactionRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class DBWriter implements ItemWriter<Transaction> {
    private final TransactionRepository transactionRepository;
    private final CompteRepository compteRepository;

    public DBWriter(CompteRepository compteRepository, TransactionRepository transactionRepository) {
        this.compteRepository = compteRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void write(List<? extends Transaction> transactions) {
        System.out.println("Writing...");
        for(Transaction transaction:transactions){
            Compte compte=transaction.getCompte();
            compte.setSolde(compte.getSolde()-transaction.getMontant());
            compteRepository.save(compte);
            transaction.setCompte(compte);
            transactionRepository.save(transaction);
        }
        System.out.println("Finished Writing !");
    }
}
