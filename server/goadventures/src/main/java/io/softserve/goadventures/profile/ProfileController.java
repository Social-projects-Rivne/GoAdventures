package io.softserve.goadventures.profile;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.softserve.goadventures.auth.service.JWTService;
import io.softserve.goadventures.user.model.User;
import io.softserve.goadventures.user.repository.UserRepository;
import io.softserve.goadventures.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;




@CrossOrigin
@RestController
@RequestMapping("profile")
public class ProfileController {
    private UserRepository userRepository;

    private final Logger logger = LoggerFactory.getLogger(ProfileController.class);

    private final JWTService jwtService;
    private final UserService userService;

    @Autowired
    public ProfileController(JWTService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @GetMapping("{userName}")
    public Profile getProfileUser(@PathVariable String userName) {
        User user = userRepository.findByUsername(userName);

        Profile profile = new Profile(user.getFullname(), user.getUsername(), user.getEmail());

        logger.info(user.getFullname() + " " + user.getUsername() + " " + user.getEmail());

        logger.info(profile.toString());

        return profile;
    }


    @GetMapping(path = "/edit-profile", produces = {MediaType.APPLICATION_JSON_VALUE} )
    public void EditProfileData(@RequestHeader(value="Authorization") String authorizationHeader, @RequestBody User changeThisUser
    )  {
        String token = authorizationHeader;
        logger.info(authorizationHeader);

        User user = userService.getUserByEmail(jwtService.parseToken(token));
        if(user!=null){
            logger.info("Old data " + user.getEmail() + " " + user.getUsername());
            user.setUsername(changeThisUser.getUsername());
            user.setEmail(changeThisUser.getEmail());
            userService.updateUser(user);


            logger.info("new data "+ user.getEmail() + " " + user.getUsername());


        }







    }




}
