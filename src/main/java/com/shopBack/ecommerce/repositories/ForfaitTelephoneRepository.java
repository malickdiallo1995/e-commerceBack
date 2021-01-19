package com.shopBack.ecommerce.repositories;


import com.shopBack.ecommerce.domains.ForfaitTelephone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForfaitTelephoneRepository extends JpaRepository<ForfaitTelephone, Integer> {

}
