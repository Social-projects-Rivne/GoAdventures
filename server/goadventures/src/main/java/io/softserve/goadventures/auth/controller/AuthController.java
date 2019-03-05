package io.softserve.goadventures.auth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.softserve.goadventures.auth.enums.UserStatus;
import io.softserve.goadventures.auth.service.CheckEmailService;
import io.softserve.goadventures.auth.service.EmailSenderService;
import io.softserve.goadventures.auth.service.JWTService;
import io.softserve.goadventures.user.model.User;
import io.softserve.goadventures.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import io.softserve.goadventures.auth.dtoModels.UserAuthDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;
import javax.mail.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

//@CrossOrigin(origins = "http://domain2.com", maxAge = 3600)
@CrossOrigin
@RestController
@RequestMapping("auth")
public class AuthController extends HttpServlet {
    private Logger logger = LoggerFactory.getLogger(io.softserve.goadventures.auth.controller.AuthController.class);
    private final JWTService jwtService;
    private final UserService userService;
    private final EmailSenderService emailSender;
    private final CheckEmailService checkEmail;

    @Autowired
    public AuthController(JWTService jwtService, UserService userService, EmailSenderService emailSender, CheckEmailService checkEmail) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.emailSender = emailSender;
        this.checkEmail = checkEmail;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<String> signUp(@RequestBody User user) throws MessagingException, UnsupportedEncodingException {
        HttpHeaders httpHeaders = new HttpHeaders();
        logger.info(String.valueOf(user));
        if (!checkEmail.checkingEmail(user.getEmail())) {

            user.setStatusId(UserStatus.PENDING.getUserStatus());
            user.setUsername(user.getEmail().split("@")[0]);
            userService.addUser(user);
            logger.info("\nsignUp: New user create.");
            logger.info("\nsignUp detail: \n\t" +
                    user.getFullname() + " : \n\t" +
                    user.getEmail() + " : \n\t" +
                    user.getUsername());

            String confirmationToken = jwtService.createToken(user);
            emailSender.sendEmail(confirmationToken, user);
            return ResponseEntity.ok().headers(httpHeaders).body("user created");
        } else {
            logger.info("\nsignUp: This user is already exist.");

            return ResponseEntity.badRequest().body("user already exist");
        }
    }

  @GetMapping("/confirm-account")
  public ResponseEntity<String> confirmUserAccount(@RequestParam("token") String confirmationToken, HttpServletRequest req) {
      User user = userService.getUserByEmail(jwtService.parseToken(confirmationToken));
      if (user != null) {
          String authToken = jwtService.createToken(user);
          user.setStatusId(UserStatus.ACTIVE.getUserStatus());
          userService.updateUser(user);
          HttpHeaders responseHeaders = new HttpHeaders();
          responseHeaders.setBearerAuth(authToken);
          return ResponseEntity.ok().headers(responseHeaders).body("User verified");
      } else {
          return ResponseEntity.badRequest().body("The link is invalid or broken!");
      }
  }

    @PostMapping("/sign-in")
    public ResponseEntity<String>  signIn(@RequestBody UserAuthDto userAuthDto) throws JsonProcessingException {

      logger.info("add: Entered data = name = " + "; email = " + userAuthDto.getEmail() + "; password = " + userAuthDto.getPassword());

      User user = userService.getUserByEmail(userAuthDto.getEmail());
      if (user != null) {
        logger.info("checkEmail: " + user.toString());
        logger.info("Glory to Ukraine========"+userAuthDto.getPassword());
        if (BCrypt.checkpw(userAuthDto.getPassword(),user.getPassword())){

            String authToken = jwtService.createToken(user);

          if(user.getStatusId()==UserStatus.PENDING.getUserStatus())
            return ResponseEntity.badRequest().body("User is not confirm auth!");
          if(user.getStatusId()==UserStatus.BANNED.getUserStatus())
            return ResponseEntity.badRequest().body("User is banned");
          if(user.getStatusId()==UserStatus.DELETED.getUserStatus())
            return ResponseEntity.badRequest().body("User is deleted");

            user.setStatusId(UserStatus.ACTIVE.getUserStatus());
            userService.updateUser(user);
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.setBearerAuth(authToken);

            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

            return ResponseEntity.ok().headers(responseHeaders).body(ow.writeValueAsString(user));
          }
          else return ResponseEntity.badRequest().body("User password is wrong");
        }
      else
        return ResponseEntity.badRequest().body("Not found user");
    }
}
