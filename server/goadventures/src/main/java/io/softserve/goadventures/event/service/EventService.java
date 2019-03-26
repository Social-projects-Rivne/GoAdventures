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
    //
    // public Iterable<EventDTO> getEventsByLocation(String location){
    // List<EventDTO> returnEvents = new ArrayList();
    // for(Event event: eventRepository.findAll()) {
    // if (event.getLocation().equals(location)) {
    // Category category=event.getCategory();
    //
    // List<String>gallery = new ArrayList<>();
    //
    // for(Gallery gal:galleryRepository.findByEventId(event.getId()))
    // gallery.add(gal.getImageUrl());
    //
    //
    // returnEvents.add(
    // new EventDTO(event.getId(),event.getTopic(),event.getStartDate(),
    // event.getEndDate(),event.getLocation(),event.getDescription(),
    // event.getStatusId(),
    // category.getCategoryName(),gallery
    // ));
    // }
    // }
    // return returnEvents;
    // }

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
