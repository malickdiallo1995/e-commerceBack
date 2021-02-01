package com.shopBack.ecommerce.repositories;

import com.shopBack.ecommerce.domains.Commande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

@Transactional
public interface CommandeRepository extends JpaRepository <Commande,Integer> {
    @Modifying
    @Query("update Commande c set c.statutCommande = ?1 where c.id = ?2 ")
    void setCommandeStateByCommandeId(String statutCommande,  int idCommande);

}
