package io.softserve.goadventures.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.softserve.goadventures.dto.CategoryDto;
import io.softserve.goadventures.dto.EventDTO;
import io.softserve.goadventures.dto.GalleryDto;
import io.softserve.goadventures.utils.EventDtoBuilder;
import io.softserve.goadventures.dto.UserAuthDto;
import io.softserve.goadventures.enums.UserStatus;
import io.softserve.goadventures.models.Category;
import io.softserve.goadventures.models.Event;
import io.softserve.goadventures.models.Gallery;
import io.softserve.goadventures.models.User;
import io.softserve.goadventures.repositories.CategoryRepository;
import io.softserve.goadventures.repositories.EventRepository;
import io.softserve.goadventures.services.EventService;
import io.softserve.goadventures.services.JWTService;
import io.softserve.goadventures.services.UserService;
import net.bytebuddy.dynamic.loading.ClassInjector;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.results.ResultMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.nio.charset.Charset;
import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import static org.hamcrest.Matchers.*;

public class EventControllerTest {

    @InjectMocks
    EventController eventController;

    @Mock
    EventService eventService;

    @Mock
    CategoryRepository categoryRepository;

    @Mock
    EventRepository eventRepository;

    @Mock
    EventDtoBuilder eventDtoBuilder;

    @Mock
    ModelMapper modelMapper;

    @Mock
    UserService userService;

    @Mock
    JWTService jwtService;

    @Mock
    View mockView;

    private MockMvc mockMvc;
    private UserAuthDto authDto;
    private User user;
    private User userConfirm;
    private String token;
    CategoryDto categoryDto = new CategoryDto( "first");
    private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
    private static final MediaType TEXT_PLAIN = new MediaType(MediaType.TEXT_PLAIN.getType(), MediaType.TEXT_PLAIN.getSubtype(), Charset.forName("ISO-8859-1"));


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(eventController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers(new ViewResolver() {
                    @Override
                    public View resolveViewName(String viewName, Locale locale) throws Exception {
                        return new MappingJackson2JsonView();
                    }
                })
                .build();
    }

    @Test
    public void create_ShouldAddEventAndReturnResponseEntity_Test() throws Exception{
        Category category = new Category( "first");
        Event first = new Event("topic1", "", "", "location", 25.33, 24.33, "description", category);
        first.setId(1);
        GalleryDto galleryDTO = new GalleryDto();
        EventDTO firstDTO = new EventDTO(1, "topic1", "", "", "location", 25.33, 24.33, "description", 1, categoryDto, galleryDTO);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(firstDTO);

        when(modelMapper.map(firstDTO, Event.class)).thenReturn(first);
        when(categoryRepository.findByCategoryName(firstDTO.getCategory().getCategoryName())).thenReturn(category);
    }

    @Test
    public void createCategory_ShouldAddCategoryAndReturnResponseEntity() throws Exception{
        Category category = new Category( "Skateboarding");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(category);

        when(categoryRepository.save(category)).thenReturn(category);

        this.mockMvc.perform(post("/event/category").contentType(APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TEXT_PLAIN))
                .andExpect(content().string("Category created"));
    }

    @Test
    public void getAllEvents_ShouldReturnFoundEvents() throws Exception{
        Category category = new Category( "Skateboarding");
        Event first = new Event("topic1", "", "", "location", 25.33, 24.33, "description", category);
        Event second = new Event("topic2", "", "", "location", 25.33, 24.33, "description", category);
        first.setId(1);
        second.setId(2);
        GalleryDto galleryDTO = new GalleryDto();
        EventDTO firstDTO = new EventDTO(1, "topic1", "", "", "location", 25.33, 24.33, "description", 1, categoryDto, galleryDTO);
        EventDTO secondDTO = new EventDTO(2, "topic2", "", "", "location", 25.33, 24.33, "description", 1, categoryDto, galleryDTO);

        List<Event> events = new ArrayList<>();
        events.add(first);
        events.add(second);
        Page<Event> eventPage = new PageImpl<>(events);
        Pageable pageable = PageRequest.of(0,15, Sort.Direction.ASC,"id");

        List<EventDTO> expectedDTO = new ArrayList<>();
        expectedDTO.add(firstDTO);
        expectedDTO.add(secondDTO);
        Page<EventDTO> eventDTOPage = new PageImpl<>(expectedDTO);

        when(eventService.getAllEvents(pageable)).thenReturn(eventPage);
        when(eventDtoBuilder.convertToDto(eventPage)).thenReturn(eventDTOPage);

        mockMvc.perform(get("/event/all"))
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

        verify(eventService, times(1)).getAllEvents(pageable);
        verify(eventDtoBuilder, times(1)).convertToDto(eventPage);
        verifyNoMoreInteractions(eventService, eventDtoBuilder);
    }

    @Test
    public void getAllEvents_CantFindEvents_ShouldReturnEndOfPages() throws Exception{
        Category category = new Category( "Skateboarding");
        Event first = new Event("topic1", "", "", "location", 25.33, 24.33, "description", category);
        Event second = new Event("topic2", "", "", "location", 25.33, 24.33, "description", category);
        first.setId(1);
        second.setId(2);
        List<Event> events = new ArrayList<>();
        events.add(first);
        events.add(second);
        Page<Event> eventPage = new PageImpl<>(events);
        Pageable pageable = PageRequest.of(0,15, Sort.Direction.ASC,"id");

        when(eventService.getAllEvents(pageable)).thenReturn(null);

        mockMvc.perform(get("/event/all"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.publicError").value("Server error, try again later"))
                .andExpect(jsonPath("$.innerError").value("Pageable error"));

        verify(eventService, times(1)).getAllEvents(pageable);
        verifyNoMoreInteractions(eventService);
    }

    @Test
    public void getAllCategory_ShouldReturnFoundCategories() throws Exception{
        Category first = new Category("Skate");
        Category second = new Category("Second");
        first.setId(1);
        second.setId(2);

        List<Category> expectedList = new ArrayList<>();
        expectedList.add(first);
        expectedList.add(second);

        when(categoryRepository.findAll()).thenReturn(expectedList);

        mockMvc.perform(get("/event/allCategory"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].categoryName").value("Skate"))
                .andExpect(jsonPath("$[1].id").value("2"))
                .andExpect(jsonPath("$[1].categoryName").value("Second"));

        verify(categoryRepository, times(1)).findAll();
        verifyNoMoreInteractions(categoryRepository);
    }

    @Test
    public void updateEvent_ShouldReturnResponseEntity() throws Exception{
        Category category = new Category( "Skateboarding");
        Event first = new Event("topic1", "", "", "location", 25.33, 24.33, "description", category);
        int id = 1;
        first.setId(id);
        GalleryDto galleryDTO = new GalleryDto();
        EventDTO firstDTO = new EventDTO(1, "topic1", "", "", "location", 25.33, 24.33, "description", 1, categoryDto, galleryDTO);

        when(eventService.getEventById(id)).thenReturn(first);
        when(eventService.updateEvent(first)).thenReturn(first);
        when(modelMapper.map(first,EventDTO.class)).thenReturn(firstDTO);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(firstDTO);

        mockMvc.perform(put("/event/update/" + id).contentType(APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.topic").value("topic1"))
                ;

        verify(eventService, times(1)).getEventById(id);
        verify(eventService, times(1)).updateEvent(first);
        verifyNoMoreInteractions(eventService);
    }

    @Test
    public void updateEvent_EventDoesNotExist_ShouldReturnResponseEntity() throws Exception{
        int id = 1;
        GalleryDto galleryDTO = new GalleryDto();
        EventDTO firstDTO = new EventDTO(1, "topic1", "", "", "location", 25.33, 24.33, "description", 1, categoryDto, galleryDTO);

        when(eventService.getEventById(id)).thenReturn(null);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(firstDTO);

        mockMvc.perform(put("/event/update/" + id).contentType(APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().is(500))
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.publicError").value("Server error, try again later"))
                .andExpect(jsonPath("$.innerError").value("java.io.IOException: Event does not exist"));

        verify(eventService, times(1)).getEventById(id);
        verifyNoMoreInteractions(eventService);
    }

    @Test
    public void getAllEventsByCategoryId_ShouldReturnFoundEvents() throws Exception{
        int categoryId = 3;
        Category category = new Category( "Skateboarding");
        category.setId(categoryId);
        Event first = new Event("topic1", "", "", "location", 25.33, 24.33, "description", category);
        Event second = new Event("topic2", "", "", "location", 25.33, 24.33, "description", category);
        first.setId(1);
        second.setId(2);

        List<Event> events = new ArrayList<>();
        events.add(first);
        events.add(second);
        Page<Event> eventPage = new PageImpl<>(events);
        Pageable pageable = PageRequest.of(0,15, Sort.Direction.ASC,"id");

        when(eventRepository.findByCategoryId(categoryId, pageable)).thenReturn(eventPage);

        mockMvc.perform(get("/event/category/" + categoryId))
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

        verify(eventRepository, times(1)).findByCategoryId(categoryId, pageable);
        verifyNoMoreInteractions(eventRepository);
    }

    @Test
    public void getEvent_ShouldReturnFoundEvent() throws Exception{
        int eventId = 1;
        Category category = new Category( "Skateboarding");
        Event first = new Event("topic1", "", "", "location", 25.33, 24.33, "description", category);
        first.setId(eventId);

        when(eventService.getEventById(eventId)).thenReturn(first);

        mockMvc.perform(get("/event/" + eventId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.topic").value("topic1"));

        verify(eventService, times(1)).getEventById(eventId);
        verifyNoMoreInteractions(eventService);
    }

    @Test
    public void delete_ShouldDeleteEventAndReturnResponseEntity() throws Exception{
        int eventId = 1;
        String token = "sdfsds";
        Category category = new Category( "Skateboarding");
        Event event = new Event("topic", "", "", "location", 25.33, 24.33, "description", category);
        event.setId(eventId);
        User user = new User("fullname", "Email", "password");

        when(eventService.getEventById(eventId)).thenReturn(event);
        when(eventService.delete(user, event)).thenReturn(true);
        when(userService.getUserByEmail(jwtService.parseToken(token))).thenReturn(user);

        this.mockMvc.perform(delete("/event/delete").header("Authorization", token)
                .header("EventId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TEXT_PLAIN))
                .andExpect(content().string("Event deleted"));
    }

    @Test
    public void close_ShouldCloseEventAndReturnResponseEntity() throws Exception{
        int eventId = 1;
        String token = "sdfsds";
        Category category = new Category( "Skateboarding");
        Event event = new Event("topic", "", "", "location", 25.33, 24.33, "description", category);
        event.setId(eventId);
        User user = new User("fullname", "Email", "password");

        when(eventService.getEventById(eventId)).thenReturn(event);
        when(eventService.closeEvent(user, event)).thenReturn(true);
        when(userService.getUserByEmail(jwtService.parseToken(token))).thenReturn(user);

        this.mockMvc.perform(post("/event/close").header("Authorization", token)
                .header("EventId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TEXT_PLAIN))
                .andExpect(content().string("Event closed"));
    }

    @Test
    public void open_ShouldOpenEventAndReturnResponseEntity() throws Exception{
        int eventId = 1;
        String token = "sdfsds";
        Category category = new Category( "Skateboarding");
        Event event = new Event("topic", "", "", "location", 25.33, 24.33, "description", category);
        event.setId(eventId);
        User user = new User("fullname", "Email", "password");

        when(eventService.getEventById(eventId)).thenReturn(event);
        when(eventService.openEvent(user, event)).thenReturn(true);
        when(userService.getUserByEmail(jwtService.parseToken(token))).thenReturn(user);

        this.mockMvc.perform(post("/event/open").header("Authorization", token)
                .header("EventId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TEXT_PLAIN))
                .andExpect(content().string("Event opened"));
    }
}