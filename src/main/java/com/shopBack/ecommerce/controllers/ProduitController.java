package com.shopBack.ecommerce.controllers;

import com.amazonaws.services.iot.model.ResourceNotFoundException;
import com.shopBack.ecommerce.domains.Image;
import com.shopBack.ecommerce.domains.Marque;
import com.shopBack.ecommerce.domains.Produit;
import com.shopBack.ecommerce.services.ImageService;
import com.shopBack.ecommerce.services.MarqueService;
import com.shopBack.ecommerce.services.TelephoneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/produits")
public class ProduitController {


    private TelephoneService telephoneService;
    private ImageService imageService;
    private MarqueService marqueService;
    private static final Logger log = LoggerFactory.getLogger(ProduitController.class);
    private Produit produit;


    @Autowired
    public ProduitController(TelephoneService telephoneService, ImageService imageService, MarqueService marqueService) {
        this.telephoneService = telephoneService;
        this.imageService = imageService;
        this.marqueService = marqueService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Produit>> getAllTelephones() {
        List<Produit> produits = telephoneService.findAll();
        List<Produit> telephonesWithImages = new ArrayList<>();
        log.info("Produit list size = {}", produits.size());
        for(int i = 0; i< produits.size(); i++){
            List<Image> listImage = this.imageService.findByIdTelephone(produits.get(i).getId());
            Marque marque = this.marqueService.findById(produits.get(i).getIdMarque());
            produits.get(i).setImages(listImage);
            produits.get(i).setBrand(marque.getName());
            telephonesWithImages.add(produits.get(i));
        }
        System.out.println(telephonesWithImages.toString());
        return ResponseEntity.ok(telephonesWithImages);
    }





    @GetMapping("/{id}")
    public ResponseEntity<Produit> getTelephoneByID(@PathVariable int id) {
        try {
            Produit produit = telephoneService.findById(id);
            List<Image> listImage = this.imageService.findByIdTelephone(id);
            Marque marque = this.marqueService.findById(produit.getIdMarque());
            produit.setBrand(marque.getName());
            produit.setImages(listImage);
            return ResponseEntity.ok(produit);

        }catch (Exception e){

            log.info("Produit with id = {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Produit>> getTelephonesByCategory(@PathVariable String category) {
        List<Produit> produits = telephoneService.findByCategory(category);
        List<Produit> telephonesWithImages = new ArrayList<>();
        log.info("Produit list size = {}", produits.size());
        for(int i = 0; i< produits.size(); i++){
            List<Image> listImage = this.imageService.findByIdTelephone(produits.get(i).getId());
            Marque marque = this.marqueService.findById(produits.get(i).getIdMarque());
            produits.get(i).setBrand(marque.getName());
            produits.get(i).setImages(listImage);
            telephonesWithImages.add(produits.get(i));
        }
        System.out.println(telephonesWithImages.toString());
        return ResponseEntity.ok(telephonesWithImages);
    }


    @PostMapping("/create")
    public ResponseEntity<Object> postTelephone(@Valid @RequestBody Produit produit) {
        this.produit = telephoneService.save(produit);
        if (this.produit != null) {
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(this.produit.getId())
                    .toUri();
            return ResponseEntity.created(location).build();
        } else {
            log.info("Produit is already existing or null");
            return ResponseEntity.unprocessableEntity().build();
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTelephone(@PathVariable int id, @RequestBody Produit produit) {
        try {
            telephoneService.update(produit, id);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTelephone(@PathVariable int id) {
        try {
        telephoneService.deleteById(id);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }



}