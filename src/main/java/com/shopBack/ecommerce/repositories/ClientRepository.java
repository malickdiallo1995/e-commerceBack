package com.shopBack.ecommerce.repositories;

import com.shopBack.ecommerce.domains.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

}
