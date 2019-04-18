package io.softserve.goadventures.controllers;

import io.softserve.goadventures.dto.EventDTO;
import io.softserve.goadventures.dto.GalleryDto;
import io.softserve.goadventures.models.Category;
import io.softserve.goadventures.models.Event;
import io.softserve.goadventures.models.User;
import io.softserve.goadventures.services.EventDtoBuilder;
import io.softserve.goadventures.services.EventService;
import io.softserve.goadventures.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class ProfileControllerTest {

    @InjectMocks
    ProfileController profileControllerMock;

    @Mock
    EventService eventServiceMock;

    @Mock
    EventDtoBuilder eventDtoBuilderMock;

    private MockMvc mockMvc;
    private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(profileControllerMock)
                .build();
    }

    @Test
    public void getAllEvents_ShouldReturnFoundEvents() throws Exception{
        Category category = new Category( "Skateboarding");
        Event first = new Event("topic1", "", "", "location", 25.33, 24.33, "description", category);
        Event second = new Event("topic2", "", "", "location", 25.33, 24.33, "description", category);
        first.setId(1);
        second.setId(2);
        GalleryDto galleryDTO = new GalleryDto();
        EventDTO firstDTO = new EventDTO(1, "topic1", "", "", "location", 25.33, 24.33, "description", 1, "first", galleryDTO);
        EventDTO secondDTO = new EventDTO(2, "topic2", "", "", "location", 25.33, 24.33, "description", 1, "second", galleryDTO);
        User user = new User();
        user.setId(1);

        List<Event> events = new ArrayList<>();
        events.add(first);
        events.add(second);
        Page<Event> eventPage = new PageImpl<>(events);
        Pageable pageable = PageRequest.of(0,15, Sort.Direction.ASC,"id");

        List<EventDTO> expectedDTO = new ArrayList<>();
        expectedDTO.add(firstDTO);
        expectedDTO.add(secondDTO);
        Page<EventDTO> eventDTOPage = new PageImpl<>(expectedDTO);

        when(eventServiceMock.getAllEvents(pageable)).thenReturn(eventPage);
        when(eventServiceMock.getAllEventsByOwner(pageable, user.getId())).thenReturn(eventPage);
        when(eventDtoBuilderMock.convertToDto(eventPage)).thenReturn(eventDTOPage);

        mockMvc.perform(get("/profile/all-events"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.empty").value("false"))
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].id").value("1"))
                .andExpect(jsonPath("$.content[0].topic").value("topic1"))
                .andExpect(jsonPath("$.content[1].id").value("2"))
                .andExpect(jsonPath("$.content[1].topic").value("topic2"))
                .andExpect(jsonPath("$.last").value("true"))
                .andExpect(jsonPath("$.first").value("true"));

        verify(eventServiceMock, times(1)).getAllEvents(pageable);
        verify(eventDtoBuilderMock, times(1)).convertToDto(eventPage);
        verifyNoMoreInteractions(eventServiceMock, eventDtoBuilderMock);
    }
}
