package io.softserve.goadventures.auth.controller;

import io.softserve.goadventures.auth.providers.JWTProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;

import io.softserve.goadventures.auth.enums.UserStatus;

import io.softserve.goadventures.auth.services.EmailSenderService;
import io.softserve.goadventures.auth.services.VerificationService;
import io.softserve.goadventures.user.model.User;
import io.softserve.goadventures.user.service.UserService;

@Controller
@RequestMapping("auth")
public class AuthController {
  @Autowired
  private VerificationService verificationService;

  @Autowired
  private JWTProvider jwtProvider;

  @Autowired
  private EmailSenderService emailSenderService;

  @Autowired
  private UserService userService;


  @PostMapping("/sign-up")
  public void signUp(@RequestBody User newUser) {
    /*  Sign-up logic  */
    // ############################################
    String confirmationToken = "TOKEN";
    SimpleMailMessage mailMessage = new SimpleMailMessage();
    mailMessage.setTo(newUser.getEmail());
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
    User user = userService.getUserByEmail(jwtProvider.parseToken(confirmationToken));
    if (confirmationToken != null) {
      String authToken = jwtProvider.createToken(user);
      user.setStatusId(UserStatus.ACTIVE.getUserStatus());
      userService.updateUser(user);
      HttpHeaders responseHeaders = new HttpHeaders();
      responseHeaders.setBearerAuth(authToken);
      return ResponseEntity.ok().headers(responseHeaders).body("User verified");
    } else {
      return ResponseEntity.badRequest().body("The link is invalid or broken!");
    }
  }
}
