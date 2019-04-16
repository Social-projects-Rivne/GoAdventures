package io.softserve.goadventures.services;

import io.softserve.goadventures.models.Gallery;
import io.softserve.goadventures.repositories.GalleryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GalleryCRUDService {
    private final GalleryRepository galleryRepository;

    @Autowired
    public GalleryCRUDService(GalleryRepository galleryRepository) {
        this.galleryRepository = galleryRepository;
    }

    public Gallery updateGallery(Gallery mutatedGallery) {
        return galleryRepository.save( mutatedGallery );
    }

}
