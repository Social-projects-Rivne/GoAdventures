package io.softserve.goadventures.controllers;

import io.softserve.goadventures.dto.GalleryDto;
import io.softserve.goadventures.models.Category;
import io.softserve.goadventures.models.Event;
import io.softserve.goadventures.models.Gallery;
import io.softserve.goadventures.repositories.GalleryRepository;
import io.softserve.goadventures.services.EventService;
import io.softserve.goadventures.services.JWTService;
import io.softserve.goadventures.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;
import java.nio.charset.Charset;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class GalleryControllerTest {

    @InjectMocks
    GalleryController galleryControllerMock;

    @Mock
    UserService userServiceMock;

    @Mock
    JWTService jwtServiceMock;

    @Mock
    EventService eventService;

    @Mock
    GalleryRepository galleryRepositoryMock;

    @Mock
    ModelMapper modelMapperMock;


    private MockMvc mockMvc;
    private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
    private static final MediaType TEXT_PLAIN = new MediaType(MediaType.TEXT_PLAIN.getType(), MediaType.TEXT_PLAIN.getSubtype(), Charset.forName("ISO-8859-1"));
    private Category category = new Category( "Skateboarding");
    private Event first = new Event("topic1", "", "", "location", 25.33, 24.33, "description", category);
    private Gallery gallery = new Gallery();
    private GalleryDto galleryDto = new GalleryDto();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        galleryDto.setId(1);
        mockMvc = MockMvcBuilders.standaloneSetup(galleryControllerMock)
                .build();
    }

    @Test
    public void getGallery_Test() throws Exception{
        int galleryId = 1;

        when(galleryRepositoryMock.findById(galleryId)).thenReturn(gallery);
        when(modelMapperMock.map(gallery, GalleryDto.class)).thenReturn(galleryDto);

        mockMvc.perform(get("/event/gallery/get/" + galleryId))
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    @Test
    public void getGallery_CantFindGallery_Test() throws Exception{
        int galleryId = 1;

        when(galleryRepositoryMock.findById(galleryId)).thenReturn(null);
        when(modelMapperMock.map(gallery, GalleryDto.class)).thenReturn(galleryDto);

        mockMvc.perform(get("/event/gallery/get/" + galleryId))
                .andExpect(status().is(404))
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.publicError").value("Gallery doesn't exist"))
                .andExpect(jsonPath("$.innerError").value("Gallery not founded"));
    }

    @Test
    public void deactivateGallery_Test() throws Exception{
        int eventId = 1;

        when(eventService.getEventById(eventId)).thenReturn(first);
        when(galleryRepositoryMock.findByEventId(eventId)).thenReturn(gallery);
        when(eventService.updateEvent(first)).thenReturn(first);

        mockMvc.perform(put("/event/gallery/deattach/" + eventId))
                .andExpect(status().isOk());
    }

    @Test
    public void deactivateGallery_EventNotFound_Test() throws Exception{
        int eventId = 1;
        IOException error = new IOException(String.format("Event with %s id does not exist", eventId));

        when(eventService.getEventById(eventId)).thenReturn(null);

        mockMvc.perform(put("/event/gallery/deattach/" + eventId))
                .andExpect(status().is(500))
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.publicError").value("Server error, try again later"))
                .andExpect(jsonPath("$.innerError").value(error.getMessage()));
    }
}
