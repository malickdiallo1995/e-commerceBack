package com.shopBack.ecommerce.controllers;

import com.amazonaws.services.iot.model.ResourceNotFoundException;
import com.shopBack.ecommerce.domains.Utilisateur;
import com.shopBack.ecommerce.services.UtilisateurService;
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
@RequestMapping("/api/utilisateurs")
public class UtilisateurController {

    private UtilisateurService utilisateurService;
    private static final Logger log = LoggerFactory.getLogger(UtilisateurController.class);
    private Utilisateur utilisateur;

    @Autowired
    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Utilisateur>> getAllUtilisateurs() {
        List<Utilisateur> utilisateurs = utilisateurService.findAll();
        log.info("Utilisateur list size = {}", utilisateurs.size());
        return ResponseEntity.ok(utilisateurs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Utilisateur> getUtilisateurByID(@PathVariable int id) {
        try {
            Utilisateur utilisateur = utilisateurService.findById(id);
            return ResponseEntity.ok(utilisateur);


        }catch (Exception e){

            log.info("Utilisateur with id = {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Object> postUtilisateur(@Valid @RequestBody Utilisateur utilisateur) {
        this.utilisateur = utilisateurService.save(utilisateur);
        if (this.utilisateur != null) {
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(this.utilisateur.getId())
                    .toUri();
            return ResponseEntity.created(location).build();
        } else {
            log.info("Utilisateur is already existing or null");
            return ResponseEntity.unprocessableEntity().build();
        }

    }


    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUtilisateur(@PathVariable int id, @RequestBody Utilisateur utilisateur) {
        try {
            utilisateurService.update(utilisateur, id);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUtilisateur(@PathVariable int id) {
        try {
        utilisateurService.deleteById(id);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

}