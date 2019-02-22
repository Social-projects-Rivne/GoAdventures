package io.softserve.goadventures.controllers;

import io.softserve.goadventures.entities.User;
import io.softserve.goadventures.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserRepository userRepository;

    @GetMapping("/add")
    public String greeting(
            @RequestParam(name = "name", required = false, defaultValue = "Tima") String name,
            Map<String, Object> model) {

        return "add";
    }

    @PostMapping
    public void add(@RequestParam String fullname,
                    @RequestParam String email,
                    @RequestParam String password) {
        logger.info("add: Entered data = name = " + fullname + "; email = " + email + "; password = " + password);

        User user = new User(fullname, email, password);
        if (!checkEmail(user.getEmail())) {
            userRepository.save(user);
            logger.info("add: New user create.");
        } else {
            logger.info("add: This user is already exist.");
        }

    }

    private boolean checkEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            logger.info("checkEmail: " + user.toString());
            return true;
        } else {
            logger.info("checkEmail: can't find this user");
            return false;
        }
    }
}
