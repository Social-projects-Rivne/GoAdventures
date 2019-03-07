package io.softserve.goadventures.event.controller;

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

    @Autowired
    public EventController(EventService eventService, EventRepository eventRepository, CategoryRepository categoryRepository){
        this.eventService = eventService;
        this.eventRepository=eventRepository;
        this.categoryRepository=categoryRepository;
    }

    /*@PostMapping("/create/{categoryId}")
    public ResponseEntity<String> createEvent(@PathVariable (value = "categoryId") int categoryId, @RequestBody Event event){
        Category category = categoryRepository.findById(categoryId);
        event.setCategory(category);
        eventRepository.save(event);

        HttpHeaders httpHeaders = new HttpHeaders();
        logger.info(String.valueOf(event));
        /*event.setStatusId(EventStatus.CREATED.getEventStatus());
        eventService.addEvent(event);

        return ResponseEntity.ok().headers(httpHeaders).body("Event created");
    }*/

    @GetMapping("/all")
    public Iterable<Event> getAllEvents(){
        return eventService.getAllEvents();
    }

    /*@GetMapping("/category/{categoryId}")
    public Page<Event> getAllCommentsByPostId(@PathVariable (value = "categoryId") int postId,
                                              Pageable pageable) {
        return eventRepository.findByCategoryId(postId, pageable);
    }*/
}