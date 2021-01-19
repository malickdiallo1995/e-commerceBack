package com.shopBack.ecommerce.repositories;


import com.shopBack.ecommerce.domains.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProduitRepository extends JpaRepository<Produit, Integer> {



}
