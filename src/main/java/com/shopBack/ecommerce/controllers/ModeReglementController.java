package com.shopBack.ecommerce.controllers;

import com.amazonaws.services.iot.model.ResourceNotFoundException;
import com.shopBack.ecommerce.domains.ModeReglement;
import com.shopBack.ecommerce.services.ModeReglementService;
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
@RequestMapping("/api/modeReglements")
public class ModeReglementController {

    private ModeReglementService modeReglementService;
    private static final Logger log = LoggerFactory.getLogger(ModeReglementController.class);
    private ModeReglement modeReglement;

    @Autowired
    public ModeReglementController(ModeReglementService modeReglementService) {
        this.modeReglementService = modeReglementService;
    }

    @GetMapping("/")
    public ResponseEntity<List<ModeReglement>> getAllModeReglements() {
        List<ModeReglement> modeReglements = modeReglementService.findAll();
        log.info("ModeReglement list size = {}", modeReglements.size());
        return ResponseEntity.ok(modeReglements);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModeReglement> getModeReglementByID(@PathVariable int id) {
        try {
            ModeReglement modeReglement = modeReglementService.findById(id);
            return ResponseEntity.ok(modeReglement);

        }catch (Exception e){

            log.info("ModeReglement with id = {} not found", id);
            return ResponseEntity.notFound().build();

        }

    }

    @PostMapping("/create")
    public ResponseEntity<Object> postModeReglement(@Valid @RequestBody ModeReglement modeReglement) {
        this.modeReglement = modeReglementService.save(modeReglement);
        if (this.modeReglement != null) {
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(this.modeReglement.getId())
                    .toUri();
            return ResponseEntity.created(location).build();
        } else {
            log.info("ModeReglement is already existing or null");
            return ResponseEntity.unprocessableEntity().build();
        }

    }


    @PutMapping("/{id}")
    public ResponseEntity<Object> updateModeReglement(@PathVariable int id, @RequestBody ModeReglement modeReglement) {
        try {
            modeReglementService.update(modeReglement, id);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteModeReglement(@PathVariable int id) {
        try {
        modeReglementService.deleteById(id);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

}