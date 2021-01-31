package com.shopBack.ecommerce.controllers;

import com.amazonaws.services.iot.model.ResourceNotFoundException;
import com.shopBack.ecommerce.domains.Commande;
import com.shopBack.ecommerce.domains.LigneCommande;
import com.shopBack.ecommerce.services.CommandeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/commandes")
public class CommandeController {

    private CommandeService commandeService;
    private static final Logger log = LoggerFactory.getLogger(CommandeController.class);
    private Commande commande;

    @Autowired
    public CommandeController(CommandeService commandeService){
        this.commandeService = commandeService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Commande>> getCommandes() {
        List<Commande> commande = commandeService.findAll();
        log.info("Commande list size = {}", commande.size());
        return ResponseEntity.ok(commande);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Commande> getCommandeByID(@PathVariable int id) {
        try{
            Commande commande = commandeService.findById(id);
            return ResponseEntity.ok(commande);

        }catch (Exception e){

            log.info("Commande with id = {} not found", id);
            return ResponseEntity.notFound().build();

        }
    }

    @PostMapping("/create")
    public Commande postCommande(@Valid @RequestBody Commande commande) {
        this.commande = commandeService.save(commande);
        if (this.commande != null) {
            return this.commande;
        } else {
            log.info("commande is already existing or null");
            return null;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCommande(@PathVariable int id, @RequestBody Commande commande) {
        try {
            commandeService.update(commande, id);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommande(@PathVariable int id) {
        try {
            commandeService.deleteById(id);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
