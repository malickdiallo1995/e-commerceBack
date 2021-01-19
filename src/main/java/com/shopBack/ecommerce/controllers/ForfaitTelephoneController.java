package com.shopBack.ecommerce.controllers;

import com.amazonaws.services.iot.model.ResourceNotFoundException;
import com.shopBack.ecommerce.domains.ForfaitTelephone;
import com.shopBack.ecommerce.services.ForfaitTelephoneService;
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
@RequestMapping("/api/forfaitTelephones")
public class ForfaitTelephoneController {

    private ForfaitTelephoneService forfaitTelephoneService;
    private static final Logger log = LoggerFactory.getLogger(ForfaitTelephoneController.class);
    private ForfaitTelephone forfaitTelephone;

    @Autowired
    public ForfaitTelephoneController(ForfaitTelephoneService forfaitTelephoneService) {
        this.forfaitTelephoneService = forfaitTelephoneService;
    }

    @GetMapping("/")
    public ResponseEntity<List<ForfaitTelephone>> getAllForfaitTelephones() {
        List<ForfaitTelephone> forfaitTelephone = forfaitTelephoneService.findAll();
        log.info("ForfaitTelephone list size = {}", forfaitTelephone.size());
        return ResponseEntity.ok(forfaitTelephone);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ForfaitTelephone> getForfaitTelephoneByID(@PathVariable int id) {
        try{
            ForfaitTelephone forfaitTelephone = forfaitTelephoneService.findById(id);
            return ResponseEntity.ok(forfaitTelephone);


        }catch (Exception e){

            log.info("ForfaitTelephone with id = {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Object> postForfaitTelephone(@Valid @RequestBody ForfaitTelephone forfaitTelephone) {
        this.forfaitTelephone = forfaitTelephoneService.save(forfaitTelephone);
        if (this.forfaitTelephone != null) {
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(this.forfaitTelephone.getId())
                    .toUri();
            return ResponseEntity.created(location).build();
        } else {
            log.info("ForfaitTelephone is already existing or null");
            return ResponseEntity.unprocessableEntity().build();
        }

    }


    @PutMapping("/{id}")
    public ResponseEntity<Object> updateForfaitTelephone(@PathVariable int id, @RequestBody ForfaitTelephone forfaitTelephone) {
        try {
            forfaitTelephoneService.update(forfaitTelephone, id);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteForfaitTelephone(@PathVariable int id) {
        try {
        forfaitTelephoneService.deleteById(id);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

}