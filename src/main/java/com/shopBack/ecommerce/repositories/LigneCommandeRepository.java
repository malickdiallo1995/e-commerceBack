package com.shopBack.ecommerce.repositories;



import com.shopBack.ecommerce.domains.LigneCommande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LigneCommandeRepository extends JpaRepository<LigneCommande, Integer> {


}
