package com.shopBack.ecommerce.controllers;

import com.amazonaws.services.iot.model.ResourceNotFoundException;
import com.shopBack.ecommerce.domains.Marque;
import com.shopBack.ecommerce.services.MarqueService;
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
@RequestMapping("/api/marques")
public class MarqueController {

    private MarqueService marqueService;
    private static final Logger log = LoggerFactory.getLogger(MarqueController.class);
    private Marque marque;

    @Autowired
    public MarqueController(MarqueService marqueService) {
        this.marqueService = marqueService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Marque>> getAllMarques() {
        List<Marque> Marques = marqueService.findAll();
        log.info("Marque list size = {}", Marques.size());
        return ResponseEntity.ok(Marques);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Marque> getMarqueByID(@PathVariable int id) {
        try {
            Marque marque = marqueService.findById(id);
            return ResponseEntity.ok(marque);

        }catch (Exception e){
            log.info("Marque with id = {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Object> postMarque(@Valid @RequestBody Marque marque) {
        this.marque = marqueService.save(marque);
        if (this.marque != null) {
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(this.marque.getId())
                    .toUri();
            return ResponseEntity.created(location).build();
        } else {
            log.info("Marque is already existing or null");
            return ResponseEntity.unprocessableEntity().build();
        }

    }


    @PutMapping("/{id}")
    public ResponseEntity<Object> updateMarque(@PathVariable int id, @RequestBody Marque marque) {
        try {
            marqueService.update(marque, id);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMarque(@PathVariable int id) {
        try {
        marqueService.deleteById(id);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

}