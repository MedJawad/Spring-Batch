package com.ensa.config;

import com.ensa.beans.Compte;
import com.ensa.beans.Transaction;
import com.ensa.beans.TransactionDTO;
import com.ensa.repositories.CompteRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class DBProcessor implements ItemProcessor<TransactionDTO, Transaction> {
    private final CompteRepository compteRepository;

    public DBProcessor(CompteRepository compteRepository) {
        this.compteRepository = compteRepository;
    }

    @Override
    public Transaction process(TransactionDTO transactionDTO) {
        Compte compte = compteRepository.findById(transactionDTO.getIdCompte()).get();
        Transaction transaction=new Transaction(transactionDTO);
        transaction.setCompte(compte);
        return transaction;
    }
}
