package com.shopBack.ecommerce.services.Impl;


import com.shopBack.ecommerce.domains.Produit;
import com.shopBack.ecommerce.services.TelephoneService;
import com.shopBack.ecommerce.repositories.ProduitRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TelephoneServiceImpl implements TelephoneService {

    private ProduitRepository ProduitRepository;
    private static final Logger log = LoggerFactory.getLogger(TelephoneServiceImpl.class);

    public TelephoneServiceImpl() {

    }

    @Autowired
    public TelephoneServiceImpl(ProduitRepository repository) {
        this.ProduitRepository = repository;
    }


    @Override
    public List<Produit> findAll() {
        List<Produit> list = new ArrayList<>();
        ProduitRepository.findAll().forEach(e -> list.add(e));
        return list;
    }

    @Override
    public Produit findById(int id) {
        Optional<Produit> optionalTelephone = ProduitRepository.findById(id);
        return optionalTelephone.get();
    }

    @Override
    public Produit save(Produit Produit) {
        log.info("Produit created with success.");
        return ProduitRepository.save(Produit);
    }

    @Override
    public List<Produit> findByCategory(String category) {
        List<Produit> list = new ArrayList<>();
        ProduitRepository.findAll().forEach(e -> {
            if(e.getCategory() == category){
                list.add(e);
            }
        });
        return list;

    }

    @Override
    public void update(Produit Produit, int id) {
        log.info("Produit to be updated : id = {}", Produit.getId());
        Produit telephonefromDb = this.findById(Produit.getId());
        if (telephonefromDb != null) {
            log.info("Produit updated with success.");
            ProduitRepository.save(Produit);
            Produit.setId(id);
        } else {
            // AndriceOffer absent => no update
            log.info("Produit with id = {} cannot found in the database", Produit.getId());
        }
    }

    @Override
    public void deleteById(int id) {
        Produit Produit = this.findById(id);
        if(Produit != null) {
            ProduitRepository.delete(Produit);
            log.info("Produit deleted with success.");
        }else {
            log.info("Error while deleting Produit.");

        }
    }

}
