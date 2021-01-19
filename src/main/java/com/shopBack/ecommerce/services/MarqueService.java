package com.shopBack.ecommerce.services;


import com.shopBack.ecommerce.domains.Marque;

import java.util.List;

public interface MarqueService {

    List<Marque> findAll();
    Marque findById(int id);
    Marque save(Marque marque);
    void update(Marque marque, int id);
    void deleteById(int id);


}
