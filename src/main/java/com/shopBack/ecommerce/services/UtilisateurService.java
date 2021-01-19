package com.shopBack.ecommerce.services;


import com.shopBack.ecommerce.domains.Utilisateur;

import java.util.List;

public interface UtilisateurService {

    List<Utilisateur> findAll();
    Utilisateur findById(int id);
    Utilisateur save(Utilisateur user);
    void update(Utilisateur user, int id);
    void deleteById(int id);

}
