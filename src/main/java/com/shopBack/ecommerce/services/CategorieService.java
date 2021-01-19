package com.shopBack.ecommerce.services;


import com.shopBack.ecommerce.domains.Categorie;

import java.util.List;

public interface CategorieService {

    List<Categorie> findAll();
    Categorie findById(int id);
    Categorie save(Categorie categorie);
    void update(Categorie categorie, int id);
    void deleteById(int id);


}
