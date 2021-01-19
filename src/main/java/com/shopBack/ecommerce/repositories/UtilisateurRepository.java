package com.shopBack.ecommerce.repositories;


import com.shopBack.ecommerce.domains.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {


}
