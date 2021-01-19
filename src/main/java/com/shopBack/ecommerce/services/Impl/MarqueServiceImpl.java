package com.shopBack.ecommerce.services.Impl;


import com.shopBack.ecommerce.domains.Marque;
import com.shopBack.ecommerce.repositories.MarqueRepository;
import com.shopBack.ecommerce.services.MarqueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MarqueServiceImpl implements MarqueService {

    private com.shopBack.ecommerce.repositories.MarqueRepository MarqueRepository;
    private static final Logger log = LoggerFactory.getLogger(MarqueServiceImpl.class);

    public MarqueServiceImpl() {

    }

    @Autowired
    public MarqueServiceImpl(MarqueRepository repository) {
        this.MarqueRepository = repository;
    }


    @Override
    public List<Marque> findAll() {
        List<Marque> list = new ArrayList<>();
        MarqueRepository.findAll().forEach(e -> list.add(e));
        return list;
    }

    @Override
    public Marque findById(int id) {
        Optional<Marque> optionalMarque = MarqueRepository.findById(id);
        return optionalMarque.get();
    }

    @Override
    public Marque save(Marque Marque) {
        log.info("Marque created with success.");
        return MarqueRepository.save(Marque);
    }

    @Override
    public void update(Marque Marque, int id) {
        log.info("Marque to be updated : id = {}", Marque.getId());
        Marque MarquefromDb = this.findById(Marque.getId());
        if (MarquefromDb != null) {
            log.info("Marque updated with success.");
            MarqueRepository.save(Marque);
            Marque.setId(id);
        } else {
            // AndriceOffer absent => no update
            log.info("Marque with id = {} cannot found in the database", Marque.getId());
        }
    }

    @Override
    public void deleteById(int id) {
        Marque Marque = this.findById(id);
        if(Marque != null) {
            MarqueRepository.delete(Marque);
            log.info("Marque deleted with success.");
        }else {
            log.info("Error while deleting Marque.");

        }
    }

}
