package com.shopBack.ecommerce.services.Impl;

import com.shopBack.ecommerce.domains.Client;
import com.shopBack.ecommerce.repositories.ClientRepository;
import com.shopBack.ecommerce.services.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    private com.shopBack.ecommerce.repositories.ClientRepository ClientRepository;
    private static final Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);

    public ClientServiceImpl() {

    }

    @Autowired
    public ClientServiceImpl(ClientRepository repository) {
        this.ClientRepository = repository;
    }


    @Override
    public List<Client> findAll() {
        List<Client> list = new ArrayList<>();
        ClientRepository.findAll().forEach(e -> list.add(e));
        return list;
    }

    @Override
    public Client findById(int id) {
        Optional<Client> optionalClient = ClientRepository.findById(id);
        return optionalClient.get();
    }

    @Override
    public Client save(Client Client) {
        log.info("Client created with success.");
        return ClientRepository.save(Client);
    }

    @Override
    public void update(Client Client, int id) {
        log.info("Client to be updated : id = {}", Client.getId());
        Client ClientfromDb = this.findById(Client.getId());
        if (ClientfromDb != null) {
            log.info("Client updated with success.");
            ClientRepository.save(Client);
            Client.setId(id);
        } else {
            // AndriceOffer absent => no update
            log.info("Client with id = {} cannot found in the database", Client.getId());
        }
    }

    @Override
    public void deleteById(int id) {
        Client Client = this.findById(id);
        if(Client != null) {
            ClientRepository.delete(Client);
            log.info("Client deleted with success.");
        }else {
            log.info("Error while deleting Client.");

        }
    }
}
