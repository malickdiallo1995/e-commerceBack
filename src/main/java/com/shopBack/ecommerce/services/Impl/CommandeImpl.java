package com.shopBack.ecommerce.services.Impl;

import com.shopBack.ecommerce.domains.Commande;
import com.shopBack.ecommerce.domains.LigneCommande;
import com.shopBack.ecommerce.repositories.CommandeRepository;
import com.shopBack.ecommerce.services.CommandeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommandeImpl implements CommandeService {

    private com.shopBack.ecommerce.repositories.CommandeRepository commandeRepository;
    private static final Logger log = LoggerFactory.getLogger(LigneCommandeServiceImpl.class);


    private CommandeImpl(){

    }
    @Autowired
    private CommandeImpl(CommandeRepository commandeRepository){
        this.commandeRepository = commandeRepository;

    }
    @Override
    public List<Commande> findAll() {
        List<Commande> commandeList = new ArrayList<>();
        commandeRepository.findAll().forEach(e -> commandeList.add(e));
        return commandeList;
    }

    @Override
    public Commande findById(int id) {
        Optional<Commande> optionalCommande= commandeRepository.findById(id);
        return optionalCommande.get();
    }

    @Override
    public Commande save(Commande commande) {
        log.info("Commande created with success.");
        return commandeRepository.save(commande);
    }

    @Override
    public void update(Commande commande, int id) {
        log.info("Commande to be updated : id = {}", commande.getId());
        Commande CommandeDB = this.findById(commande.getId());
        if (CommandeDB != null) {
            log.info("Commande updated with success.");
            commandeRepository.save(commande);
            commande.setId(id);
        } else {
            log.info("commande with id = {} cannot found in the database", commande.getId());
        }
    }

    @Override
    public void deleteById(int id) {
        Commande commande = this.findById(id);
        if(commande != null) {
            commandeRepository.delete(commande);
            log.info("commande deleted with success.");
        }else {
            log.info("Error while deleting commande.");

        }
    }
}
