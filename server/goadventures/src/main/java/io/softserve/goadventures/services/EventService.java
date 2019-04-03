package io.softserve.goadventures.services;

import io.softserve.goadventures.controllers.EventController;
import io.softserve.goadventures.dto.EventDTO;
import io.softserve.goadventures.dto.EventDtoBuilder;
import io.softserve.goadventures.enums.EventStatus;
import io.softserve.goadventures.errors.UserNotFoundException;
import io.softserve.goadventures.models.Category;
import io.softserve.goadventures.models.Event;
import io.softserve.goadventures.repositories.EventRepository;
import io.softserve.goadventures.repositories.CategoryRepository;
import io.softserve.goadventures.repositories.GalleryRepository;
import io.softserve.goadventures.models.User;
import io.softserve.goadventures.services.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class EventService {
    private Logger logger = LoggerFactory.getLogger(EventController.class);
    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final JWTService jwtService;
    private final UserService userService;

    @Autowired
    public EventService(EventRepository eventRepository,
                           CategoryRepository categoryRepository, UserService userService,
                           JWTService jwtService) {
        this.eventRepository = eventRepository;
        this.categoryRepository = categoryRepository;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    public Event getEventById(int id) {
        return eventRepository.findById(id);
    }

    public Event getEventByTopic(String topic) {
        return eventRepository.findByTopic(topic);
    }

    public Page<Event> getAllEventsByTopic(Pageable eventPageable, String topic) {
        return eventRepository.findAllByTopic(eventPageable, topic);
    }

    public void addEvent(EventDTO eventDTO, String token) throws UserNotFoundException {
        Category category = categoryRepository.findByCategoryName(eventDTO.getCategory());

        Event event = new Event(eventDTO.getTopic(), eventDTO.getStartDate(), eventDTO.getEndDate(),
                eventDTO.getLocation(), eventDTO.getLatitude(), eventDTO.getLongitude(),
                eventDTO.getDescription(), category);
        event.setStatusId(EventStatus.OPENED.getEventStatus());
        event.setOwner(userService.getUserByEmail(jwtService.parseToken(token)));
        eventRepository.save(event);
    }

    public Event updateEvent(Event event) {
       return eventRepository.save(event);
    }

    public Page<Event> getAllEvents(Pageable eventPageable) {
        Page<Event> eventPage = eventRepository.findAll(eventPageable);
        return eventPage;
    }

    public Page<Event> getAllEventsByOwner(Pageable pageable, Integer id) {
        List<Event> list = new ArrayList<>();

        for (Event e : eventRepository.findAll(pageable)) {
            if (id.equals(e.getOwner().getId())) {
                list.add(e);
            }
        }
        return new PageImpl<>(list);
    }

    public boolean delete(User user, Event event) {
        if (user.getId() == event.getOwner().getId()) {
            eventRepository.delete(event);
            return true;
        } else {
            return false;
        }
    }

    public boolean closeEvent(User user, Event event) {
        if (user.getId() == event.getOwner().getId()) {
            event.setStatusId(EventStatus.CLOSED.getEventStatus());
            eventRepository.save(event);
            return true;
        } else {
            return false;
        }
    }

    public boolean openEvent(User user, Event event) {
        if (user.getId() == event.getOwner().getId()) {
            event.setStatusId(EventStatus.OPENED.getEventStatus());
            eventRepository.save(event);
            return true;
        } else {
            return false;
        }
    }
}