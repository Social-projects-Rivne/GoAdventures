package io.softserve.goadventures.services;

import io.softserve.goadventures.models.Event;
import io.softserve.goadventures.models.EventParticipants;
import io.softserve.goadventures.models.User;
import io.softserve.goadventures.repositories.EventParticipantsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventParticipantsService {

    private  final EventParticipantsRepository eventParticipantsRepository;

    @Autowired
    public EventParticipantsService(EventParticipantsRepository eventParticipantsRepository) {
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

    public boolean deleteParticipant(User user, Event event){

        for (EventParticipants e : eventParticipantsRepository.findAll()) {
            if ((event.getId()) == (e.getEvent().getId()) && (user.getId()) == (e.getUser().getId())) {
                eventParticipantsRepository.delete(e);
                return true;
            }
        }

        return false;
    }

    public List<EventParticipants> getAllSubscribersForOneEvent(Integer id) {

        List<EventParticipants> list = new ArrayList<>();

        for (EventParticipants e : eventParticipantsRepository.findAll()) {
            if (id.equals(e.getEvent().getId())) {
                list.add(e);
            }
        }
        return list;
    }

    public boolean isParticipant(User user, Event event){

        for (EventParticipants e : eventParticipantsRepository.findAll()) {
            if ((event.getId()) == (e.getEvent().getId()) && (user.getId()) == (e.getUser().getId())) {
                return  true;
            }
        }

        return false;
    }

}