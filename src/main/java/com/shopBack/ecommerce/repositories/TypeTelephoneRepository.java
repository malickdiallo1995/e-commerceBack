package com.shopBack.ecommerce.repositories;


import com.shopBack.ecommerce.domains.TypeTelephone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeTelephoneRepository extends JpaRepository<TypeTelephone, Integer> {


}
