package io.softserve.goadventures.controllers;

import io.softserve.goadventures.dto.EventDTO;
import io.softserve.goadventures.enums.EventStatus;
import io.softserve.goadventures.errors.ErrorMessageManager;
import io.softserve.goadventures.errors.UserNotFoundException;
import io.softserve.goadventures.models.*;
import io.softserve.goadventures.repositories.CategoryRepository;
import io.softserve.goadventures.repositories.EventParticipantsRepository;
import io.softserve.goadventures.repositories.EventRepository;
import io.softserve.goadventures.repositories.GalleryRepository;
import io.softserve.goadventures.services.EventParticipantsService;
import io.softserve.goadventures.services.EventService;
import io.softserve.goadventures.services.JWTService;
import io.softserve.goadventures.services.UserService;
import io.softserve.goadventures.utils.EventDtoBuilder;
import org.modelmapper.ModelMapper;
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

import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping("event")
public class EventController {
    private final Logger logger = LoggerFactory.getLogger(EventController.class);
    private final EventService eventService;
    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final EventDtoBuilder eventDtoBuilder;
    private final JWTService jwtService;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final EventParticipantsService eventParticipantsService;
    private final EventParticipantsRepository eventParticipantsRepository;

    @Autowired
    public EventController(EventParticipantsService eventParticipantsService, EventParticipantsRepository eventParticipantsRepository,
                           EventService eventService, EventRepository eventRepository,
                           CategoryRepository categoryRepository, GalleryRepository galleryRepository,
                           EventDtoBuilder eventDtoBuilder, UserService userService,
                           JWTService jwtService, ModelMapper modelMapper) {
        this.eventService = eventService;
        this.eventRepository = eventRepository;
        this.categoryRepository = categoryRepository;
        this.eventDtoBuilder = eventDtoBuilder;
        this.jwtService = jwtService;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.eventParticipantsService = eventParticipantsService;
        this.eventParticipantsRepository = eventParticipantsRepository;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createEvent(@RequestHeader(value = "Authorization") String token,
                                              @RequestBody EventDTO event) {
        logger.info("[CREATE-EVENT] - event: " + event);

        Event mappedEvent = modelMapper.map(event, Event.class);
        Category category = categoryRepository.findByCategoryName(event.getCategory().getCategoryName());
        mappedEvent.setCategory(category);
        mappedEvent.setStatusId(EventStatus.OPENED.getEventStatus());

        Gallery gallery;
        if (event.getGallery() != null) {
            gallery = modelMapper.map(event.getGallery(), Gallery.class);
            gallery.setEventId(mappedEvent);
            mappedEvent.setGallery(gallery);
        }
        try {
            eventService.addEvent(mappedEvent, token);
            LoggerFactory.getLogger("Create Event Controller: ")
                    .info(userService.getUserByEmail(jwtService.parseToken(token)).toString());
            eventService.addEvent(mappedEvent, token);
        } catch (UserNotFoundException e) {
            logger.error(e.toString());
        }
        return ResponseEntity.ok("Event created");
    }

    @PostMapping("/close")
    public ResponseEntity<String> closeEvent(@RequestHeader(value = "Authorization") String token,
                                             @RequestHeader(value = "EventId") int eventId) {
        logger.info("[CLOSE-EVENT] - eventId: " + eventId);
        Event event = eventService.getEventById(eventId);
        User user = userService.getUserByEmail(jwtService.parseToken(token));
        if (eventService.closeEvent(user, event)) {
            return ResponseEntity.ok("Event closed");
        } else {
            return ResponseEntity.badRequest().body("Close doesn't work");
        }
    }

    @PostMapping("/open")
    public ResponseEntity<String> openEvent(@RequestHeader(value = "Authorization") String token,
                                            @RequestHeader(value = "EventId") int eventId) {
        logger.info("[OPEN-EVENT] - eventId: " + eventId);
        Event event = eventService.getEventById(eventId);
        User user = userService.getUserByEmail(jwtService.parseToken(token));
        if (eventService.openEvent(user, event)) {
            return ResponseEntity.ok("Event opened");
        } else {
            return ResponseEntity.badRequest().body("Open doesn't work");
        }
    }

    @PostMapping("/subscribe")
    public ResponseEntity<String> addNewSubscriber(@RequestHeader(value = "Authorization") String token,
                                                   @RequestHeader(value = "EventId") int eventId) {
        logger.info("[SUBSCRIBE] - eventId: " + eventId);

        Event event = eventService.getEventById(eventId);
        logger.info("Topic of Event: " + event.getTopic());
        User user = userService.getUserByEmail(jwtService.parseToken(token));
        logger.info("Email of User: " + user.getEmail());

        eventParticipantsService.addParicipant(user, event);

        HttpHeaders httpHeaders = new HttpHeaders();
        return ResponseEntity.ok().headers(httpHeaders).body("Added new Subscriber");
    }

    @PostMapping("/unsubscribe")
    public ResponseEntity<String> deleteSubscriber(@RequestHeader(value = "Authorization") String token,
                                                   @RequestHeader(value = "EventId") int eventId) {
        logger.info("[UNSUBSCRIBE] - eventId: " + eventId);

        Event event = eventService.getEventById(eventId);
        logger.info("Topic of Event: " + event.getTopic());
        User user = userService.getUserByEmail(jwtService.parseToken(token));
        logger.info("Email of User: " + user.getEmail());

        eventParticipantsService.deleteParticipant(user, event);

        HttpHeaders httpHeaders = new HttpHeaders();
        return ResponseEntity.ok().headers(httpHeaders).body("Deleted Subscriber");
    }

    @GetMapping("/allSubscribers")
    public Iterable<EventParticipants> getAllSubcribers() {
        return eventParticipantsRepository.findAll();
    }

    @GetMapping("/allSubscribersForEvent")
    public Iterable<EventParticipants> getAllForEvent(@RequestHeader(value = "eventId") int eventId) {
        logger.info("Get all subscribers for event by Id: " + eventId);
        return eventParticipantsService.getAllSubscribersForOneEvent(eventId);
    }

    @PostMapping("/category")
    public ResponseEntity<String> createCategory(@RequestBody Category category) {
        logger.info("[CREATE-CATEGORY] - category: " + category);

        category.setEvents(null);
        categoryRepository.save(category);

        HttpHeaders httpHeaders = new HttpHeaders();
        return ResponseEntity.ok().headers(httpHeaders).body("Category created");
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllEvents(@RequestParam(value = "search", required = false) String search,
                                          @RequestParam(value = "filter", required = false) String filter,
                                          @PageableDefault(size = 15, sort = "id") Pageable eventPageable) {
        logger.info("[GET-ALL-EVENTS] - search: " + search + "; filter: " + filter);

        Category category = categoryRepository.findByCategoryName(filter);

        Page<Event> eventsPage = (search == null & filter == null) ? eventService.getAllEvents(eventPageable)
                : eventService.getAllByCategoryOrSearch(eventPageable, category, search);

        if (eventsPage != null) {
            int nextPageNum = eventsPage.getNumber() + 1;
            UriComponents uriComponentsBuilder = UriComponentsBuilder.newInstance().path("/event/all")
                    .query("page={keyword}").buildAndExpand(nextPageNum);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("nextpage", uriComponentsBuilder.toString());
            Slice<EventDTO> eventDTOSlice = eventDtoBuilder.convertToDto(eventsPage);
            return new ResponseEntity<>(eventDTOSlice, httpHeaders, HttpStatus.OK);
        } else {
            return ResponseEntity.badRequest()
                    .body(new ErrorMessageManager("Server error, try again later", "Pageable error"));
        }
    }

    @PutMapping("update/{eventId}")
    public ResponseEntity<?> updateEvent(@PathVariable("eventId") int eventId, @RequestBody EventDTO updatedEvent) {
        logger.info("[UPDATE-EVENT] - eventId: " + eventId + "; eventDto: " + updatedEvent);
        try {
            Event event = eventService.getEventById(eventId);
            if (event != null) {
                modelMapper.map(updatedEvent, event);
                event.setCategory(categoryRepository.findByCategoryName(updatedEvent.getCategory().getCategoryName()));
                return ResponseEntity.ok().body(modelMapper.map(eventService.updateEvent(event), EventDTO.class));
            } else {
                throw new IOException("Event does not exist");
            }
        } catch (IOException error) {
            logger.error(error.toString());
            return ResponseEntity.status(500)
                    .body(new ErrorMessageManager("Server error, try again later", error.toString()));
        }
    }

    @GetMapping("/allCategory")
    public Iterable<Category> getAllCategory() {
        logger.info("[GET-ALL-CATEGORY]");
        return categoryRepository.findAll();
    }

    @GetMapping("/category/{categoryId}")
    public Page<Event> getAllEventsByCategoryId(@PathVariable(value = "categoryId") int categoryId,
                                                @PageableDefault(size = 15, sort = "id") Pageable pageable) {
        logger.info("[GET-ALL-EVENTS-BY-CATEGORY-ID] - categoryId: " + categoryId);
        return eventRepository.findByCategoryId(categoryId, pageable);
    }

    @GetMapping("/{eventId}")
    public Event getEvent(@PathVariable(value = "eventId") int eventId) {
        logger.info("[GET-EVENT] - eventId: " + eventId);
        return eventService.getEventById(eventId);
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> deleteEventOwner(@RequestHeader(value = "Authorization") String token,
                                              @RequestHeader(value = "EventId") int eventId) {
        logger.info("[DELETE-EVENT-OWNER] - eventId: " + eventId);
        Event event = eventService.getEventById(eventId);
        User user = userService.getUserByEmail(jwtService.parseToken(token));
        if (eventService.delete(user, event)) {
            return ResponseEntity.ok("Event deleted");
        } else {
            return ResponseEntity.badRequest().body("Delete doesn't work");
        }
    }

    @GetMapping("isOwner")
    public ResponseEntity<?> isOwner(@RequestHeader(value = "Authorization") String token,
                                     @RequestHeader(value = "EventId") int eventId) {
        logger.info("[IS-OWNER] - eventId: " + eventId + "; ownerId: "
                + userService.getUserByEmail(jwtService.parseToken(token)).getId());

        Event event = eventService.getEventById(eventId);
        User user = userService.getUserByEmail(jwtService.parseToken(token));

        return user.getId() == event.getOwner().getId() ? ResponseEntity.ok().body("IS OWNER")
                : ResponseEntity.badRequest().body("IS NOT OWNER");
    }

    @GetMapping("is-subscriber")
    public ResponseEntity<?> isSubscriber(@RequestHeader(value = "Authorization") String token,
                                          @RequestHeader(value = "EventId") int eventId) {
        logger.info("[IS-SUBSCRIBER] - eventId: " + eventId + "; userId: "
                + userService.getUserByEmail(jwtService.parseToken(token)).getId());
        Event event = eventService.getEventById(eventId);
        User user = userService.getUserByEmail(jwtService.parseToken(token));

        return eventParticipantsService.isParticipant(user, event) ? ResponseEntity.ok().body("IS SUBSCRIBER")
                : ResponseEntity.badRequest().body("IS NOT SUBSCRIBER");
    }

    @GetMapping("/event-detail/{eventName}")
    public ResponseEntity<?> eventDetail(@PathVariable("eventName") String eventName) {
        Event event = eventService.findEventByTopic(eventName);
        EventDTO findedEvent = modelMapper.map(event, EventDTO.class);
        return ResponseEntity.ok().body(findedEvent);
    }
}