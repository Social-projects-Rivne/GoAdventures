package io.softserve.goadventures.services;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;

@Service
public class GeneratePasswordService {
    public GeneratePasswordService() {
    }

    public String generatePassword() {
        return RandomStringUtils.random(10, true, true);
    }
}
