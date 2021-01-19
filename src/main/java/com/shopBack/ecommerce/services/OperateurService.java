package com.shopBack.ecommerce.services;


import com.shopBack.ecommerce.domains.Operateur;

import java.util.List;

public interface OperateurService {

    List<Operateur> findAll();
    Operateur findById(int id);
    Operateur save(Operateur operateur);
    void update(Operateur operateur, int id);
    void deleteById(int id);


}
