package com.shopBack.ecommerce.controllers;

import com.amazonaws.services.iot.model.ResourceNotFoundException;
import com.shopBack.ecommerce.domains.QuittanceCommande;
import com.shopBack.ecommerce.services.QuittanceCommandeService;
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
@RequestMapping("/api/quittanceCommandes")
public class QuittanceCommandeController {

    private QuittanceCommandeService quittanceCommandeService;
    private static final Logger log = LoggerFactory.getLogger(QuittanceCommandeController.class);
    private QuittanceCommande quittanceCommande;

    @Autowired
    public QuittanceCommandeController(QuittanceCommandeService quittanceCommandeService) {
        this.quittanceCommandeService = quittanceCommandeService;
    }

    @GetMapping("/")
    public ResponseEntity<List<QuittanceCommande>> getAllQuittanceCommandes() {
        List<QuittanceCommande> quittanceCommandes = quittanceCommandeService.findAll();
        log.info("QuittanceCommande list size = {}", quittanceCommandes.size());
        return ResponseEntity.ok(quittanceCommandes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuittanceCommande> getQuittanceCommandeByID(@PathVariable int id) {
        try {
            QuittanceCommande quittanceCommande = quittanceCommandeService.findById(id);
            return ResponseEntity.ok(quittanceCommande);

        }catch (Exception e){

            log.info("QuittanceCommande with id = {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Object> postQuittanceCommande(@Valid @RequestBody QuittanceCommande quittanceCommande) {
        this.quittanceCommande = quittanceCommandeService.save(quittanceCommande);
        if (this.quittanceCommande != null) {
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(this.quittanceCommande.getId())
                    .toUri();
            return ResponseEntity.created(location).build();
        } else {
            log.info("QuittanceCommande is already existing or null");
            return ResponseEntity.unprocessableEntity().build();
        }

    }


    @PutMapping("/{id}")
    public ResponseEntity<Object> updateQuittanceCommande(@PathVariable int id, @RequestBody QuittanceCommande quittanceCommande) {
        try {
            quittanceCommandeService.update(quittanceCommande, id);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuittanceCommande(@PathVariable int id) {
        try {
        quittanceCommandeService.deleteById(id);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

}