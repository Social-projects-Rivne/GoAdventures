package io.softserve.goadventures.auth.controller;

import io.softserve.goadventures.auth.service.JWTService;
import io.softserve.goadventures.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;

import io.softserve.goadventures.auth.enums.UserStatus;

import io.softserve.goadventures.auth.service.EmailSenderService;
import io.softserve.goadventures.user.model.User;
import io.softserve.goadventures.user.service.UserService;

@RestController
@RequestMapping("auth")
public class AuthController {
  private Logger logger = LoggerFactory.getLogger(io.softserve.goadventures.auth.controller.AuthController.class);


  private final JWTService jwtService;
  private final EmailSenderService emailSenderService;
  private final UserService userService;
  private final UserRepository userRepository;

  @Autowired
  public AuthController( JWTService jwtService, EmailSenderService emailSenderService, UserService userService, UserRepository userRepository) {

    this.jwtService = jwtService;
    this.emailSenderService = emailSenderService;
    this.userService = userService;
    this.userRepository = userRepository;
  }


  @PostMapping("/sign-up")
  public void signUp(@RequestBody User user) {

    if (!checkEmail(user.getEmail())) {
      user.setStatusId(UserStatus.PENDING.getUserStatus());
      userRepository.save(user);
      logger.info("signUp: New user create.");
    } else {
      logger.info("signUp: This user is already exist.");
    }

    String confirmationToken = jwtService.createToken(user);

    SimpleMailMessage mailMessage = new SimpleMailMessage();
    mailMessage.setTo(user.getEmail());
    mailMessage.setSubject("Complete Registration!");
    mailMessage.setFrom("chand312902@gmail.com");
    mailMessage.setText("To confirm your account, please click here : "
            + "http://localhost:8080/confirm-account?token=" + confirmationToken);
    emailSenderService.sendEmail(mailMessage);
  }



  @PostMapping("/sign-in")
  public ResponseEntity<String>  signIn(@RequestBody User userAuthDto) {

    logger.info("add: Entered data = name = " + "; email = " + userAuthDto.getEmail() + "; password = " + userAuthDto.getPassword());

    User user = userRepository.findByEmail(userAuthDto.getEmail());
    String authToken = jwtService.createToken(user);
    if (user != null) {
      logger.info("checkEmail: " + user.toString());
      if (user.getPassword() == userAuthDto.getPassword()){

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
        return ResponseEntity.ok().headers(responseHeaders).body("pizda");
      } else
        return ResponseEntity.badRequest().body("User password is wrong");

    }
    else
      return ResponseEntity.badRequest().body("Not found user");

  }

    /**
   * @param confirmationToken
   * @return ResponseEntity<T>  authToken *
   */
  @PutMapping("/confirm-account")
  public ResponseEntity<String> confirmUserAccount(@RequestParam("token") String confirmationToken) {
    User user = userService.getUserByEmail(jwtService.parseToken(confirmationToken));
    if (confirmationToken != null) {
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
}
