package com.shopBack.ecommerce.services.Impl;


import com.shopBack.ecommerce.domains.ModeReglement;
import com.shopBack.ecommerce.services.ModeReglementService;
import com.shopBack.ecommerce.repositories.ModeReglementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ModeReglementServiceImpl implements ModeReglementService {

    private com.shopBack.ecommerce.repositories.ModeReglementRepository ModeReglementRepository;
    private static final Logger log = LoggerFactory.getLogger(ModeReglementServiceImpl.class);

    public ModeReglementServiceImpl() {

    }

    @Autowired
    public ModeReglementServiceImpl(ModeReglementRepository repository) {
        this.ModeReglementRepository = repository;
    }


    @Override
    public List<ModeReglement> findAll() {
        List<ModeReglement> list = new ArrayList<>();
        ModeReglementRepository.findAll().forEach(e -> list.add(e));
        return list;
    }

    @Override
    public ModeReglement findById(int id) {
        Optional<ModeReglement> optionalLigneCommande= ModeReglementRepository.findById(id);
        return optionalLigneCommande.get();
    }

    @Override
    public ModeReglement save(ModeReglement ModeReglement) {
        log.info("ModeReglement created with success.");
        return ModeReglementRepository.save(ModeReglement);
    }

    @Override
    public void update(ModeReglement ModeReglement, int id) {
        log.info("ModeReglement to be updated : id = {}", ModeReglement.getId());
        ModeReglement ModeReglementfromDb = this.findById(ModeReglement.getId());
        if (ModeReglementfromDb != null) {
            log.info("ModeReglement updated with success.");
            ModeReglementRepository.save(ModeReglement);
            ModeReglement.setId(id);
        } else {
            // AndriceOffer absent => no update
            log.info("ModeReglement with id = {} cannot found in the database", ModeReglement.getId());
        }
    }

    @Override
    public void deleteById(int id) {
        ModeReglement ModeReglement = this.findById(id);
        if(ModeReglement != null) {
            ModeReglementRepository.delete(ModeReglement);
            log.info("ModeReglement deleted with success.");
        }else {
            log.info("Error while deleting ModeReglement.");

        }
    }

}
