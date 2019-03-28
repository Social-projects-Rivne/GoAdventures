package io.softserve.goadventures.event.controller;

import io.softserve.goadventures.auth.service.JWTService;
import io.softserve.goadventures.event.category.Category;
import io.softserve.goadventures.event.dto.EventDTO;
import io.softserve.goadventures.event.model.Event;
import io.softserve.goadventures.event.repository.CategoryRepository;
import io.softserve.goadventures.event.repository.EventRepository;
import io.softserve.goadventures.event.service.EventDtoBuilder;
import io.softserve.goadventures.event.service.EventService;
import io.softserve.goadventures.gallery.model.Gallery;
import io.softserve.goadventures.gallery.repository.GalleryRepository;
import io.softserve.goadventures.user.model.User;
import io.softserve.goadventures.user.service.UserNotFoundException;
import io.softserve.goadventures.user.service.UserService;
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
  private final EventDtoBuilder eventDtoBuilder;
  private final UserService userService;
  private final JWTService jwtService;

  @Autowired
  public EventController(EventService eventService, EventRepository eventRepository,
                         CategoryRepository categoryRepository, GalleryRepository galleryRepository,
                         EventDtoBuilder eventDtoBuilder, UserService userService, JWTService jwtService) {
    this.eventService = eventService;
    this.eventRepository = eventRepository;
    this.categoryRepository = categoryRepository;
    this.galleryRepository = galleryRepository;
    this.eventDtoBuilder = eventDtoBuilder;
    this.userService = userService;
    this.jwtService = jwtService;
  }

  @PostMapping("/create/{categoryId}")
  public ResponseEntity<String> createEvent(@PathVariable(value = "categoryId") String categoryId,
                                            @RequestBody Event event) {
    Category category = categoryRepository.findByCategoryName(categoryId);
    event.setCategory(category);
    eventService.addEvent(event);
    HttpHeaders httpHeaders = new HttpHeaders();

    return ResponseEntity.ok().headers(httpHeaders).body("Event created");
  }

  @PostMapping("/create/")
  public ResponseEntity<String> createEvent(@RequestBody Event event) {
    eventService.addEvent(event);
    HttpHeaders httpHeaders = new HttpHeaders();

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

  @GetMapping({"/all/{search}", "/all"})
  public ResponseEntity<?> getAllEvents(@PathVariable(value = "search", required = false) String search,
                                        @PageableDefault(size = 15, sort = "id") Pageable eventPageable) {

    Page<Event> eventsPage = search == null ? eventService.getAllEvents(eventPageable)
            : eventService.getAllEventsByTopic(eventPageable, search);

    if (eventsPage != null) {
      int nextPageNum = eventsPage.getNumber() + 1;
      UriComponents uriComponentsBuilder = UriComponentsBuilder.newInstance().path("/event/all")
              .query("page={keyword}").buildAndExpand(nextPageNum);
      HttpHeaders httpHeaders = new HttpHeaders();
      httpHeaders.set("nextpage", uriComponentsBuilder.toString());
      System.out.println(eventDtoBuilder.convertToDto(eventsPage));
      Slice<EventDTO> t = eventDtoBuilder.convertToDto(eventsPage);
      logger.info("Event converted to dto", t.getContent());
      logger.info("Event converted to dto", t.getContent().get(0));
      return new ResponseEntity<Slice<EventDTO>>(t, httpHeaders,
              HttpStatus.OK);
    } else {
      // TODO: wr1 3r c
      return ResponseEntity.badRequest().body("End of pages");
    }
  }

  @GetMapping("/allCategory")
  public Iterable<Category> getAllCategory() {
    return categoryRepository.findAll();
  }

  @GetMapping("/category/{categoryId}")
  public Page<Event> getAllEventsByCategoryId(@PathVariable(value = "categoryId") int eventId, Pageable pageable) {
    return eventRepository.findByCategoryId(eventId, pageable);
  }

  @GetMapping("/gallery/{eventId}")
  public Gallery getAllGalleryByEventId(@PathVariable(value = "eventId") int eventId) {
    Event event = eventRepository.findById(eventId);
    return galleryRepository.findByEventId(event.getId());
  }

  @GetMapping("/{eventId}")
  public Event getEvent(@PathVariable(value = "eventId") int eventId) {
    return eventService.getEventById(eventId);
  }

  @DeleteMapping("delete")
  public ResponseEntity<?> deleteEventOwner(@RequestHeader(value = "Authorization") String token,
                                            @RequestHeader(value = "EventId") int eventId) throws UserNotFoundException {
    Event event = eventService.getEventById(eventId);
    User user = userService.getUserByEmail(jwtService.parseToken(token));
    if (eventService.delete(user, event)) {
      return ResponseEntity.ok("Event deleted");
    } else {
      return ResponseEntity.badRequest().body("Delete doesn't work");
    }
  }

  @PostMapping("isOwner")
  public ResponseEntity<?> isOwner(@RequestHeader(value = "Authorization") String token,
                                   @RequestHeader(value = "EventId") int eventId) throws UserNotFoundException {
    LoggerFactory.getLogger("IS OWNER EVENT").info("Event ID is : " + eventId
            + " , OwnerId is : " + userService.getUserByEmail(jwtService.parseToken(token)).getId());
    Event event = eventService.getEventById(eventId);
    User user = userService.getUserByEmail(jwtService.parseToken(token));

    return user.getId() == event.getOwner().getId() ?
            ResponseEntity.ok().body("IS OWNER") :
            ResponseEntity.badRequest().body("IS NOT OWNER");
  }
}