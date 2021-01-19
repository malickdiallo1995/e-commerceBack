package com.shopBack.ecommerce.controllers;

import com.amazonaws.services.iot.model.ResourceNotFoundException;
import com.shopBack.ecommerce.domains.Operateur;
import com.shopBack.ecommerce.services.OperateurService;
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
@RequestMapping("/api/operateurs")
public class OperateurController {

    private OperateurService operateurService;
    private static final Logger log = LoggerFactory.getLogger(OperateurController.class);
    private Operateur operateur;

    @Autowired
    public OperateurController(OperateurService operateurService) {
        this.operateurService = operateurService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Operateur>> getAlloperateurs() {
        List<Operateur> operateurs = operateurService.findAll();
        log.info("Operateur list size = {}", operateurs.size());
        return ResponseEntity.ok(operateurs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Operateur> getOperateurByID(@PathVariable int id) {
        try {
            Operateur operateur = operateurService.findById(id);
            return ResponseEntity.ok(operateur);

        }catch (Exception e){
            log.info("Operateur with id = {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Object> postOperateur(@Valid @RequestBody Operateur operateur) {
        this.operateur = operateurService.save(operateur);
        if (this.operateur != null) {
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(this.operateur.getId())
                    .toUri();
            return ResponseEntity.created(location).build();
        } else {
            log.info("Operateur is already existing or null");
            return ResponseEntity.unprocessableEntity().build();
        }

    }


    @PutMapping("/{id}")
    public ResponseEntity<Object> updateOperateur(@PathVariable int id, @RequestBody Operateur operateur) {
        try {
            operateurService.update(operateur, id);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOperateur(@PathVariable int id) {
        try {
        operateurService.deleteById(id);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

}