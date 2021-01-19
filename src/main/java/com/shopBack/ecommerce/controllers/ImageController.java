package com.shopBack.ecommerce.controllers;

import com.amazonaws.services.iot.model.ResourceNotFoundException;
import com.shopBack.ecommerce.domains.Image;
import com.shopBack.ecommerce.services.ImageService;
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
@RequestMapping("/api/images")
public class ImageController {

    private ImageService imageService;
    private static final Logger log = LoggerFactory.getLogger(ImageController.class);
    private Image image;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Image>> getAllImages() {
        List<Image> images = imageService.findAll();
        log.info("Image list size = {}", images.size());
        return ResponseEntity.ok(images);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Image> getImageByID(@PathVariable int id) {
        try{
            Image image = imageService.findById(id);
            return ResponseEntity.ok(image);

        }catch (Exception e){

            log.info("Image with id = {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Object> postImage(@Valid @RequestBody Image image) {
        this.image = imageService.save(image);
        if (this.image != null) {
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(this.image.getId())
                    .toUri();
            return ResponseEntity.created(location).build();
        } else {
            log.info("Image is already existing or null");
            return ResponseEntity.unprocessableEntity().build();
        }

    }


    @PutMapping("/{id}")
    public ResponseEntity<Object> updateImage(@PathVariable int id, @RequestBody Image image) {
        try {
            imageService.update(image, id);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable int id) {
        try {
        imageService.deleteById(id);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

}