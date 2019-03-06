package io.softserve.goadventures.profile;

import io.softserve.goadventures.auth.service.JWTService;
import io.softserve.goadventures.user.model.User;
import io.softserve.goadventures.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("profile")
public class ProfileController {
    private UserRepository userRepository;

    private final Logger logger = LoggerFactory.getLogger(ProfileController.class);

    @Autowired
    public ProfileController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("page")
    public Profile getProfileUser(@RequestHeader("Authorization") String token) {
        logger.info("\n\n\n\tDo token:" + token + "\n\n\n");

        String email = new JWTService().parseToken(token);

        logger.info("\n\n\n\tPislyatoken:" + token + "\n\n\n");

        User user = userRepository.findByEmail(email);

        Profile profile = new Profile(user.getFullname(), user.getUsername(), user.getEmail());

        logger.info(user.getFullname() + " " + user.getUsername() + " " + user.getEmail());

        logger.info(profile.toString());

        return profile;
    }
}
