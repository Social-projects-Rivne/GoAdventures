package io.softserve.goadventures.auth.controller;

import io.softserve.goadventures.auth.enums.UserStatus;
import io.softserve.goadventures.auth.service.EmailSenderService;
import io.softserve.goadventures.auth.service.JWTService;
import io.softserve.goadventures.user.model.User;
import io.softserve.goadventures.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

//@CrossOrigin(origins = "http://domain2.com", maxAge = 3600)
@CrossOrigin
@RestController
@RequestMapping("auth")
public class AuthController extends HttpServlet {
    private Logger logger = LoggerFactory.getLogger(io.softserve.goadventures.auth.controller.AuthController.class);

    private final JWTService jwtService;

    private final EmailSenderService emailSenderService;

    private final UserService userService;

    @Autowired
    public AuthController(JWTService jwtService, EmailSenderService emailSenderService, UserService userService) {
        this.jwtService = jwtService;
        this.emailSenderService = emailSenderService;
        this.userService = userService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<String> signUp(@RequestBody User user) {
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

            emailConfirmation(user);
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
  public ResponseEntity<String> confirmUserAccount(@RequestParam("token") String confirmationToken, HttpServletRequest req) {
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

    private void emailConfirmation(User user) {
        String confirmationToken = jwtService.createToken(user);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setFrom("GoAdventures@gmail.com");
        mailMessage.setText("To confirm your account, please click here : "
                + "http://localhost:3001/auth/confirm-account?token=" + confirmationToken);
        emailSenderService.sendEmail(mailMessage);
    }

    private boolean checkEmail(String email) {
        User user = userService.getUserByEmail(email);
        if (user != null) {
            logger.info("checkEmail: " + user.toString());
            return true;
        } else {
            logger.info("checkEmail: can't find this user");
            return false;
        }
    }
}
