package io.softserve.goadventures.event.controller;

import io.softserve.goadventures.Gallery.model.Gallery;
import io.softserve.goadventures.Gallery.repository.GalleryRepository;
import io.softserve.goadventures.event.category.Category;
import io.softserve.goadventures.event.model.Event;
import io.softserve.goadventures.event.repository.CategoryRepository;
import io.softserve.goadventures.event.repository.EventRepository;
import io.softserve.goadventures.event.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("event")
public class EventController {
    private Logger logger = LoggerFactory.getLogger(io.softserve.goadventures.event.controller.EventController.class);
    private final EventService eventService;
    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final GalleryRepository galleryRepository;

    @Autowired
    public EventController(EventService eventService, EventRepository eventRepository,
            CategoryRepository categoryRepository, GalleryRepository galleryRepository) {
        this.eventService = eventService;
        this.eventRepository = eventRepository;
        this.categoryRepository = categoryRepository;
        this.galleryRepository = galleryRepository;
    }

    @PostMapping("/create/{categoryId}")
    public ResponseEntity<String> createEvent(@PathVariable(value = "categoryId") int categoryId,
            @RequestBody Event event) {
        Category category = categoryRepository.findById(categoryId);
        event.setCategory(category);
        eventService.addEvent(event);
        HttpHeaders httpHeaders = new HttpHeaders();
        // event.setStatusId(EventStatus.CREATED.getEventStatus());
        // eventService.addEvent(event);

        return ResponseEntity.ok().headers(httpHeaders).body("Event created");
    }

    @PostMapping("/category")
    public ResponseEntity<String> createCategory(@RequestBody Category category) {
        category.setEvents(null);
        categoryRepository.save(category);

        HttpHeaders httpHeaders = new HttpHeaders();
        return ResponseEntity.ok().headers(httpHeaders).body("Category created");
    }

    @PostMapping("/gallery/{eventId}")
    public ResponseEntity<String> createGallery(@PathVariable(value = "eventId") int eventId,
            @RequestBody Gallery gallery) {
        Event event = eventRepository.findById(eventId);
        gallery.setEventId(event);
        galleryRepository.save(gallery);

        HttpHeaders httpHeaders = new HttpHeaders();
        return ResponseEntity.ok().headers(httpHeaders).body("Gallery created");
    }

    @GetMapping("/all")
    public Iterable<Event> getAllEvents() {
        logger.info("=====all events=====");
        return eventService.getAllEvents();
    }

    @GetMapping("/all/{location}")
    public Iterable<Event> getAllEvents(@PathVariable(value = "location") String location) {
        return eventService.getEventsByLocation(location);
    }

    @GetMapping("/category/{categoryId}")
    public Page<Event> getAllEventsByCategoryId(@PathVariable(value = "categoryId") int eventId, Pageable pageable) {
        return eventRepository.findByCategoryId(eventId, pageable);
    }

    @GetMapping("/gallery/{eventId}")
    public Page<Gallery> getAllGalleryByEventId(@PathVariable(value = "eventId") int eventId, Pageable pageable) {
        Event event = eventRepository.findById(eventId);
        return galleryRepository.findByEventId(event, pageable);
    }

    @GetMapping("/{eventId}")
    public Event getEvent(@PathVariable(value = "eventId") int eventId) {
        return eventService.getEventById(eventId);
    }

}