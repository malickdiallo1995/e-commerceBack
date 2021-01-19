package com.shopBack.ecommerce.repositories;


import com.shopBack.ecommerce.domains.QuittanceCommande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuittanceCommandeRepository extends JpaRepository<QuittanceCommande, Integer> {


}
