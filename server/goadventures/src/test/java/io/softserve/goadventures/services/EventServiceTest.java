package io.softserve.goadventures.services;

import io.softserve.goadventures.models.Event;
import io.softserve.goadventures.models.User;
import io.softserve.goadventures.repositories.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
class EventServiceTest {

    @InjectMocks
    EventService eventService;

    @Mock
    EventRepository eventRepository;

    private static final int ID = 1;
    private static final String topic = "topic";
    private Event event;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        event = new Event();
        event.setTopic(topic);
        event.setStartDate("");
        event.setDescription("description");
        event.setId(ID);
    }

    @Test
    void getEventById() {
        Event mockEvent = mock(Event.class);
        System.out.println(mockEvent);

        when(eventRepository.findById(ID)).thenReturn(mockEvent);

        assertEquals(mockEvent, eventService.getEventById(ID));
        eventService.getEventById(ID);
        verify(eventRepository, times(2)).findById(ID);
    }

    @Test
    void getEventByTopic() {
        when(eventRepository.findByTopic(topic)).thenReturn(event);

        Event eventByTopic = eventService.getEventByTopic(topic);

        assertNotNull(eventByTopic);
        assertEquals("topic", eventByTopic.getTopic());
    }

    @Test
    void getAllEvents() {
        List<Event> expected = new ArrayList<>();
        Page<Event> eventPage = new PageImpl<>(expected);
        PageRequest pageable = PageRequest.of(0,1);
        when(eventRepository.findAll(pageable)).thenReturn(eventPage);
        Page<Event> eventsPage = eventService.getAllEvents(pageable);

        assertThat(eventsPage.isFirst(),is(true));
        assertThat(eventPage.isLast(),is(true));
        assertThat(eventsPage.hasNext(),is(false));
    }

    @Test
    void addEvent() {
        Event event = mock(Event.class);
        String token = "fdsggerte";

        when(event.getId()).thenReturn(1);
        when(event.getTopic()).thenReturn("Test Topic");
        when(event.getDescription()).thenReturn("Description Test");
        when(eventRepository.save(event)).thenReturn(event);
    }
}
