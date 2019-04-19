package io.softserve.goadventures.controllers;

import io.softserve.goadventures.dto.UserAuthDto;
import io.softserve.goadventures.enums.UserStatus;
import io.softserve.goadventures.models.User;
import io.softserve.goadventures.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServlet;

@CrossOrigin
@RestController
@RequestMapping("auth")
public class AuthController extends HttpServlet {
    private Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final JWTService jwtService;
    private final UserService userService;
    private final GeneratePasswordService passwordService;
    private final MailContentBuilder mailContentBuilder;

    @Autowired
    public AuthController(JWTService jwtService, UserService userService, GeneratePasswordService passwordService,
            MailContentBuilder mailContentBuilder) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.passwordService = passwordService;
        this.mailContentBuilder = mailContentBuilder;
    }

    @GetMapping("/confirm-account")
    public ResponseEntity<User> confirmUserAccount(@RequestParam("token") String confirmationToken) {
        logger.info("[CONFIRMATION ACCOUNT]");

        User user = userService.confirmUser(jwtService.parseToken(confirmationToken));

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setBearerAuth(jwtService.createToken(user.getEmail()));

        return ResponseEntity.ok().headers(responseHeaders).body(user);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody UserAuthDto userAuthDto) throws MessagingException {
        logger.info("[SIGN-UP] - userDto : " + userAuthDto);
        HttpHeaders httpHeaders = new HttpHeaders();

        User user = userService.addUser(userAuthDto);
        String confirmationToken = jwtService.createToken(user.getEmail());

        EmailSenderService emailSenderService = new EmailSenderService(mailContentBuilder);
        emailSenderService.sendEmail(confirmationToken, user);

        return ResponseEntity.ok().headers(httpHeaders).body(user);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<String> signIn(@RequestBody UserAuthDto userAuthDto) {
        logger.info("[SIGN-IN] - userDto : ");

        UserStatus status = userService.singIn(userAuthDto.getEmail(), userAuthDto.getPassword());

        if (status.equals(UserStatus.LOGGING)) {
            String authToken = jwtService.createToken(userAuthDto.getEmail());

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(authToken);
            headers.set("token", authToken);

            return ResponseEntity.ok().headers(headers).body(String.valueOf(status));
        } else {
            return ResponseEntity.badRequest().body(String.valueOf(status));
        }
    }

    @PutMapping("/sign-out")
    public ResponseEntity<String> signOut(@RequestHeader("Authorization") String authToken) {
        logger.info("[SIGN-OUT]");
        userService.singOut(jwtService.parseToken(authToken));
        return ResponseEntity.ok("See ya");
    }

    @GetMapping("/recovery")
    public ResponseEntity<User> recoveryPassword(@RequestParam("setnewpassword") String token) {
        logger.info("[RECOVERY-PASSWORD]");
        String email = jwtService.parseRefreshToken(token);

        if (userService.checkingEmail(email)) {
            return ResponseEntity.badRequest().body(null);
        } else {
            User user = userService.getUserByEmail(email);
            user.setPassword(
                    BCrypt.hashpw(passwordService.generatePassword(email, mailContentBuilder), BCrypt.gensalt()));
            userService.updateUser(user);
            return ResponseEntity.ok().body(user);
        }
    }

    @PostMapping("/sent-recovery-email")
    public ResponseEntity<String> sentRecoveryEmail(@RequestHeader("email") String email) {
        logger.info("[SENT-RECOVERY-EMAIL]");
        if (userService.checkingEmail(email)) {
            return ResponseEntity.badRequest().body("Email " + email + " is not found.");
        } else {
            try {
                EmailSenderService emailSenderService = new EmailSenderService(mailContentBuilder);
                emailSenderService.sendRecoveryEmail(email, jwtService.refreshToken(email));
                return ResponseEntity.ok().body("emailSenderService ok");
            } catch (MessagingException e) {
                logger.error(e.toString());
                return ResponseEntity.badRequest().body("try/catch error: " + e.toString());
            }
        }
    }
}
