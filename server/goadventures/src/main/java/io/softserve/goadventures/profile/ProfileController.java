package io.softserve.goadventures.profile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.softserve.goadventures.auth.dtoModels.UserAuthDto;
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
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
@RequestMapping("profile")
public class ProfileController {


    private final Logger logger = LoggerFactory.getLogger(ProfileController.class);

    private final JWTService jwtService;
    private final UserService userService;
    private final EmailValidator emailValidator ;
    private final PasswordValidator passwordValidator;

    @Autowired
    public ProfileController(JWTService jwtService, UserService userService, UserRepository userRepository, EmailValidator emailValidator, PasswordValidator passwordValidator) {
        this.jwtService = jwtService;
        this.userService = userService;

        this.emailValidator = emailValidator;
        this.passwordValidator = passwordValidator;
    }

    @GetMapping("/page")
    public Profile getProfileUser(@RequestHeader("Authorization") String token) throws UserNotFoundException {
        logger.info("\n\n\n\tDo token:" + token + "\n\n\n");



        User user = userService.getUserByEmail(jwtService.parseToken(token));


        Profile profile = new Profile(user.getFullname(), user.getUsername(), user.getEmail());

        logger.info(user.getFullname() + " " + user.getUsername() + " " + user.getEmail());


        return profile;
    }


    @PostMapping(path = "/edit-profile", produces = {MediaType.APPLICATION_JSON_VALUE} )
    public ResponseEntity<String> EditProfileData(@RequestHeader(value="Authorization") String authorizationHeader,
                                                  @RequestBody UserAuthDto changeThisUser) throws UserNotFoundException, JsonProcessingException {

        String newToken = "";
        logger.info(authorizationHeader);
        logger.info("email " + changeThisUser.getEmail() +
                " fullname " + changeThisUser.getFullName() +
                " username" + changeThisUser.getUserName());
        User user = userService.getUserByEmail(jwtService.parseToken(authorizationHeader));   //user with old data

        if(emailValidator.validateEmail(changeThisUser.getEmail())) {
            user.setEmail(changeThisUser.getEmail());
            logger.info("email changed, new email:  " +user.getEmail());
        }
//        if(passwordValidator.validatePassword(changeThisUser.getPassword())){
//            user.setPassword(changeThisUser.getPassword());
//            logger.info("passwrod changed, new password:  " + changeThisUser.getPassword());
//        }

        if(!(changeThisUser.getPassword().equals(""))){
            if(BCrypt.checkpw(changeThisUser.getPassword(),user.getPassword())){    //check current pass
                logger.info("current password correct");

                    user.setPassword(changeThisUser.getNewPassword());                      //if valide, set new pass
                
                logger.info("password changed, new password:  " + changeThisUser.getPassword());

            } else{
                return ResponseEntity.badRequest().body("Current password is wrong!");                     //wrong password
            }
        }
        if(!(changeThisUser.getFullName().equals(""))) {
            user.setFullname(changeThisUser.getFullName());
        }

        if(!(changeThisUser.getUserName().equals(""))) {
            user.setUsername(changeThisUser.getUserName());
        }


        userService.updateUser(user);

        logger.info("new data " + user.getEmail() + " " + user.getUsername() + " " + user.getFullname() + " " + user.getPassword());

        newToken = jwtService.createToken(user);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setBearerAuth(newToken);
        responseHeaders.set("token", newToken);   


        //ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

        return ResponseEntity.ok().headers(responseHeaders).body("Data was changed");

    }































}
