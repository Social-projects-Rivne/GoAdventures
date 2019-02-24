package io.softserve.goadventures.auth.controllers;

import io.softserve.goadventures.user.model.User;
import io.softserve.goadventures.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("auth")
public class AuthController {
    private Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final UserRepository userRepository;

    @Autowired
    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/list")
    public Iterable<User> list() {
        return userRepository.findAll();
    }

    @PostMapping
    public void signUp(@RequestBody User user) {
        if (!checkEmail(user.getEmail())) {
            try {
                userRepository.save(hashPassword(user));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            logger.info("signUp: New user create.");
        } else {
            logger.info("signUp: This user is already exist.");
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

    private User hashPassword(User user) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");

        byte[] bytes = messageDigest.digest(user.getPassword().getBytes());

        StringBuilder str = new StringBuilder();

        for (byte b : bytes) {
            str.append(b);
        }

        user.setPassword(str.toString());

        return user;
    }
}
