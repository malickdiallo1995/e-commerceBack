package com.shopBack.ecommerce.repositories;


import com.shopBack.ecommerce.domains.ModeReglement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModeReglementRepository extends JpaRepository<ModeReglement, Integer> {


}
