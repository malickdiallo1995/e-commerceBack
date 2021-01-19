package com.shopBack.ecommerce.services.Impl;


import com.shopBack.ecommerce.domains.Offre;
import com.shopBack.ecommerce.services.OffreService;
import com.shopBack.ecommerce.repositories.OffreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OffreServiceImpl implements OffreService {

    private com.shopBack.ecommerce.repositories.OffreRepository OffreRepository;
    private static final Logger log = LoggerFactory.getLogger(OffreServiceImpl.class);

    public OffreServiceImpl() {

    }

    @Autowired
    public OffreServiceImpl(OffreRepository repository) {
        this.OffreRepository = repository;
    }


    @Override
    public List<Offre> findAll() {
        List<Offre> list = new ArrayList<>();
        OffreRepository.findAll().forEach(e -> list.add(e));
        return list;
    }

    @Override
    public Offre findById(int id) {
        Optional<Offre> optionalOffre = OffreRepository.findById(id);
        return optionalOffre.get();
    }

    @Override
    public Offre save(Offre Offre) {
        log.info("Offre created with success.");
        return OffreRepository.save(Offre);
    }

    @Override
    public void update(Offre Offre, int id) {
        log.info("Offre to be updated : id = {}", Offre.getId());
        Offre OffrefromDb = this.findById(Offre.getId());
        if (OffrefromDb != null) {
            log.info("Offre updated with success.");
            OffreRepository.save(Offre);
            Offre.setId(id);
        } else {
            // AndriceOffer absent => no update
            log.info("Offre with id = {} cannot found in the database", Offre.getId());
        }
    }

    @Override
    public void deleteById(int id) {
        Offre Offre = this.findById(id);
        if(Offre != null) {
            OffreRepository.delete(Offre);
            log.info("Offre deleted with success.");
        }else {
            log.info("Error while deleting Offre.");

        }
    }
}
