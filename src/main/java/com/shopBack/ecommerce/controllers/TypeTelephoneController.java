package com.shopBack.ecommerce.controllers;

import com.amazonaws.services.iot.model.ResourceNotFoundException;
import com.shopBack.ecommerce.domains.TypeTelephone;
import com.shopBack.ecommerce.services.TypeTelephoneService;
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
@RequestMapping("/api/typeTelephones")
public class TypeTelephoneController {

    private TypeTelephoneService typeTelephoneService;
    private static final Logger log = LoggerFactory.getLogger(TypeTelephoneController.class);
    private TypeTelephone typeTelephone;

    @Autowired
    public TypeTelephoneController(TypeTelephoneService typeTelephoneService) {
        this.typeTelephoneService = typeTelephoneService;
    }

    @GetMapping("/")
    public ResponseEntity<List<TypeTelephone>> getAllTypeTelephones() {
        List<TypeTelephone> typeTelephones = typeTelephoneService.findAll();
        log.info("TypeTelephone list size = {}", typeTelephones.size());
        return ResponseEntity.ok(typeTelephones);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TypeTelephone> getTypeTelephoneByID(@PathVariable int id) {
        try {
            TypeTelephone typeTelephone = typeTelephoneService.findById(id);
            return ResponseEntity.ok(typeTelephone);

        }catch (Exception e){

            log.info("TypeTelephone with id = {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Object> postTypeTelephone(@Valid @RequestBody TypeTelephone typeTelephone) {
        this.typeTelephone = typeTelephoneService.save(typeTelephone);
        if (this.typeTelephone != null) {
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(this.typeTelephone.getId())
                    .toUri();
            return ResponseEntity.created(location).build();
        } else {
            log.info("TypeTelephone is already existing or null");
            return ResponseEntity.unprocessableEntity().build();
        }

    }


    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTypeTelephone(@PathVariable int id, @RequestBody TypeTelephone typeTelephone) {
        try {
            typeTelephoneService.update(typeTelephone, id);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTypeTelephone(@PathVariable int id) {
        try {
        typeTelephoneService.deleteById(id);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

}