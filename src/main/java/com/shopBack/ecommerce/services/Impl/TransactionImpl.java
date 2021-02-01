package com.shopBack.ecommerce.services.Impl;

import com.shopBack.ecommerce.domains.Transaction;
import com.shopBack.ecommerce.repositories.TransactionRepository;
import com.shopBack.ecommerce.services.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TransactionImpl implements TransactionService {
    private TransactionRepository transactionRepository;
    private static final Logger log = LoggerFactory.getLogger(ArticleServiceImpl.class);

    public TransactionImpl(){

    }

    public TransactionImpl (TransactionRepository transactionRepository){
        this.transactionRepository = transactionRepository;
    }

    @Override
    public List<Transaction> findAll() {
        List<Transaction> list = new ArrayList<>();
        transactionRepository.findAll().forEach(e->list.add(e));
        return list;
    }

    @Override
    public Transaction findById(int id) {
        Optional<Transaction> optionalTransaction = transactionRepository.findById(id);
        return optionalTransaction.get();
    }

    @Override
    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public void update(Transaction transaction, int id) {

    }

    @Override
    public void deleteById(int id) {

    }


}
