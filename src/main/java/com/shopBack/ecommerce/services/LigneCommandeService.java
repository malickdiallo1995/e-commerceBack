package com.shopBack.ecommerce.services;



import com.shopBack.ecommerce.domains.LigneCommande;

import java.util.List;

public interface LigneCommandeService {

    List<LigneCommande> findAll();
    LigneCommande findById(int id);
    LigneCommande save(LigneCommande ligneCommande);
    void update(LigneCommande LigneCommande, int id);
    void deleteById(int id);


}
