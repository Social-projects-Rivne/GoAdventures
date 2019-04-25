package io.softserve.goadventures.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class GeneratePasswordServiceTest {

    @InjectMocks
    GeneratePasswordService passwordService;

    @Mock
    MailContentBuilder mailContentBuilder;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void generatePassword_Test() {

    }
}