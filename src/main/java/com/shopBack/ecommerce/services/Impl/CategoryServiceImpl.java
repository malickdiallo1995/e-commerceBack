package com.shopBack.ecommerce.services.Impl;


import com.shopBack.ecommerce.domains.Categorie;
import com.shopBack.ecommerce.repositories.CategorieRepository;
import com.shopBack.ecommerce.services.CategorieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategorieService {

    private CategorieRepository categorieRepository;
    private static final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);

    public CategoryServiceImpl() {

    }

    @Autowired
    public CategoryServiceImpl(CategorieRepository repository) {
        this.categorieRepository = repository;
    }


    @Override
    public List<Categorie> findAll() {
        List<Categorie> list = new ArrayList<>();
        categorieRepository.findAll().forEach(e -> list.add(e));
        return list;
    }

    @Override
    public Categorie findById(int id) {
        Optional<Categorie> optionalCategorie = categorieRepository.findById(id);
        return optionalCategorie.get();
    }

    @Override
    public Categorie save(Categorie Categorie) {
        log.info("Categorie created with success.");
        return categorieRepository.save(Categorie);
    }

    @Override
    public void update(Categorie Categorie, int id) {
        log.info("Categorie to be updated : id = {}", Categorie.getId());
        Categorie MarquefromDb = this.findById(Categorie.getId());
        if (MarquefromDb != null) {
            log.info("Categorie updated with success.");
            categorieRepository.save(Categorie);
            Categorie.setId(id);
        } else {
            // AndriceOffer absent => no update
            log.info("Categorie with id = {} cannot found in the database", Categorie.getId());
        }
    }

    @Override
    public void deleteById(int id) {
        Categorie categorie = this.findById(id);
        if(categorie != null) {
            categorieRepository.delete(categorie);
            log.info("Categorie deleted with success.");
        }else {
            log.info("Error while deleting Categorie.");

        }
    }

}
