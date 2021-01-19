package com.shopBack.ecommerce.repositories;

import com.shopBack.ecommerce.domains.Forfait;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForfaitRepository extends JpaRepository<Forfait, Integer> {


}
