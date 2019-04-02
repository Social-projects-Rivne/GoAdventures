package io.softserve.goadventures.controllers;

import io.softserve.goadventures.models.Event;
import io.softserve.goadventures.services.EventService;
import io.softserve.goadventures.models.Gallery;
import io.softserve.goadventures.repositories.GalleryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@CrossOrigin
@RestController
@RequestMapping("gallery")
public class GalleryController {
    @Autowired
    private GalleryRepository galleryRepository;
    @Autowired
    private EventService eventService;

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