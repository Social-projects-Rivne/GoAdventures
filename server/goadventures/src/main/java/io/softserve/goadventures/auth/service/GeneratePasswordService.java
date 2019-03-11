package io.softserve.goadventures.auth.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

@Service
public class GeneratePasswordService {
    public GeneratePasswordService() {
    }

    public String generatePassword(String email) {
        String newPassword = RandomStringUtils.random(10, true, true);

        LoggerFactory.getLogger(GeneratePasswordService.class).info("\n\n\tNew generate password: " + newPassword + "\n");

        try {
            EmailSenderService senderService = new EmailSenderService();
            senderService.sendNewPassword(email, newPassword);
        } catch (UnsupportedEncodingException | MessagingException e) {
            e.printStackTrace();
        }
        return newPassword;
    }
}
