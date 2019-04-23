package io.softserve.goadventures.controllers;

import io.softserve.goadventures.models.User;
import io.softserve.goadventures.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserControllerTest {

    @InjectMocks
    UserController userControllerMock;

    @Mock
    UserService userServiceMock;

    private MockMvc mockMvc;
    private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userControllerMock)
                .build();
    }

    @Test
    public void getAllCategory_ShouldReturnFoundCategories() throws Exception{
        User first = new User();
        User second = new User();
        first.setId(1);
        second.setId(2);

        List<User> expectedList = new ArrayList<>();
        expectedList.add(first);
        expectedList.add(second);

        when(userServiceMock.getAllUsers()).thenReturn(expectedList);

        mockMvc.perform(get("/user/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[1].id").value("2"));

        verify(userServiceMock, times(1)).getAllUsers();
        verifyNoMoreInteractions(userServiceMock);
    }
}
