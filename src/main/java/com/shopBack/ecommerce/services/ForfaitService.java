package com.shopBack.ecommerce.services;

import com.shopBack.ecommerce.domains.Forfait;

import java.util.List;

public interface ForfaitService {

    List<Forfait> findAll();
    Forfait findById(int id);
    Forfait save(Forfait forfait);
    void update(Forfait forfait, int id);
    void deleteById(int id);

}
