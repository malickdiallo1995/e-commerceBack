package com.shopBack.ecommerce.services;


import com.shopBack.ecommerce.domains.TypeTelephone;

import java.util.List;

public interface TypeTelephoneService {

    List<TypeTelephone> findAll();
    TypeTelephone findById(int id);
    TypeTelephone save(TypeTelephone typeTelephone);
    void update(TypeTelephone typeTelephone, int id);
    void deleteById(int id);


}
