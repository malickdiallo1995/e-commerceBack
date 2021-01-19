package com.shopBack.ecommerce.repositories;

import com.shopBack.ecommerce.domains.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface TransactionRepository extends JpaRepository <Transaction,Integer> {
    @Modifying
    @Query("update Transaction t set t.status = ?2 where t.transaction_id = ?1 ")
    void setTransactionStateByTransactionId(String transactionId, String status);
}
