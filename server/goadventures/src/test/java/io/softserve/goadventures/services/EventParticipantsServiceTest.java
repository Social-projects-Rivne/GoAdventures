package io.softserve.goadventures.services;

import io.softserve.goadventures.models.Event;
import io.softserve.goadventures.models.EventParticipants;
import io.softserve.goadventures.models.User;
import io.softserve.goadventures.repositories.EventParticipantsRepository;
import io.softserve.goadventures.repositories.EventRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class EventParticipantsServiceTest {

    @InjectMocks
    EventParticipantsService eventParticipantsServiceMock;

    @Mock
    EventParticipants eventParticipantsMock;

    @Mock
    EventParticipantsRepository eventParticipantsRepositoryMock;

    @Mock
    EventRepository eventRepository;

    private static final int ID = 1;
    private static final String topic = "topic";
    @Mock
    Event event;
    @Mock
    User user;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        event.setTopic(topic);
        event.setStartDate("");
        event.setDescription("description");
        event.setId(ID);
    }

    @Test
    public void addParticipant_Test(){
        when(eventParticipantsRepositoryMock.save(eventParticipantsMock)).thenReturn(eventParticipantsMock);

        when(eventRepository.findById(ID)).thenReturn(event);
        when(event.getOwner()).thenReturn(user);
        when(event.getOwner().getId()).thenReturn(1);
        when(user.getId()).thenReturn(1);

        eventParticipantsServiceMock.addParicipant(user,event);
    }

    @Test
    public void isParticipant_notParticipant_Test(){
        List<EventParticipants> eventParticipantsList = new ArrayList<>();
        when(eventParticipantsRepositoryMock.findAll()).thenReturn(eventParticipantsList);

        when(eventRepository.findById(ID)).thenReturn(event);
        when(eventParticipantsMock.getEvent()).thenReturn(event);
        when(eventParticipantsMock.getEvent().getId()).thenReturn(1);
        when(user.getId()).thenReturn(1);
        when(event.getId()).thenReturn(0);

        assertFalse(eventParticipantsServiceMock.isParticipant(user, event));
        verify(eventParticipantsRepositoryMock, times(1)).findAll();
    }
}
