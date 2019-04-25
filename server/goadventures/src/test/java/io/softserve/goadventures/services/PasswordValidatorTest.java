package io.softserve.goadventures.services;

import io.softserve.goadventures.utils.Validator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class PasswordValidatorTest {

    @InjectMocks
    Validator passwordValidatorMock;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void validatePassword_Test(){
        String invalidPassword = "ssds12?_.zjпрн";
        assertFalse(passwordValidatorMock.validatePassword(invalidPassword));
    }
}
