package com.shopBack.ecommerce.services.Impl;


import com.shopBack.ecommerce.domains.Utilisateur;
import com.shopBack.ecommerce.services.UtilisateurService;
import com.shopBack.ecommerce.repositories.UtilisateurRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {
    private com.shopBack.ecommerce.repositories.UtilisateurRepository UtilisateurRepository;
    private static final Logger log = LoggerFactory.getLogger(UtilisateurServiceImpl.class);

    public UtilisateurServiceImpl() {

    }

    @Autowired
    public UtilisateurServiceImpl(UtilisateurRepository repository) {
        this.UtilisateurRepository = repository;
    }


    @Override
    public List<Utilisateur> findAll() {
        List<Utilisateur> list = new ArrayList<>();
        UtilisateurRepository.findAll().forEach(e -> list.add(e));
        return list;
    }

    @Override
    public Utilisateur findById(int id) {
        Optional<Utilisateur> optionalUtilisateur = UtilisateurRepository.findById(id);
        return optionalUtilisateur.get();
    }

    @Override
    public Utilisateur save(Utilisateur Utilisateur) {
        log.info("Utilisateur created with success.");
        return UtilisateurRepository.save(Utilisateur);
    }

    @Override
    public void update(Utilisateur Utilisateur, int id) {
        log.info("Utilisateur to be updated : id = {}", Utilisateur.getId());
        Utilisateur UtilisateurfromDb = this.findById(Utilisateur.getId());
        if (UtilisateurfromDb != null) {
            log.info("Utilisateur updated with success.");
            UtilisateurRepository.save(Utilisateur);
            Utilisateur.setId(id);
        } else {
            // AndriceOffer absent => no update
            log.info("Utilisateur with id = {} cannot found in the database", Utilisateur.getId());
        }
    }

    @Override
    public void deleteById(int id) {
        Utilisateur Utilisateur = this.findById(id);
        if(Utilisateur != null) {
            UtilisateurRepository.delete(Utilisateur);
            log.info("Utilisateur deleted with success.");
        }else {
            log.info("Error while deleting Utilisateur.");

        }
    }

}
