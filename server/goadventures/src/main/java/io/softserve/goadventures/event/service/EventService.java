package io.softserve.goadventures.event.service;
import java.util.*;

import io.softserve.goadventures.Gallery.model.Gallery;
import io.softserve.goadventures.Gallery.repository.GalleryRepository;
import io.softserve.goadventures.event.category.Category;
import io.softserve.goadventures.event.model.Event;
import io.softserve.goadventures.event.model.EventDTO;
import io.softserve.goadventures.event.repository.CategoryRepository;
import io.softserve.goadventures.event.repository.EventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService{
    private final EventRepository eventRepository;
    private final GalleryRepository galleryRepository;
    private Logger logger = LoggerFactory.getLogger(io.softserve.goadventures.auth.controller.AuthController.class);

    @Autowired
    public EventService(EventRepository eventRepository,GalleryRepository galleryRepository){
        this.eventRepository = eventRepository;
        this.galleryRepository=galleryRepository;
    }

    public Event getEventById(int id){
        return eventRepository.findById(id);
    }

    public Event getEventByTopic(String topic){
        return eventRepository.findByTopic(topic);
    }

    public Iterable<EventDTO> getEventsByLocation(String location){
        List<EventDTO> returnEvents = new ArrayList();
        for(Event event: eventRepository.findAll()) {
            if (event.getLocation().equals(location)) {
                Category category=event.getCategory();

                List<String>gallery = new ArrayList<>();

                for(Gallery gal:galleryRepository.findByEventId(event.getId()))
                    gallery.add(gal.getImageUrl());



            returnEvents.add(
                    new EventDTO(event.getId(),event.getTopic(),event.getStartDate(),
                                event.getEndDate(),event.getLocation(),event.getDescription(),
                                event.getStatusId(),
                                category.getCategoryName(),gallery
                    ));
            }
        }
        return returnEvents;
    }


    public void addEvent(Event newEvent) {eventRepository.save(newEvent);}

    public void updateEvent(Event event) {
        eventRepository.save(event);
    }

    public Iterable<Event> getAllEvents() {
        return eventRepository.findAll();
    }
}
