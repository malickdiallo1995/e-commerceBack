package com.shopBack.ecommerce.services;


import com.shopBack.ecommerce.domains.Image;

import java.util.List;

public interface ImageService {

    List<Image> findAll();
    List<Image> findByIdTelephone(int idTelephone);
    Image findById(int id);
    Image save(Image image);
    void update(Image image, int id);
    void deleteById(int id);


}
