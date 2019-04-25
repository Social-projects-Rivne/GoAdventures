package io.softserve.goadventures.controllers;

import io.softserve.goadventures.dto.UserAuthDto;
import io.softserve.goadventures.enums.UserStatus;
import io.softserve.goadventures.models.User;
import io.softserve.goadventures.services.JWTService;
import io.softserve.goadventures.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.nio.charset.Charset;
import java.util.Locale;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class AuthControllerTest {

    @InjectMocks
    AuthController authControllerMock;

    @Mock
    UserService userServiceMock;

    @Mock
    JWTService jwtServiceMock;

    private MockMvc mockMvc;
    private UserAuthDto authDto;
    private User user;
    @Mock
    private User userConfirm;
    private String token;
    private static final MediaType TEXT_PLAIN = new MediaType(MediaType.TEXT_PLAIN.getType(), MediaType.TEXT_PLAIN.getSubtype(), Charset.forName("ISO-8859-1"));


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        authDto = new UserAuthDto();
        authDto.setEmail("auth_controller@test.com");
        authDto.setFullname("Auh Controller");
        authDto.setPassword("setupcontroller");

        user = new User();
        user.setEmail(authDto.getEmail());
        user.setFullname(authDto.getFullname());
        user.setStatusId(UserStatus.PENDING.getUserStatus());
        user.setUsername(user.getEmail().split("@")[0]);
        user.setPassword(BCrypt.hashpw(authDto.getPassword(), BCrypt.gensalt()));

        userConfirm = new User();
        BeanUtils.copyProperties(user, userConfirm);
        userConfirm.setStatusId(UserStatus.ACTIVE.getUserStatus());

        token = jwtServiceMock.createToken(authDto.getEmail());

        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(authControllerMock)
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
    public void confirmUserAccount_Test() {
        String authToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsb3JkLm9mLnRoZS5yaW5nc0BnbWFpbC5jb20iLCJleHAiOjE1NTU1ODExNTZ9.-cBQ7gHle7UEJWoXUh7cgrRyNzjakssJBQexzt_RccM";
        when(userServiceMock.confirmUser(authDto.getEmail())).thenReturn(userConfirm);
        when(userConfirm.getEmail()).thenReturn("email");
        when(jwtServiceMock.createToken(authDto.getEmail())).thenReturn(authToken);
        when(jwtServiceMock.parseToken(token)).thenReturn(authDto.getEmail());
    }

    @Test
    public void signIn_Test() {
    }

    @Test
    public void signOut_Test() throws Exception{
        token = "dsfdsn2ok";
        String email = "email";

        when(jwtServiceMock.parseToken(token)).thenReturn(email);

        mockMvc.perform(put("/auth/sign-out")
                .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TEXT_PLAIN))
                .andExpect(content().string("See ya"));
    }

    @Test
    public void sentRecoveryEmail_Test(){

    }
}