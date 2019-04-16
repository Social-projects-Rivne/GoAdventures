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
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class AuthControllerTest {

    @InjectMocks
    AuthController authController;

    @Mock
    UserService userService;

    @Mock
    JWTService jwtService;

    private UserAuthDto authDto;
    private User user;
    private User userConfirm;
    private String token;

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

        token = jwtService.createToken(authDto.getEmail());
    }

    @Test
    public void confirmUserAccount() {
        when(userService.confirmUser(authDto.getEmail())).thenReturn(userConfirm);
        when(jwtService.createToken(authDto.getEmail())).thenReturn("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsb3JkLm9mLnRoZS5yaW5nc0BnbWFpbC5jb20iLCJleHAiOjE1NTU1ODExNTZ9.-cBQ7gHle7UEJWoXUh7cgrRyNzjakssJBQexzt_RccM");
        when(jwtService.parseToken(token)).thenReturn(authDto.getEmail());

        ResponseEntity<User> userConfirmResp = authController.confirmUserAccount(token);

        assertNotNull(userConfirmResp);
        assertEquals(1, Objects.requireNonNull(userConfirmResp.getBody()).getStatusId());
    }

    @Test
    public void signIn() {
        when(userService.singIn(anyString(), anyString())).thenReturn("User log in");

        ResponseEntity<String> signInResponse = authController.signIn(authDto);

        assertEquals("User log in", signInResponse.getBody());
    }
}