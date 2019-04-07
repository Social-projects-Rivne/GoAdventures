package io.softserve.goadventures.services;

import io.softserve.goadventures.models.Event;
import io.softserve.goadventures.models.EventParticipants;
import io.softserve.goadventures.models.User;
import io.softserve.goadventures.repositories.EventParticipantsRepository;
import io.softserve.goadventures.repositories.EventRepository;
import io.softserve.goadventures.repositories.GalleryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventParticipantsService {

    private final EventRepository eventRepository;
    private final GalleryRepository galleryRepository;
    private  final EventParticipantsRepository eventParticipantsRepository;


    @Autowired
    public EventParticipantsService(EventRepository eventRepository, GalleryRepository galleryRepository, EventParticipantsRepository eventParticipantsRepository) {
        this.eventRepository = eventRepository;
        this.galleryRepository = galleryRepository;
        this.eventParticipantsRepository = eventParticipantsRepository;
    }

    public EventParticipants getById(int id) {
        return eventParticipantsRepository.findById(id);
    }


    public EventParticipants addParicipant(User user, Event event) {

        EventParticipants eventParticipant = new EventParticipants();
        eventParticipant.setEvent(event);
        eventParticipant.setUser(user);
        if (user.getId() == event.getOwner().getId()) {
            eventParticipant.setIs_owner(true);
        }
        else {
            eventParticipant.setIs_owner(false);
        }
        eventParticipant.setIs_subscriber(true);

        return eventParticipantsRepository.save(eventParticipant);

    }

    public boolean delete(EventParticipants eventParticipant){

        if(eventParticipant.getIs_subscriber() == true){
            eventParticipantsRepository.delete(eventParticipant);
            return true;
        }
        else {
            return false;
        }
    }

}