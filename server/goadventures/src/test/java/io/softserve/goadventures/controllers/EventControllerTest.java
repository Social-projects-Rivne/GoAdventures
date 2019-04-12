package io.softserve.goadventures.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.inject.internal.util.Lists;
import io.softserve.goadventures.dto.UserAuthDto;
import io.softserve.goadventures.enums.UserStatus;
import io.softserve.goadventures.models.Category;
import io.softserve.goadventures.models.Event;
import io.softserve.goadventures.models.User;
import io.softserve.goadventures.repositories.CategoryRepository;
import io.softserve.goadventures.services.EventService;
import io.softserve.goadventures.services.JWTService;
import io.softserve.goadventures.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.nio.charset.Charset;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class EventControllerTest {

    @InjectMocks
    EventController eventController;

    @Mock
    EventService eventService;

    @Mock
    CategoryRepository categoryRepository;

    @Mock
    JWTService jwtService;

    @Mock
    View mockView;

    private MockMvc mockMvc;
    private UserAuthDto authDto;
    private User user;
    private User userConfirm;
    private String token;
    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(eventController)
                .setSingleView(mockView)
                .build();
    }

    @Test
    public void create() throws Exception{
        Category category = new Category("Skateboarding");
        Event event = new Event("topic", "", "", "location", 25.33, 24.33, "description", category);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(event);

        this.mockMvc.perform(post("/event/create/").contentType(APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().isOk())
        .andExpect(content().string("Event created"));
    }

    @Test
    public void getAllCategory() throws Exception{
        Category[] categories = new Category[1];
        Iterable<Category> expectedCategory = Arrays.asList(categories);
        List<Category> myList = Lists.newArrayList(expectedCategory);
        when(categoryRepository.findAll()).thenReturn(myList);

        mockMvc.perform(get("/event/allCategory")).andExpect(status().isOk());
    }
}