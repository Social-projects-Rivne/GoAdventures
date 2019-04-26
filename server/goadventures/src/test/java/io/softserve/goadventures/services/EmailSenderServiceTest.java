package io.softserve.goadventures.services;

import io.softserve.goadventures.models.User;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.mail.Message;
import javax.mail.Transport;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class EmailSenderServiceTest {

    @InjectMocks
    EmailSenderService emailSenderService;

    @Mock
    private MailContentBuilder contentBuilder;

    private Properties props;
    private Transport transport;
    private Message msg;
    private User user;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "false");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.port", "587");
    }

    @Test
    void sendEmail() {
        String confirmationToken = "Token";
        user = new User();
    }

    @Test
    void sendNewPassword() {
    }

    @Test
    void sendRecoveryEmail() {
    }

    @Test
    void eventEmailNotidication() {
    }
}