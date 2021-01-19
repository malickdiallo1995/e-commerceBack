package com.shopBack.ecommerce.services.Impl;



import com.shopBack.ecommerce.domains.LigneCommande;
import com.shopBack.ecommerce.services.LigneCommandeService;
import com.shopBack.ecommerce.repositories.LigneCommandeRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LigneCommandeServiceImpl implements LigneCommandeService {

    private com.shopBack.ecommerce.repositories.LigneCommandeRepository LigneCommandeRepository;
    private static final Logger log = LoggerFactory.getLogger(LigneCommandeServiceImpl.class);

    public LigneCommandeServiceImpl() {

    }

    @Autowired
    public LigneCommandeServiceImpl(LigneCommandeRepository repository) {
        this.LigneCommandeRepository = repository;
    }


    @Override
    public List<LigneCommande> findAll() {
        List<LigneCommande> list = new ArrayList<>();
        LigneCommandeRepository.findAll().forEach(e -> list.add(e));
        return list;
    }

    @Override
    public LigneCommande findById(int id) {
        Optional<LigneCommande> optionalLigneCommande= LigneCommandeRepository.findById(id);
        return optionalLigneCommande.get();
    }

    @Override
    public LigneCommande save(LigneCommande LigneCommande) {
        log.info("LigneCommande created with success.");
        return LigneCommandeRepository.save(LigneCommande);
    }

    @Override
    public void update(LigneCommande LigneCommande, int id) {
        log.info("LigneCommande to be updated : id = {}", LigneCommande.getId());
        LigneCommande LigneCommandefromDb = this.findById(LigneCommande.getId());
        if (LigneCommandefromDb != null) {
            log.info("LigneCommande updated with success.");
            LigneCommandeRepository.save(LigneCommande);
            LigneCommande.setId(id);
        } else {
            // AndriceOffer absent => no update
            log.info("LigneCommande with id = {} cannot found in the database", LigneCommande.getId());
        }
    }

    @Override
    public void deleteById(int id) {
        LigneCommande LigneCommande = this.findById(id);
        if(LigneCommande != null) {
            LigneCommandeRepository.delete(LigneCommande);
            log.info("LigneCommande deleted with success.");
        }else {
            log.info("Error while deleting LigneCommande.");

        }
    }
}
