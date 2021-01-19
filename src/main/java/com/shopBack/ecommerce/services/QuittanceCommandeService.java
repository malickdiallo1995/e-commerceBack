package com.shopBack.ecommerce.services;


import com.shopBack.ecommerce.domains.QuittanceCommande;

import java.util.List;

public interface QuittanceCommandeService {

    List<QuittanceCommande> findAll();
    QuittanceCommande findById(int id);
    QuittanceCommande save(QuittanceCommande quittance);
    void update(QuittanceCommande quittance, int id);
    void deleteById(int id);


}
