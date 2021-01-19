package com.shopBack.ecommerce.services;


import com.shopBack.ecommerce.domains.Offre;

import java.util.List;

public interface OffreService {

    List<Offre> findAll();
    Offre findById(int id);
    Offre save(Offre offre);
    void update(Offre offre, int id);
    void deleteById(int id);


}
