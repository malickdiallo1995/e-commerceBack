package com.shopBack.ecommerce.repositories;


import com.shopBack.ecommerce.domains.Marque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarqueRepository extends JpaRepository<Marque, Integer> {


}
