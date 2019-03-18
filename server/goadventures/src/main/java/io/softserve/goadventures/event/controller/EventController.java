package io.softserve.goadventures.event.controller;

import io.softserve.goadventures.gallery.model.Gallery;
import io.softserve.goadventures.gallery.repository.GalleryRepository;
import io.softserve.goadventures.event.category.Category;
import io.softserve.goadventures.event.model.Event;
import io.softserve.goadventures.event.model.EventDTO;
import io.softserve.goadventures.event.repository.CategoryRepository;
import io.softserve.goadventures.event.repository.EventRepository;
import io.softserve.goadventures.event.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;


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
  public ResponseEntity<String> createEvent(@PathVariable(value = "categoryId") String categoryId, @RequestBody Event event) {
    Category category = categoryRepository.findByCategoryName(categoryId);
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
    HttpHeaders httpHeaders = new HttpHeaders();
    gallery.setEventId(event);
    galleryRepository.save(gallery);
    return ResponseEntity.ok().headers(httpHeaders).body("gallery created");
  }

  @GetMapping("/all")
  public ResponseEntity<?> getAllEvents(@PageableDefault(size = 15, sort = "id") Pageable eventPageable) {
    Page<Event> eventsPage = eventService.getAllEvents(eventPageable);
    if(eventsPage != null) {
      int nextPageNum = eventsPage.getNumber() + 1;
      UriComponents uriComponentsBuilder = UriComponentsBuilder.newInstance()
              .path("/event/all/")
              .query("page={keyword}")
              .buildAndExpand(nextPageNum);
      HttpHeaders httpHeaders = new HttpHeaders();
      httpHeaders.set("nextPage", uriComponentsBuilder.toString());
      return new ResponseEntity<Slice<Event>>(eventsPage,
              httpHeaders,
              HttpStatus.OK);
         } else {
            // TODO: wr1 3r c
            return  ResponseEntity.badRequest().body("End of pages");
        }
  }

  @GetMapping("/allCategory")
  public Iterable<Category> getAllCategory() {
    return categoryRepository.findAll();
  }

  @GetMapping("/all/{location}")
  public Iterable<EventDTO> getAllEvents(@PathVariable(value = "location") String location) {
    return eventService.getEventsByLocation(location);
  }

  @GetMapping("/category/{categoryId}")
  public Page<Event> getAllEventsByCategoryId(@PathVariable(value = "categoryId") int eventId, Pageable pageable) {
    return eventRepository.findByCategoryId(eventId, pageable);
  }

  @GetMapping("/gallery/{eventId}")
  public Iterable<Gallery> getAllGalleryByEventId(@PathVariable(value = "eventId") int eventId,
                                                  Pageable pageable) {
    Event event = eventRepository.findById(eventId);
    return galleryRepository.findByEventId(event.getId());
  }

  @GetMapping("/{eventId}")
  public Event getEvent(@PathVariable(value = "eventId") int eventId) {
    return eventService.getEventById(eventId);
  }

}