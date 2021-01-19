package com.shopBack.ecommerce.controllers;

import com.amazonaws.services.iot.model.ResourceNotFoundException;
import com.shopBack.ecommerce.domains.Client;
import com.shopBack.ecommerce.services.ClientService;
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
@RequestMapping("/api/clients")
public class ClientController {

    private ClientService clientService;
    private static final Logger log = LoggerFactory.getLogger(ClientController.class);
    private Client client;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Client>> getAllClients() {
        List<Client> clients = clientService.findAll();
        log.info("Client list size = {}", clients.size());
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientByID(@PathVariable int id) {
        try{
            Client client = clientService.findById(id);
            return ResponseEntity.ok(client);
        }catch(Exception e){
            log.info("Client with id = {} not found", id);
            return ResponseEntity.notFound().build();


        }
    }

    @PostMapping("/create")
    public ResponseEntity<Object> postClient(@Valid @RequestBody Client client) {
        this.client = clientService.save(client);
        if (this.client != null) {
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(this.client.getId())
                    .toUri();
            return ResponseEntity.created(location).build();
        } else {
            log.info("Client is already existing or null");
            return ResponseEntity.unprocessableEntity().build();
        }

    }


    @PutMapping("/{id}")
    public ResponseEntity<Object> updateClient(@PathVariable int id, @RequestBody Client client) {
        try {
            clientService.update(client, id);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable int id) {
        try {
        clientService.deleteById(id);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

}