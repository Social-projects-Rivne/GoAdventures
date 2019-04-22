package io.softserve.goadventures.controllers;

import io.softserve.goadventures.dto.CategoryDto;
import io.softserve.goadventures.dto.EventDTO;
import io.softserve.goadventures.dto.GalleryDto;
import io.softserve.goadventures.dto.UserDto;
import io.softserve.goadventures.models.Category;
import io.softserve.goadventures.models.Event;
import io.softserve.goadventures.models.User;
import io.softserve.goadventures.services.EventDtoBuilder;
import io.softserve.goadventures.services.EventService;
import io.softserve.goadventures.services.JWTService;
import io.softserve.goadventures.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class ProfileControllerTest {

    @InjectMocks
    ProfileController profileControllerMock;

    @Mock
    EventService eventServiceMock;

    @Mock
    UserService userServiceMock;

    @Mock
    JWTService jwtServiceMock;

    @Mock
    EventDtoBuilder eventDtoBuilderMock;

    @Mock
    ModelMapper modelMapperMock;

    private MockMvc mockMvc;
    private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    private Category category = new Category( "Skateboarding");
    private CategoryDto categoryDto = new CategoryDto( "Skateboarding");
    private Event first = new Event("topic1", "", "", "location", 25.33, 24.33, "description", category);
    private Event second = new Event("topic2", "", "", "location", 25.33, 24.33, "description", category);
    private GalleryDto galleryDTO = new GalleryDto();
    private EventDTO firstDTO = new EventDTO(1, "topic1", "", "", "location", 25.33, 24.33, "description", 1, categoryDto, galleryDTO);
    private EventDTO secondDTO = new EventDTO(2, "topic2", "", "", "location", 25.33, 24.33, "description", 1, categoryDto, galleryDTO);
    private User user = new User();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        first.setId(1);
        second.setId(2);
        user.setId(1);

        mockMvc = standaloneSetup(profileControllerMock)
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
    public void getProfileUser_Test() throws Exception{
        String token = "dsfdsfds";
        String email = "email";
        User user = new User();
        user.setEmail(email);
        UserDto userDto = new UserDto();
        userDto.setEmail(email);

        when(jwtServiceMock.parseToken(token)).thenReturn(email);
        when(userServiceMock.getUserByEmail(email)).thenReturn(user);
        when(modelMapperMock.map(user, UserDto.class)).thenReturn(userDto);

        mockMvc.perform(get("/profile/page")
                .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.email").value(email));
    }

    @Test
    public void getAllEvents_ShouldReturnFoundEvents() throws Exception{
        String token = "dsfdsfds";
        String email = "email";
        UserDto userDto = new UserDto();
        userDto.setEmail(email);

        List<Event> events = new ArrayList<>();
        events.add(first);
        events.add(second);
        Page<Event> eventPage = new PageImpl<>(events);
        Pageable pageable = PageRequest.of(0,15, Sort.Direction.ASC,"id");

        List<EventDTO> expectedDTO = new ArrayList<>();
        expectedDTO.add(firstDTO);
        expectedDTO.add(secondDTO);
        Page<EventDTO> eventDTOPage = new PageImpl<>(expectedDTO);

        when(jwtServiceMock.parseToken(token)).thenReturn(email);
        when(userServiceMock.getUserByEmail(email)).thenReturn(user);
        when(eventServiceMock.getAllEventsByOwner(pageable, user.getId())).thenReturn(eventPage);
        when(eventDtoBuilderMock.convertToDto(eventPage)).thenReturn(eventDTOPage);

        mockMvc.perform(get("/profile/all-events")
                .header("Authorization", token))
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
    }
}
