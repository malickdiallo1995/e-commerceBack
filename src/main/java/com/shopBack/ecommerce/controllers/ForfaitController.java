package com.shopBack.ecommerce.controllers;

import com.amazonaws.services.iot.model.ResourceNotFoundException;
import com.shopBack.ecommerce.domains.Forfait;
import com.shopBack.ecommerce.services.ForfaitService;
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
@RequestMapping("/api/forfaits")
public class ForfaitController {

    private ForfaitService forfaitService;
    private static final Logger log = LoggerFactory.getLogger(ForfaitController.class);
    private Forfait forfait;

    @Autowired
    public ForfaitController(ForfaitService forfaitService) {
        this.forfaitService = forfaitService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Forfait>> getAllForfaits() {
        List<Forfait> forfaits = forfaitService.findAll();
        log.info("Forfait list size = {}", forfaits.size());
        return ResponseEntity.ok(forfaits);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Forfait> getForfaitByID(@PathVariable int id) {

        try{
            Forfait forfait = forfaitService.findById(id);
            return ResponseEntity.ok(forfait);

        }catch (Exception e){

            log.info("Forfait with id = {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Object> postForfait(@Valid @RequestBody Forfait forfait) {
        this.forfait = forfaitService.save(forfait);
        if (this.forfait != null) {
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(this.forfait.getId())
                    .toUri();
            return ResponseEntity.created(location).build();
        } else {
            log.info("Forfait is already existing or null");
            return ResponseEntity.unprocessableEntity().build();
        }

    }


    @PutMapping("/{id}")
    public ResponseEntity<Object> updateForfait(@PathVariable int id, @RequestBody Forfait forfait) {
        try {
            forfaitService.update(forfait, id);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteForfait(@PathVariable int id) {
        try {
        forfaitService.deleteById(id);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

}