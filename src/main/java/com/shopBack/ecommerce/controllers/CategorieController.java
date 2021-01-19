package com.shopBack.ecommerce.controllers;

import com.amazonaws.services.iot.model.ResourceNotFoundException;
import com.shopBack.ecommerce.domains.Categorie;
import com.shopBack.ecommerce.services.CategorieService;
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
@RequestMapping("/api/categories")
public class CategorieController {

    private CategorieService categorieService;
    private static final Logger log = LoggerFactory.getLogger(CategorieController.class);
    private Categorie categorie;

    @Autowired
    public CategorieController(CategorieService categorieService) {
        this.categorieService = categorieService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Categorie>> getAllCategories() {
        List<Categorie> Categories = categorieService.findAll();
        log.info("Categorie list size = {}", Categories.size());
        return ResponseEntity.ok(Categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categorie> getCategorieByID(@PathVariable int id) {
        try {
            Categorie categorie = categorieService.findById(id);
            return ResponseEntity.ok(categorie);

        }catch (Exception e){
            log.info("Categorie with id = {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Object> postCategorie(@Valid @RequestBody Categorie categorie) {
        this.categorie = categorieService.save(categorie);
        if (this.categorie != null) {
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(this.categorie.getId())
                    .toUri();
            return ResponseEntity.created(location).build();
        } else {
            log.info("Categorie is already existing or null");
            return ResponseEntity.unprocessableEntity().build();
        }

    }


    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCategorie(@PathVariable int id, @RequestBody Categorie categorie) {
        try {
            categorieService.update(categorie, id);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategorie(@PathVariable int id) {
        try {
        categorieService.deleteById(id);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

}