package com.shopBack.ecommerce.controllers;

import com.amazonaws.services.iot.model.ResourceNotFoundException;
import com.shopBack.ecommerce.domains.Offre;
import com.shopBack.ecommerce.services.OffreService;
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
@RequestMapping("/api/offres")
public class OffreController {

    private OffreService offreService;
    private static final Logger log = LoggerFactory.getLogger(OffreController.class);
    private Offre offre;

    @Autowired
    public OffreController(OffreService offreService) {
        this.offreService = offreService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Offre>> getAllOffres() {
        List<Offre> offres = offreService.findAll();
        log.info("Offre list size = {}", offres.size());
        return ResponseEntity.ok(offres);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Offre> getOffreByID(@PathVariable int id) {

        try {
            Offre offre = offreService.findById(id);
            return ResponseEntity.ok(offre);

        }catch (Exception e){

            log.info("Offre with id = {} not found", id);
            return ResponseEntity.notFound().build();

        }

    }

    @PostMapping("/create")
    public ResponseEntity<Object> postOffre(@Valid @RequestBody Offre offre) {
        this.offre = offreService.save(offre);
        if (this.offre != null) {
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(this.offre.getId())
                    .toUri();
            return ResponseEntity.created(location).build();
        } else {
            log.info("Offre is already existing or null");
            return ResponseEntity.unprocessableEntity().build();
        }

    }


    @PutMapping("/{id}")
    public ResponseEntity<Object> updateOffre(@PathVariable int id, @RequestBody Offre offre) {
        try {
            offreService.update(offre, id);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOffre(@PathVariable int id) {
        try {
        offreService.deleteById(id);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

}