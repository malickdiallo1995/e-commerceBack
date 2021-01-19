package com.shopBack.ecommerce.services.Impl;


import com.shopBack.ecommerce.domains.Reglement;
import com.shopBack.ecommerce.services.ReglementService;
import com.shopBack.ecommerce.repositories.ReglementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReglementServiceImpl implements ReglementService {

    private com.shopBack.ecommerce.repositories.ReglementRepository ReglementRepository;
    private static final Logger log = LoggerFactory.getLogger(ReglementServiceImpl.class);

    public ReglementServiceImpl() {

    }

    @Autowired
    public ReglementServiceImpl(ReglementRepository repository) {
        this.ReglementRepository = repository;
    }


    @Override
    public List<Reglement> findAll() {
        List<Reglement> list = new ArrayList<>();
        ReglementRepository.findAll().forEach(e -> list.add(e));
        return list;
    }

    @Override
    public Reglement findById(int id) {
        Optional<Reglement> optionalReglement = ReglementRepository.findById(id);
        return optionalReglement.get();
    }

    @Override
    public Reglement save(Reglement Reglement) {
        log.info("Reglement created with success.");
        return ReglementRepository.save(Reglement);
    }

    @Override
    public void update(Reglement Reglement, int id) {
        log.info("Reglement to be updated : id = {}", Reglement.getId());
        Reglement ReglementfromDb = this.findById(Reglement.getId());
        if (ReglementfromDb != null) {
            log.info("Reglement updated with success.");
            ReglementRepository.save(Reglement);
            Reglement.setId(id);
        } else {
            // AndriceOffer absent => no update
            log.info("Reglement with id = {} cannot found in the database", Reglement.getId());
        }
    }

    @Override
    public void deleteById(int id) {
        Reglement Reglement = this.findById(id);
        if(Reglement != null) {
            ReglementRepository.delete(Reglement);
            log.info("Reglement deleted with success.");
        }else {
            log.info("Error while deleting Reglement.");

        }
    }

}
