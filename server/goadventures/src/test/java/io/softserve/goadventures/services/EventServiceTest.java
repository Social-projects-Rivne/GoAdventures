package io.softserve.goadventures.services;

import io.softserve.goadventures.models.Event;
import io.softserve.goadventures.repositories.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
class EventServiceTest {

    @InjectMocks
    EventService eventService;

    @Mock
    EventRepository eventRepository;

    private static final int ID = 1;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getEventById() {
        Event mockEvent = mock(Event.class);
        System.out.println(mockEvent);

        when(eventRepository.findById(ID)).thenReturn((mockEvent));

        assertEquals(mockEvent, eventService.getEventById(ID));
        eventService.getEventById(ID);
        verify(eventRepository).findById(ID);
    }

    @Test
    void getEventByTopic() {
    }

    @Test
    void getAllEvents() {
//        Pageable pageable
//        eventService.getAllEvents()
    }

    @Test
    void addEvent() {
        Event event = mock(Event.class);

        when(event.getId()).thenReturn(1);
        when(event.getTopic()).thenReturn("Test Topic");
        when(event.getDescription()).thenReturn("Description Test");
        System.out.println(event.getTopic());
        when(eventRepository.save(event)).thenReturn(event);

//        assertEquals(event, eventService.addEvent(event));

        eventService.addEvent(event);
        verify(eventRepository).save(event);
    }
}