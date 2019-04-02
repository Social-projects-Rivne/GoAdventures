package io.softserve.goadventures.controllers;

import io.softserve.goadventures.models.Event;
import io.softserve.goadventures.services.EventService;
import io.softserve.goadventures.models.Gallery;
import io.softserve.goadventures.repositories.GalleryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@CrossOrigin
@RestController
@RequestMapping(value = "event/gallery")
public class GalleryController {
    private Logger logger = LoggerFactory.getLogger(GalleryController.class);
    @Autowired
    private GalleryRepository galleryRepository;
    @Autowired
    private EventService eventService;


    @PostMapping("/gallery/{eventId}")
    public ResponseEntity<String> createGallery(@PathVariable(value = "eventId") int eventId,
                                                @RequestBody Gallery gallery) {
        Event event = eventRepository.findById(eventId);
        HttpHeaders httpHeaders = new HttpHeaders();
        gallery.setEventId(event);
        galleryRepository.save(gallery);
        return ResponseEntity.ok().headers(httpHeaders).body("gallery created");
    }

    @GetMapping("/gallery/{eventId}")
    public Gallery getAllGalleryByEventId(@PathVariable(value = "eventId") int eventId) {
        Event event = eventRepository.findById(eventId);
        return galleryRepository.findByEventId(event.getId());
    }

    @PostMapping("/add-new/{eventId}")
    public ResponseEntity<?> addNewGallery(@PathVariable("eventId") int eventId) {
        // creates mock gallery
        Event event = eventService.getEventById(eventId);
        Set<String> imageUrls = new HashSet<>();
        imageUrls.add("https://www.railway-technology.com/wp-content/uploads/sites/24/2017/10/1-image-2.jpg");
        imageUrls.add("https://d2eip9sf3oo6c2.cloudfront.net/tags/images/000/000/256/full/nodejslogo.png");
        imageUrls.add("https://d2eip9sf3oo6c2.cloudfront.net/tags/images/000/000/256/full/nodejslogo.png");
        imageUrls.add("https://d2eip9sf3oo6c2.cloudfront.net/tags/images/000/000/256/full/nodejslogo.png");
        Gallery mockGallery = new Gallery(0, event, imageUrls, false);
        galleryRepository.save(mockGallery);
        event.setGallery(mockGallery);
        eventService.updateEvent(event);
        return ResponseEntity.ok().body(mockGallery);
    }
}