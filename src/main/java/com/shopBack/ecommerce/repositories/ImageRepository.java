package com.shopBack.ecommerce.repositories;


import com.shopBack.ecommerce.domains.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {


}
