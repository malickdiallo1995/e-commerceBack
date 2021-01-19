package com.shopBack.ecommerce.services;

import com.shopBack.ecommerce.domains.Transaction;

import java.util.List;

public interface TransactionService {
    List<Transaction> findAll();
    Transaction findById(int id);
    Transaction save(Transaction transaction);
    void update(Transaction transaction, int id);
    void deleteById(int id);
}
