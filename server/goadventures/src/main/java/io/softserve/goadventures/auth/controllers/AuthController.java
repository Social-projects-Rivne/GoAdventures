package io.softserve.goadventures.auth.controllers;

import io.softserve.goadventures.auth.service.JwtService;
import io.softserve.goadventures.user.model.User;
import io.softserve.goadventures.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthController {
    private Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Autowired
    public AuthController(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @GetMapping("/list")
    public Iterable<User> list() {
        return userRepository.findAll();
    }

    @PostMapping
    public void signUp(@RequestBody User user) {
        if (!checkEmail(user.getEmail())) {
            userRepository.save(user);
            logger.info("signUp: New user create.");
        } else {
            logger.info("signUp: This user is already exist.");
        }

        String confirmationToken = jwtService.createToken(user);

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
