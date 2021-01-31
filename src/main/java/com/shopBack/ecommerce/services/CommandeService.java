package com.shopBack.ecommerce.services;

import com.shopBack.ecommerce.domains.Commande;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.List;

public interface CommandeService {
    List<Commande> findAll();
    Commande findById(int id);
    Commande save(Commande commande);
    void update (Commande commande, int id);
    void deleteById(int id);
}
