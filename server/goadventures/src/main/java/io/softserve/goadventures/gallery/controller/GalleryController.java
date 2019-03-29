package io.softserve.goadventures.gallery.controller;

import java.util.HashSet;
import java.util.Set;

import io.softserve.goadventures.event.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.softserve.goadventures.event.service.EventService;
import io.softserve.goadventures.gallery.model.Gallery;
import io.softserve.goadventures.gallery.repository.GalleryRepository;

@CrossOrigin
@RestController
@RequestMapping("gallery")
public class GalleryController {

  @Autowired
  private GalleryRepository galleryRepository;
  @Autowired
  private EventService eventService;

  @PostMapping("/add-new")
  public ResponseEntity<?> addNewGallery() {
    // creates mock gallery
    Event event = eventService.getEventById(8);
    Set<String> imageUrls = new HashSet<>();
    imageUrls.add("https://www.railway-technology.com/wp-content/uploads/sites/24/2017/10/1-image-2.jpg");
    imageUrls.add("https://2static1.fjcdn.com/thumbnails/comments/For+"
            + "those+who+want+it+_f5bc15a12dc73ef31d8adb5357552a60.jpg");
    Gallery mockGallery = new Gallery(0, event, imageUrls, false);
    galleryRepository.save(mockGallery);
    return ResponseEntity.ok().body(mockGallery);
  }

}