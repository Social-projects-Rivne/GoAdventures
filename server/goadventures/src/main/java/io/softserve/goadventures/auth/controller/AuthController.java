package io.softserve.goadventures.auth.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.softserve.goadventures.auth.enums.UserStatus;
import io.softserve.goadventures.auth.service.CheckEmailService;
import io.softserve.goadventures.auth.service.EmailSenderService;
import io.softserve.goadventures.auth.service.JWTService;
import io.softserve.goadventures.user.model.User;
import io.softserve.goadventures.user.service.UserNotFoundException;
import io.softserve.goadventures.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import io.softserve.goadventures.auth.dtoModels.UserAuthDto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

//@CrossOrigin(origins = "http://domain2.com", maxAge = 3600)
@CrossOrigin
@RestController
@RequestMapping("auth")
public class AuthController extends HttpServlet {
  private Logger logger = LoggerFactory.getLogger(io.softserve.goadventures.auth.controller.AuthController.class);
  private final JWTService jwtService;
  private final UserService userService;
  private final EmailSenderService emailSenderService;

  @Autowired
  public AuthController(JWTService jwtService, UserService userService, EmailSenderService emailSenderService) {
    this.emailSenderService = emailSenderService;
    this.jwtService = jwtService;
    this.userService = userService;
  }

  @PostMapping("/sign-up")
  public ResponseEntity<String> signUp(@RequestBody User user) throws MessagingException, UnsupportedEncodingException {
    HttpHeaders httpHeaders = new HttpHeaders();
    logger.info(String.valueOf(user));
    if (!checkEmail(user.getEmail())) {

      user.setStatusId(UserStatus.PENDING.getUserStatus());
      user.setUsername(user.getEmail().split("@")[0]);
      userService.addUser(user);
      logger.info("\nsignUp: New user create.");
      logger.info("\nsignUp detail: \n\t" +
              user.getFullname() + " : \n\t" +
              user.getEmail() + " : \n\t" +
              user.getUsername());

        String confirmationToken = jwtService.createToken(user);

      emailSenderService.sendEmail(confirmationToken, user);
      return ResponseEntity.ok().headers(httpHeaders).body("user created");

    } else {
      logger.info("\nsignUp: This user is already exist.");

      return ResponseEntity.badRequest().body("user already exist");
    }
  }


    /**
   * @param confirmationToken
   * @return ResponseEntity<T>  authToken *
   */
  @GetMapping("/confirm-account")
  public ResponseEntity<String> confirmUserAccount(@RequestParam("token") String confirmationToken) throws UserNotFoundException {
    User user = userService.getUserByEmail(jwtService.parseToken(confirmationToken));
    if (user != null) {
      String authToken = jwtService.createToken(user);
      System.out.println("AUTH TOKEN  " + authToken);
      user.setStatusId(UserStatus.ACTIVE.getUserStatus());
      userService.updateUser(user);
      HttpHeaders responseHeaders = new HttpHeaders();
      responseHeaders.setBearerAuth(authToken);
      //HttpSession session = req.getSession();
      //session.setAttribute("user", user);
      return ResponseEntity.ok().headers(responseHeaders).body("User verified"); //TODO page User Verified
    } else {
      return ResponseEntity.badRequest().body("The link is invalid or broken!"); // TODO page the link is invalid
    }
  }



  private boolean checkEmail(String email) {
    try {
      return userService.getUserByEmail(email) != null;
    } catch (UserNotFoundException error) {
      logger.info("checkEmail: can't find this user " + error);
      return false;
//    if (user != null) {
//      logger.info("checkEmail: " + user.toString());
//      return true;
//    } else {
//      logger.info("checkEmail: can't find this user");
//      return false;
//    }
    }
  }

  /**
   * @param userAuthDto
   * @return
   * @throws JsonProcessingException
   * @throws UserNotFoundException
   */
  @PostMapping("/sign-in")
  public ResponseEntity<String> signIn(@RequestBody UserAuthDto userAuthDto)
          throws JsonProcessingException, UserNotFoundException {

    logger.info("add: Entered data = name = " +
            "; email = " + userAuthDto.getEmail() + "; password = " + userAuthDto.getPassword());

    User user = userService.getUserByEmail(userAuthDto.getEmail());

    if (user != null) {
      String authToken = jwtService.createToken(user);
      logger.info("checkEmail: " + user.toString());
      logger.info("Glory to Ukraine========" + userAuthDto.getPassword());
      if (BCrypt.checkpw(userAuthDto.getPassword(), user.getPassword())) {


        if (user.getStatusId() == UserStatus.PENDING.getUserStatus())
          return ResponseEntity.badRequest().body("User is not confirm auth!");
        if (user.getStatusId() == UserStatus.BANNED.getUserStatus())
          return ResponseEntity.badRequest().body("User is banned");
        if (user.getStatusId() == UserStatus.DELETED.getUserStatus())
          return ResponseEntity.badRequest().body("User is deleted");

        user.setStatusId(UserStatus.ACTIVE.getUserStatus());
        userService.updateUser(user);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setBearerAuth(authToken);
        responseHeaders.set("token", authToken);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

        return ResponseEntity.ok().headers(responseHeaders).body(ow.writeValueAsString(user));
      } else return ResponseEntity.badRequest().body("User password is wrong");

    } else
      return ResponseEntity.badRequest().body("Not found user");

  }

  /**
   * @param authToken
   * @return ResponseEntity<String>
   * @throws ResponseStatusException
   */
  @PutMapping("/sign-out")
  public ResponseEntity<String> signOut(@RequestHeader("Authorization") String authToken) throws ResponseStatusException {
    try {
      User user = userService.getUserByEmail(jwtService.parseToken(authToken));
      user.setStatusId(2);
      userService.updateUser(user);
      return ResponseEntity.status(200).body("See ya");
    } catch (UserNotFoundException error) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad token provided");
    }
  }

}
