package com.shopBack.ecommerce.services.Impl;

import com.shopBack.ecommerce.domains.Forfait;
import com.shopBack.ecommerce.repositories.ForfaitRepository;
import com.shopBack.ecommerce.services.ForfaitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ForfaitServiceImpl implements ForfaitService {

    private com.shopBack.ecommerce.repositories.ForfaitRepository ForfaitRepository;
    private static final Logger log = LoggerFactory.getLogger(ForfaitServiceImpl.class);

    public ForfaitServiceImpl() {

    }

    @Autowired
    public ForfaitServiceImpl(ForfaitRepository repository) {
        this.ForfaitRepository = repository;
    }


    @Override
    public List<Forfait> findAll() {
        List<Forfait> list = new ArrayList<>();
        ForfaitRepository.findAll().forEach(e -> list.add(e));
        return list;
    }

    @Override
    public Forfait findById(int id) {
        Optional<Forfait> optionalForfait = ForfaitRepository.findById(id);
        return optionalForfait.get();
    }

    @Override
    public Forfait save(Forfait Forfait) {
        log.info("Forfait created with success.");
        return ForfaitRepository.save(Forfait);
    }

    @Override
    public void update(Forfait Forfait, int id) {
        log.info("Forfait to be updated : id = {}", Forfait.getId());
        Forfait ForfaitfromDb = this.findById(Forfait.getId());
        if (ForfaitfromDb != null) {
            log.info("Forfait updated with success.");
            ForfaitRepository.save(Forfait);
            Forfait.setId(id);
        } else {
            // AndriceOffer absent => no update
            log.info("Forfait with id = {} cannot found in the database", Forfait.getId());
        }
    }

    @Override
    public void deleteById(int id) {
        Forfait Forfait = this.findById(id);
        if(Forfait != null) {
            ForfaitRepository.delete(Forfait);
            log.info("Forfait deleted with success.");
        }else {
            log.info("Error while deleting Forfait.");

        }
    }
}
