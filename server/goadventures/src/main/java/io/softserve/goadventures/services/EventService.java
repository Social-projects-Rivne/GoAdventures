package io.softserve.goadventures.services;

import io.softserve.goadventures.models.Category;
import io.softserve.goadventures.models.Event;
import io.softserve.goadventures.repositories.EventRepository;
import io.softserve.goadventures.repositories.GalleryRepository;
import io.softserve.goadventures.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final GalleryRepository galleryRepository;

    @Autowired
    public EventService(EventRepository eventRepository, GalleryRepository galleryRepository) {
        this.eventRepository = eventRepository;
        this.galleryRepository = galleryRepository;
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

    public void addEvent(Event newEvent) {
        eventRepository.save(newEvent);
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

    public Page<Event> getAllEventsByCategory(Pageable pageable, Category category) {
        return eventRepository.findAllByCategory(pageable, category);
    }
}
