package com.shopBack.ecommerce.services;


import com.shopBack.ecommerce.domains.Produit;

import java.util.List;

public interface TelephoneService {

    List<Produit> findAll();
    List<Produit> findByCategory(String category);
    Produit findById(int id);
    Produit save(Produit produit);
    void update(Produit produit, int id);
    void deleteById(int id);

}
