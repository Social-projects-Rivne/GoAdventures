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
import io.softserve.goadventures.auth.service.VerificationService;
import io.softserve.goadventures.user.model.User;
import io.softserve.goadventures.user.service.UserService;

@RestController
@RequestMapping("auth")
public class AuthController {
  private Logger logger = LoggerFactory.getLogger(io.softserve.goadventures.auth.controller.AuthController.class);

  private final VerificationService verificationService;
  private final JWTService jwtService;
  private final EmailSenderService emailSenderService;
  private final UserService userService;
  private final UserRepository userRepository;

  @Autowired
  public AuthController(VerificationService verificationService, JWTService jwtService, EmailSenderService emailSenderService, UserService userService, UserRepository userRepository) {
    this.verificationService = verificationService;
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
