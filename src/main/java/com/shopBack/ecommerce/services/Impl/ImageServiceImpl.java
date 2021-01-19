package com.shopBack.ecommerce.services.Impl;


import com.shopBack.ecommerce.domains.Image;
import com.shopBack.ecommerce.repositories.ImageRepository;
import com.shopBack.ecommerce.services.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {

    private ImageRepository ImageRepository;
    private static final Logger log = LoggerFactory.getLogger(ImageServiceImpl.class);

    public ImageServiceImpl() {

    }

    @Autowired
    public ImageServiceImpl(ImageRepository repository) {
        this.ImageRepository = repository;
    }


    @Override
    public List<Image> findAll() {
        List<Image> list = new ArrayList<>();
        ImageRepository.findAll().forEach(e -> list.add(e));
        return list;
    }

    @Override
    public List<Image> findByIdTelephone(int idTelephone) {
        List<Image> list = new ArrayList<>();
        ImageRepository.findAll().forEach(e -> {
            if(e.getIdTelephone() == idTelephone){
                list.add(e);
            }
        });
        return list;
    }

    @Override
    public Image findById(int id) {
        Optional<Image> optionalImage = ImageRepository.findById(id);
        return optionalImage.get();
    }

    @Override
    public Image save(Image Image) {
        log.info("Image created with success.");
        return ImageRepository.save(Image);
    }

    @Override
    public void update(Image Image, int id) {
        log.info("Image to be updated : id = {}", Image.getId());
        Image ImagefromDb = this.findById(Image.getId());
        if (ImagefromDb != null) {
            log.info("Image updated with success.");
            ImageRepository.save(Image);
            Image.setId(id);
        } else {
            // AndriceOffer absent => no update
            log.info("Image with id = {} cannot found in the database", Image.getId());
        }
    }

    @Override
    public void deleteById(int id) {
        Image Image = this.findById(id);
        if(Image != null) {
            ImageRepository.delete(Image);
            log.info("Image deleted with success.");
        }else {
            log.info("Error while deleting Image.");

        }
    }

}
