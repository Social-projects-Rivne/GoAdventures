package io.softserve.goadventures.services;

import io.softserve.goadventures.models.User;
import io.softserve.goadventures.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import static org.mockito.Mockito.*;

public class MailContentBuilderTest {

    @InjectMocks
    MailContentBuilder mailContentBuilderMock;

    @Mock
    private TemplateEngine templateEngineMock;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void signUp(){
        String message = "message";
        String confirmationToken = "dsfdsf32r4b/";
        Context context = new Context();
        context.setVariable("fullname", message);
        context.setVariable("confirmationToken", confirmationToken);
        when(templateEngineMock.process("confirm-sign-up", context)).thenReturn("string");
        //templateEngineMock.process("confirm-sign-up", context);
        mailContentBuilderMock.signUp(message, confirmationToken);
        verify(templateEngineMock,times(1)).process("confirm-sign-up", context);
    }
}
