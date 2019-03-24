package io.softserve.goadventures.user.controller;
import io.softserve.goadventures.auth.service.JWTService;
import io.softserve.goadventures.errors.InvalidPasswordErrorMesage;
import io.softserve.goadventures.user.dto.UserDto;
import io.softserve.goadventures.user.dto.UserUpdateDto;
import io.softserve.goadventures.user.model.User;
import io.softserve.goadventures.user.service.EmailValidator;
import io.softserve.goadventures.user.service.PasswordValidator;
import io.softserve.goadventures.user.service.UserNotFoundException;
import io.softserve.goadventures.user.service.UserService;
import org.modelmapper.ModelMapper;
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
    private final InvalidPasswordErrorMesage invalidPasswordErrorMesage;
    private final ModelMapper modelMapper;

    @Autowired
    public ProfileController(JWTService jwtService,
                             UserService userService,
                             EmailValidator emailValidator,
                             PasswordValidator passwordValidator,
                             InvalidPasswordErrorMesage invalidPasswordErrorMesage, ModelMapper modelMapper) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.emailValidator = emailValidator;
        this.passwordValidator = passwordValidator;
        this.invalidPasswordErrorMesage = invalidPasswordErrorMesage;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/page")
    public UserDto getProfileUser(@RequestHeader("Authorization") String token) throws UserNotFoundException {
        logger.info("\n\n\n\tDo token:" + token + "\n\n\n");
        User user = userService.getUserByEmail(jwtService.parseToken(token));
        return modelMapper.map(user, UserDto.class);
    }


    @PutMapping(path = "/edit-profile", produces = {MediaType.APPLICATION_JSON_VALUE} )
    public ResponseEntity<String> EditProfileData(@RequestHeader(value="Authorization") String authorizationHeader,
                                                  @RequestBody UserUpdateDto updateUser) throws UserNotFoundException{
        String newToken;
        User user = userService.getUserByEmail(jwtService.parseToken(authorizationHeader));   //user with old data
        if(!(updateUser.getPassword().equals(""))){
            if(BCrypt.checkpw(updateUser.getPassword(),user.getPassword())){    //check current pass
                logger.info("current password correct");
                if(passwordValidator.validatePassword(updateUser.getNewPassword())){
                    updateUser.setPassword(updateUser.getNewPassword());          //if valide, set new pass
                    logger.info("password changed, new password:  " + updateUser.getPassword());
                }
            } else{
                return ResponseEntity.badRequest().body(invalidPasswordErrorMesage.getErrorMesage());        //wrong password
            }
        }
        modelMapper.map(updateUser, user);
        userService.updateUser(user);
        newToken = jwtService.createToken(user);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setBearerAuth(newToken);
        responseHeaders.set("token", newToken);
        return ResponseEntity.ok().headers(responseHeaders).body("Data was changed");
    }
}
