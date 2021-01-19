package com.shopBack.ecommerce.services;


import com.shopBack.ecommerce.domains.ForfaitTelephone;

import java.util.List;

public interface ForfaitTelephoneService {

    List<ForfaitTelephone> findAll();
    ForfaitTelephone findById(int id);
    ForfaitTelephone save(ForfaitTelephone forfaitTelephone);
    void update(ForfaitTelephone forfaitTelephone, int id);
    void deleteById(int id);

}
