package com.shopBack.ecommerce.repositories;

import com.shopBack.ecommerce.domains.Commande;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandeRepository extends JpaRepository <Commande,Integer> {
}
