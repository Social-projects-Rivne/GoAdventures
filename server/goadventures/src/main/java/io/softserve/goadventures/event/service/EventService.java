package io.softserve.goadventures.event.service;

import io.softserve.goadventures.event.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import io.softserve.goadventures.event.repository.EventRepository;
import io.softserve.goadventures.gallery.repository.GalleryRepository;

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

    public void updateEvent(Event event) {
        eventRepository.save(event);
    }

    public Page<Event> getAllEvents(Pageable eventPageable) {
        Page<Event> eventPage = eventRepository.findAll(eventPageable);
        return eventPage;
    }
}
