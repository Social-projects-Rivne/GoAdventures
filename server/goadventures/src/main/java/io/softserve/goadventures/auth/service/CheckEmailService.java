package io.softserve.goadventures.auth.service;

import io.softserve.goadventures.user.model.User;
import io.softserve.goadventures.user.service.UserNotFoundException;
import io.softserve.goadventures.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class CheckEmailService {
    private Logger logger = LoggerFactory.getLogger(io.softserve.goadventures.auth.service.CheckEmailService.class);
    private final UserService userService;

    @Autowired
    public CheckEmailService(UserService userService){
        this.userService=userService;
    }

    public boolean checkingEmail(String email){
        try {
            User user = userService.getUserByEmail(email);
            if (user != null) {
                logger.info("checkEmail: " + user.toString());
                return true;
            } else {
                logger.info("checkEmail: can't find this user");
                return false;
            }
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
}
