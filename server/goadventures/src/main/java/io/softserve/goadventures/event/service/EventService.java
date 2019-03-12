package io.softserve.goadventures.event.service;
import java.util.*;
import io.softserve.goadventures.event.model.Event;
import io.softserve.goadventures.event.repository.EventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService{
    private final EventRepository eventRepository;
    private Logger logger = LoggerFactory.getLogger(io.softserve.goadventures.auth.controller.AuthController.class);

    @Autowired
    public EventService(EventRepository eventRepository){
        this.eventRepository = eventRepository;
    }

    public Event getEventById(int id){
        return eventRepository.findById(id);
    }

    public Event getEventByTopic(String topic){
        return eventRepository.findByTopic(topic);
    }

    public Iterable<Event> getEventsByLocation(String location){
        List<Event> returnEvents=new ArrayList();
        for(Event event: eventRepository.findAll()) {
            if (event.getLocation().equals(location)) {
                logger.info(location);
                returnEvents.add(event);
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
