package io.softserve.goadventures.controllers;

import io.softserve.goadventures.services.JWTService;
import io.softserve.goadventures.dto.EventDTO;
import io.softserve.goadventures.models.Event;
import io.softserve.goadventures.dto.EventDtoBuilder;
import io.softserve.goadventures.services.EventService;
import io.softserve.goadventures.dto.UserDto;
import io.softserve.goadventures.dto.UserUpdateDto;
import io.softserve.goadventures.models.User;
import io.softserve.goadventures.services.EmailValidator;
import io.softserve.goadventures.services.PasswordValidator;
import io.softserve.goadventures.errors.UserNotFoundException;
import io.softserve.goadventures.services.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
    private final ModelMapper modelMapper;
    private final EventService eventService;
    private final EventDtoBuilder eventDtoBuilder;

    @Autowired
    public ProfileController(JWTService jwtService, UserService userService,
                             EmailValidator emailValidator, PasswordValidator passwordValidator,
                             ModelMapper modelMapper, EventService eventService,
                             EventDtoBuilder eventDtoBuilder) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.emailValidator = emailValidator;
        this.passwordValidator = passwordValidator;
        this.modelMapper = modelMapper;
        this.eventDtoBuilder = eventDtoBuilder;
        this.eventService = eventService;
    }

    @GetMapping("/page")
    public UserDto getProfileUser(@RequestHeader("Authorization") String token) throws UserNotFoundException {
        User user = userService.getUserByEmail(jwtService.parseToken(token));
        return modelMapper.map(user, UserDto.class);
    }

    @PutMapping(path = "/edit-profile", produces = {MediaType.APPLICATION_JSON_VALUE} )
    public ResponseEntity<String> EditProfileData(@RequestHeader(value="Authorization") String authorizationHeader,
                                                  @RequestBody UserUpdateDto updateUser) throws UserNotFoundException{
        String newToken;
        User user = userService.getUserByEmail(jwtService.parseToken(authorizationHeader));   //user with old data

        if(!(updateUser.getPassword().equals(""))) {
            if(BCrypt.checkpw(updateUser.getPassword(),user.getPassword())) {    //check current pass
                logger.info("current password correct");
                if(passwordValidator.validatePassword(updateUser.getNewPassword())) {
                    updateUser.setPassword(updateUser.getNewPassword());          //if valide, set new pass
                    logger.info("password changed, new password:  " + updateUser.getPassword());
                }
            } else {
                return ResponseEntity.badRequest().body("Current password is wrong!");        //wrong password
            }
        }

        modelMapper.map(updateUser, user);
        userService.updateUser(user);
        newToken = jwtService.createToken(user.getEmail());
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setBearerAuth(newToken);
        responseHeaders.set("token", newToken);

        return ResponseEntity.ok().headers(responseHeaders).body("Data was changed");
    }

    @GetMapping("/all-events")
    public ResponseEntity<?> getAllEvents(Pageable eventPageable, @RequestHeader("Authorization") String token) throws UserNotFoundException {
        User user = userService.getUserByEmail(jwtService.parseToken(token));
        Page<Event> eventsPage = eventService.getAllEventsByOwner(eventPageable, user.getId());

        if(eventsPage != null) {
            return new ResponseEntity<Slice<EventDTO>>(eventDtoBuilder.convertToDto(eventsPage), HttpStatus.OK);
        } else {
            return  ResponseEntity.badRequest().body("End of pages");
        }
    }
}