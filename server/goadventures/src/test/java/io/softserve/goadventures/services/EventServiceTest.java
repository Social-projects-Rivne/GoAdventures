package io.softserve.goadventures.services;

import io.softserve.goadventures.dto.EventDTO;
import io.softserve.goadventures.repositories.CategoryRepository;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import static org.junit.Assert.*;

public class EventServiceTest {

    @Mock
    CategoryRepository categoryRepository;

    @Test
    public void newAddEvent() {
        EventDTO eventDTO = new EventDTO();
        eventDTO.setTopic("Topic from Postman");
        eventDTO.setDescription("Description from Postmanishche");
        eventDTO.setCategory("Chototamto");
        eventDTO.setStatusId(1);

//        when(newAddEvent(eventDTO)).thenReturn(null);
    }
}