package com.shopBack.ecommerce.services.Impl;


import com.shopBack.ecommerce.domains.QuittanceCommande;
import com.shopBack.ecommerce.services.QuittanceCommandeService;
import com.shopBack.ecommerce.repositories.QuittanceCommandeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuittanceCommandeServiceImpl implements QuittanceCommandeService {

    private com.shopBack.ecommerce.repositories.QuittanceCommandeRepository QuittanceCommandeRepository;
    private static final Logger log = LoggerFactory.getLogger(QuittanceCommandeServiceImpl.class);

    public QuittanceCommandeServiceImpl() {

    }

    @Autowired
    public QuittanceCommandeServiceImpl(QuittanceCommandeRepository repository) {
        this.QuittanceCommandeRepository = repository;
    }


    @Override
    public List<QuittanceCommande> findAll() {
        List<QuittanceCommande> list = new ArrayList<>();
        QuittanceCommandeRepository.findAll().forEach(e -> list.add(e));
        return list;
    }

    @Override
    public QuittanceCommande findById(int id) {
        Optional<QuittanceCommande> optionalQuittanceCommande = QuittanceCommandeRepository.findById(id);
        return optionalQuittanceCommande.get();
    }

    @Override
    public QuittanceCommande save(QuittanceCommande QuittanceCommande) {
        log.info("QuittanceCommande created with success.");
        return QuittanceCommandeRepository.save(QuittanceCommande);
    }

    @Override
    public void update(QuittanceCommande QuittanceCommande, int id) {
        log.info("QuittanceCommande to be updated : id = {}", QuittanceCommande.getId());
        QuittanceCommande QuittanceCommandefromDb = this.findById(QuittanceCommande.getId());
        if (QuittanceCommandefromDb != null) {
            log.info("QuittanceCommande updated with success.");
            QuittanceCommandeRepository.save(QuittanceCommande);
            QuittanceCommande.setId(id);
        } else {
            // AndriceOffer absent => no update
            log.info("QuittanceCommande with id = {} cannot found in the database", QuittanceCommande.getId());
        }
    }

    @Override
    public void deleteById(int id) {
        QuittanceCommande QuittanceCommande = this.findById(id);
        if(QuittanceCommande != null) {
            QuittanceCommandeRepository.delete(QuittanceCommande);
            log.info("QuittanceCommande deleted with success.");
        }else {
            log.info("Error while deleting QuittanceCommande.");

        }
    }
}
