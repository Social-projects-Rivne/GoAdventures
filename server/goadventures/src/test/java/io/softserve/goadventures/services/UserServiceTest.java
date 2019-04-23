package io.softserve.goadventures.services;

import io.softserve.goadventures.dto.UserAuthDto;
import io.softserve.goadventures.enums.UserStatus;
import io.softserve.goadventures.errors.UserNotFoundException;
import io.softserve.goadventures.models.User;
import io.softserve.goadventures.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class UserServiceTest {
    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    private User user;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        user = new User();
        user.setEmail("service@test.com");
        user.setFullname("Junit Mockito");
        user.setPassword("$2a$10$WOqCikzFnLWdm9u67yrfGOUqjCWx7Z9kvjlqY7rqQZgEQ/SqEBccu");
    }

    @Test
    public void getUserById() {
        when(userRepository.findUserById(0)).thenReturn(user);

        User userById = userService.getUserById(0);

        assertNotNull(userById);
        assertEquals("Junit Mockito", userById.getFullname());
    }

    @Test
    public void getUserByEmail() throws UserNotFoundException {
        when(userRepository.findByEmail("service@test.com")).thenReturn(user);

        User userByEmail = userService.getUserByEmail("service@test.com");

        assertNotNull(userByEmail);
    }

    @Test
    public void confirmUser() {
        when(userRepository.findByEmail("service@test.com")).thenReturn(user);

        User confirm = userService.confirmUser("service@test.com");
        assertNotNull(confirm);
        assertEquals(1, confirm.getStatusId());
    }

    @Test
    public void addUser() {
        when(userRepository.findByEmail(anyString())).thenReturn(null);
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserAuthDto authDto = new UserAuthDto();
        authDto.setEmail("service@test.com");
        authDto.setFullname("Junit Mockito");
        authDto.setPassword("password");

        User addUser = userService.addUser(authDto);
        assertNotNull(addUser);
        assertTrue(BCrypt.checkpw("password", user.getPassword()));
        assertEquals("service", addUser.getUsername());
        assertEquals(0, addUser.getStatusId());
    }

    @Test
    public void signIn() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);

        User userIn = userService.confirmUser(user.getEmail());
        UserStatus result = userService.singIn(user.getEmail(), "password");

        assertEquals("LOGGING", String.valueOf(result));
    }

    @Test
    public void signOut() {
        when(userRepository.findByEmail("service@test.com")).thenReturn(user);

        userService.singOut("service@test.com");

        assertEquals(UserStatus.UNACTIVE.getUserStatus(), user.getStatusId());
    }

    @Test
    public void getAllUsers() {
        Iterable<User> iterable = new ArrayList<>();
        ((ArrayList<User>) iterable).add(user);
        ((ArrayList<User>) iterable).add(user);

        when(userRepository.findAll()).thenReturn(iterable);

        assertNotNull(iterable);
        assertEquals(2, ((Collection<User>) userService.getAllUsers()).size());
    }

    @Test
    public void checkingEmail() {
        when(userRepository.findByEmail("service@test.com")).thenReturn(user);

        assertFalse(userService.checkingEmail("service@test.com"));
    }
}