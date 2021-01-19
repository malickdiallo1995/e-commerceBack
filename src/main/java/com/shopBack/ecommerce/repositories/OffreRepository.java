package com.shopBack.ecommerce.repositories;


import com.shopBack.ecommerce.domains.Offre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OffreRepository extends JpaRepository<Offre, Integer> {


}
