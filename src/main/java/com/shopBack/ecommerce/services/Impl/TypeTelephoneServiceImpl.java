package com.shopBack.ecommerce.services.Impl;


import com.shopBack.ecommerce.domains.TypeTelephone;
import com.shopBack.ecommerce.services.TypeTelephoneService;
import com.shopBack.ecommerce.repositories.TypeTelephoneRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TypeTelephoneServiceImpl implements TypeTelephoneService {

    private com.shopBack.ecommerce.repositories.TypeTelephoneRepository TypeTelephoneRepository;
    private static final Logger log = LoggerFactory.getLogger(TypeTelephoneServiceImpl.class);

    public TypeTelephoneServiceImpl() {

    }

    @Autowired
    public TypeTelephoneServiceImpl(TypeTelephoneRepository repository) {
        this.TypeTelephoneRepository = repository;
    }


    @Override
    public List<TypeTelephone> findAll() {
        List<TypeTelephone> list = new ArrayList<>();
        TypeTelephoneRepository.findAll().forEach(e -> list.add(e));
        return list;
    }

    @Override
    public TypeTelephone findById(int id) {
        Optional<TypeTelephone> optionalTypeTelephone = TypeTelephoneRepository.findById(id);
        return optionalTypeTelephone.get();
    }

    @Override
    public TypeTelephone save(TypeTelephone TypeTelephone) {
        log.info("TypeTelephone created with success.");
        return TypeTelephoneRepository.save(TypeTelephone);
    }

    @Override
    public void update(TypeTelephone TypeTelephone, int id) {
        log.info("TypeTelephone to be updated : id = {}", TypeTelephone.getId());
        TypeTelephone TypeTelephonefromDb = this.findById(TypeTelephone.getId());
        if (TypeTelephonefromDb != null) {
            log.info("TypeTelephone updated with success.");
            TypeTelephoneRepository.save(TypeTelephone);
            TypeTelephone.setId(id);
        } else {
            // AndriceOffer absent => no update
            log.info("TypeTelephone with id = {} cannot found in the database", TypeTelephone.getId());
        }
    }

    @Override
    public void deleteById(int id) {
        TypeTelephone TypeTelephone = this.findById(id);
        if(TypeTelephone != null) {
            TypeTelephoneRepository.delete(TypeTelephone);
            log.info("TypeTelephone deleted with success.");
        }else {
            log.info("Error while deleting TypeTelephone.");

        }
    }

}
