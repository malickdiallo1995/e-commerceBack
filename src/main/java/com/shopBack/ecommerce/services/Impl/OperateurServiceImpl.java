package com.shopBack.ecommerce.services.Impl;


import com.shopBack.ecommerce.domains.Operateur;
import com.shopBack.ecommerce.services.OperateurService;
import com.shopBack.ecommerce.repositories.OperateurRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OperateurServiceImpl implements OperateurService {

    private com.shopBack.ecommerce.repositories.OperateurRepository OperateurRepository;
    private static final Logger log = LoggerFactory.getLogger(OperateurServiceImpl.class);

    public OperateurServiceImpl() {

    }

    @Autowired
    public OperateurServiceImpl(OperateurRepository repository) {
        this.OperateurRepository = repository;
    }


    @Override
    public List<Operateur> findAll() {
        List<Operateur> list = new ArrayList<>();
        OperateurRepository.findAll().forEach(e -> list.add(e));
        return list;
    }

    @Override
    public Operateur findById(int id) {
        Optional<Operateur> optionalOperateur = OperateurRepository.findById(id);
        return optionalOperateur.get();
    }

    @Override
    public Operateur save(Operateur Operateur) {
        log.info("Operateur created with success.");
        return OperateurRepository.save(Operateur);
    }

    @Override
    public void update(Operateur Operateur, int id) {
        log.info("Operateur to be updated : id = {}", Operateur.getId());
        Operateur OperateurfromDb = this.findById(Operateur.getId());
        if (OperateurfromDb != null) {
            log.info("Operateur updated with success.");
            OperateurRepository.save(Operateur);
            Operateur.setId(id);
        } else {
            // AndriceOffer absent => no update
            log.info("Operateur with id = {} cannot found in the database", Operateur.getId());
        }
    }

    @Override
    public void deleteById(int id) {
        Operateur Operateur = this.findById(id);
        if(Operateur != null) {
            OperateurRepository.delete(Operateur);
            log.info("Operateur deleted with success.");
        }else {
            log.info("Error while deleting Operateur.");

        }
    }

}
