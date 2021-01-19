package com.shopBack.ecommerce.services;


import com.shopBack.ecommerce.domains.ModeReglement;

import java.util.List;

public interface ModeReglementService {

    List<ModeReglement> findAll();
    ModeReglement findById(int id);
    ModeReglement save(ModeReglement modeReglement);
    void update(ModeReglement modeReglement, int id);
    void deleteById(int id);


}
