package com.shopBack.ecommerce.repositories;


import com.shopBack.ecommerce.domains.Operateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperateurRepository extends JpaRepository<Operateur, Integer> {


}
