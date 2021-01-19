package com.shopBack.ecommerce.services;


import com.shopBack.ecommerce.domains.Reglement;

import java.util.List;

public interface ReglementService {

    List<Reglement> findAll();
    Reglement findById(int id);
    Reglement save(Reglement reglement);
    void update(Reglement reglement, int id);
    void deleteById(int id);


}
