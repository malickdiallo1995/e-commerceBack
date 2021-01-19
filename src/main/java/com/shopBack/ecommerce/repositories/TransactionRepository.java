package com.shopBack.ecommerce.repositories;

import com.shopBack.ecommerce.domains.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository <Transaction,Integer> {
}
