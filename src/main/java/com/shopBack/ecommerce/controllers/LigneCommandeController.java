package com.shopBack.ecommerce.controllers;

import com.amazonaws.services.iot.model.ResourceNotFoundException;
import com.shopBack.ecommerce.domains.LigneCommande;
import com.shopBack.ecommerce.services.LigneCommandeService;
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
@RequestMapping("/api/ligneCommandes")
public class LigneCommandeController {

    private LigneCommandeService ligneCommandeService;
    private static final Logger log = LoggerFactory.getLogger(LigneCommandeController.class);
    private LigneCommande ligneCommande;

    @Autowired
    public LigneCommandeController(LigneCommandeService ligneCommandeService) {
        this.ligneCommandeService = ligneCommandeService;
    }

    @GetMapping("/")
    public ResponseEntity<List<LigneCommande>> getAllLigneCommandes() {
        List<LigneCommande> ligneCommande = ligneCommandeService.findAll();
        log.info("LigneCommande list size = {}", ligneCommande.size());
        return ResponseEntity.ok(ligneCommande);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LigneCommande> getLigneCommandeByID(@PathVariable int id) {
        try{
            LigneCommande ligneCommande = ligneCommandeService.findById(id);
            return ResponseEntity.ok(ligneCommande);

        }catch (Exception e){

            log.info("LigneCommande with id = {} not found", id);
            return ResponseEntity.notFound().build();

        }
    }

    @PostMapping("/create")
    public LigneCommande postLigneCommande(@Valid @RequestBody LigneCommande ligneCommande) {
        this.ligneCommande = ligneCommandeService.save(ligneCommande);
        if (this.ligneCommande != null) {
            return this.ligneCommande;
        } else {
            log.info("LigneCommande is already existing or null");
            return null;
        }

    }


    @PutMapping("/{id}")
    public ResponseEntity<Object> updateLigneCommande(@PathVariable int id, @RequestBody LigneCommande ligneCommande) {
        try {
            ligneCommandeService.update(ligneCommande, id);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLigneCommande(@PathVariable int id) {
        try {
        ligneCommandeService.deleteById(id);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

}