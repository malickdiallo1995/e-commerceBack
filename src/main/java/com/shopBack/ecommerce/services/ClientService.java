package com.shopBack.ecommerce.services;

import com.shopBack.ecommerce.domains.Client;

import java.util.List;

public interface ClientService {

    List<Client> findAll();
    Client findById(int id);
    Client save(Client client);
    void update(Client client, int id);
    void deleteById(int id);
}
