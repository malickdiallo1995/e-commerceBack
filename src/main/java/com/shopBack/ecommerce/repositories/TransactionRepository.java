package com.shopBack.ecommerce.repositories;

import com.shopBack.ecommerce.domains.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

@Transactional
public interface TransactionRepository extends JpaRepository <Transaction,Integer> {
    @Modifying
    @Query("update Transaction t set t.status = ?4, t.currency = ?2, t.payment_options = ?3  where t.transaction_id = ?1 ")
    void setTransactionStateByTransactionId(String transactionId,  String currency, String channel, String status);

    @Query("select t  from Transaction t where t.transaction_id= ?1")
    Transaction findTransactionByTransaction_id(String transaction_id);
}
