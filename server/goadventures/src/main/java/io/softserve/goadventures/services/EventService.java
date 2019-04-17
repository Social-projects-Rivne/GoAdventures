package io.softserve.goadventures.services;

import io.softserve.goadventures.controllers.EventController;
import io.softserve.goadventures.enums.EventStatus;
import io.softserve.goadventures.errors.UserNotFoundException;
import io.softserve.goadventures.models.Category;
import io.softserve.goadventures.models.Event;
import io.softserve.goadventures.models.User;
import io.softserve.goadventures.repositories.EventRepository;
import io.softserve.goadventures.repositories.GalleryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventService {
    private Logger logger = LoggerFactory.getLogger(EventController.class);
    private final EventRepository eventRepository;
    private final GalleryRepository galleryRepository;
    private final UserService userService;
    private  final JWTService jwtService;

    @Autowired
    public EventService(
            EventRepository eventRepository,
            GalleryRepository galleryRepository,
            UserService userService,
            JWTService jwtService) {
        this.galleryRepository = galleryRepository;
        this.eventRepository = eventRepository;
        this.userService = userService;
        this.jwtService = jwtService;
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

    public void addEvent(Event event, String token) throws UserNotFoundException {
        event.setOwner(userService.getUserByEmail(jwtService.parseToken(token)));
        eventRepository.save(event);
    }

    public Event updateEvent(Event event) {
        return eventRepository.save(event);
    }

    public Page<Event> getAllEvents(Pageable eventPageable) {
        return  eventRepository.findAll(eventPageable);

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

    public Page<Event> getAllEventsByCategory(Pageable pageable, Category category) {
        return eventRepository.findAllByCategory(pageable, category);
    }

    public Page<Event> getAllEventBySearch(Pageable pageable, String search) {
        Page<Event> events = eventRepository.findAll(pageable);

        List<Event> list = new ArrayList<>();

        for (Event event : events) {
            if (event.toString().contains(search)) {
                list.add(event);
            }
        }

        return new PageImpl<>(list);
    }

    public List<Event> findAllEvents() {
        return eventRepository.findAll();
    }
}
