package io.softserve.goadventures.services;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class GeneratePasswordServiceTest {

    @InjectMocks
    GeneratePasswordService passwordService;

    @Mock
    MailContentBuilder mailContentBuilder;

    @Test
    public void generatePassword() {



    }
}