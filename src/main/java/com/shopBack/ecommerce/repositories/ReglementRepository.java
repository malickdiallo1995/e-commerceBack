package com.shopBack.ecommerce.repositories;


import com.shopBack.ecommerce.domains.Reglement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReglementRepository extends JpaRepository<Reglement, Integer> {



}
