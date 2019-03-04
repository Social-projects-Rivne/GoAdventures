package io.softserve.goadventures.profile;

import io.softserve.goadventures.user.model.User;
import io.softserve.goadventures.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("profile")
public class ProfileController {
    private UserRepository userRepository;

    private final Logger logger = LoggerFactory.getLogger(ProfileController.class);

    @Autowired
    public ProfileController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("{userName}")
    public Profile getProfileUser(@PathVariable String userName) {
        User user = userRepository.findByUsername(userName);

        Profile profile = new Profile(user.getFullname(), user.getUsername(), user.getEmail());

        logger.info(user.getFullname() + " " + user.getUsername() + " " + user.getEmail());

        logger.info(profile.toString());

        return profile;
    }
}
