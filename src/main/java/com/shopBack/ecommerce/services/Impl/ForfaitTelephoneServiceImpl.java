package com.shopBack.ecommerce.services.Impl;


import com.shopBack.ecommerce.domains.ForfaitTelephone;

import com.shopBack.ecommerce.repositories.ForfaitTelephoneRepository;
import com.shopBack.ecommerce.services.ForfaitTelephoneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ForfaitTelephoneServiceImpl implements ForfaitTelephoneService {

    private com.shopBack.ecommerce.repositories.ForfaitTelephoneRepository ForfaitTelephoneRepository;
    private static final Logger log = LoggerFactory.getLogger(ForfaitTelephoneServiceImpl.class);

    public ForfaitTelephoneServiceImpl() {

    }

    @Autowired
    public ForfaitTelephoneServiceImpl(ForfaitTelephoneRepository repository) {
        this.ForfaitTelephoneRepository = repository;
    }


    @Override
    public List<ForfaitTelephone> findAll() {
        List<ForfaitTelephone> list = new ArrayList<>();
        ForfaitTelephoneRepository.findAll().forEach(e -> list.add(e));
        return list;
    }

    @Override
    public ForfaitTelephone findById(int id) {
        Optional<ForfaitTelephone> optionalForfaitTelephone = ForfaitTelephoneRepository.findById(id);
        return optionalForfaitTelephone.get();
    }

    @Override
    public ForfaitTelephone save(ForfaitTelephone ForfaitTelephone) {
        log.info("ForfaitTelephone created with success.");
        return ForfaitTelephoneRepository.save(ForfaitTelephone);
    }

    @Override
    public void update(ForfaitTelephone ForfaitTelephone, int id) {
        log.info("ForfaitTelephone to be updated : id = {}", ForfaitTelephone.getId());
        ForfaitTelephone ForfaitTelephonefromDb = this.findById(ForfaitTelephone.getId());
        if (ForfaitTelephonefromDb != null) {
            log.info("ForfaitTelephone updated with success.");
            ForfaitTelephoneRepository.save(ForfaitTelephone);
            ForfaitTelephone.setId(id);
        } else {
            // AndriceOffer absent => no update
            log.info("ForfaitTelephone with id = {} cannot found in the database", ForfaitTelephone.getId());
        }
    }

    @Override
    public void deleteById(int id) {
        ForfaitTelephone ForfaitTelephone = this.findById(id);
        if(ForfaitTelephone != null) {
            ForfaitTelephoneRepository.delete(ForfaitTelephone);
            log.info("ForfaitTelephone deleted with success.");
        }else {
            log.info("Error while deleting ForfaitTelephone.");

        }
    }
}
