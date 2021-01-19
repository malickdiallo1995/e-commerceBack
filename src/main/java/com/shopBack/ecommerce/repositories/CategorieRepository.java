package com.shopBack.ecommerce.repositories;

import com.shopBack.ecommerce.domains.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategorieRepository extends JpaRepository<Categorie, Integer> {


}
