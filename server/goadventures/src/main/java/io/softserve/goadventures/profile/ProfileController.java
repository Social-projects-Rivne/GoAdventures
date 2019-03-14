package io.softserve.goadventures.profile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.softserve.goadventures.auth.service.JWTService;
import io.softserve.goadventures.user.model.User;
import io.softserve.goadventures.user.repository.UserRepository;
import io.softserve.goadventures.user.service.UserNotFoundException;
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


    private final Logger logger = LoggerFactory.getLogger(ProfileController.class);

    private final JWTService jwtService;
    private final UserService userService;

    @Autowired
    public ProfileController(JWTService jwtService, UserService userService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @GetMapping("page")
    public Profile getProfileUser(@RequestHeader("Authorization") String token) throws UserNotFoundException {
        logger.info("\n\n\n\tDo token:" + token + "\n\n\n");



        User user = userService.getUserByEmail(jwtService.parseToken(token));


        Profile profile = new Profile(user.getFullname(), user.getUsername(), user.getEmail());

        logger.info(user.getFullname() + " " + user.getUsername() + " " + user.getEmail());

        logger.info(profile.toString());

        return profile;
    }


    @PostMapping(path = "/edit-profile", produces = {MediaType.APPLICATION_JSON_VALUE} )
    public ResponseEntity<String> EditProfileData(@RequestHeader(value="Authorization") String authorizationHeader, @RequestBody User changeThisUser
    ) throws UserNotFoundException, JsonProcessingException {
        String token = authorizationHeader;
        String newToken = "";
        logger.info(token);
        logger.info("email " + changeThisUser.getEmail() + " fullname " + changeThisUser.getFullname() + " username" + changeThisUser.getUsername());
        User user = userService.getUserByEmail(jwtService.parseToken(token));   //user with old data



        //changeThisUser.setId(user.getId());


        if(!(changeThisUser.getFullname().equals(""))) user.setFullname(changeThisUser.getFullname());
        if(!(changeThisUser.getEmail().equals(""))) user.setEmail(changeThisUser.getEmail());
        if(!(changeThisUser.getUsername().equals(""))) user.setUsername(changeThisUser.getUsername());

        userService.updateUser(user);

        logger.info("new data " + user.getEmail() + " " + user.getUsername() + " " + user.getFullname());

        newToken = jwtService.createToken(user);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setBearerAuth(newToken);
        responseHeaders.set("token", newToken);


        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

        return ResponseEntity.ok().headers(responseHeaders).body("Data was changed");

    }































}
