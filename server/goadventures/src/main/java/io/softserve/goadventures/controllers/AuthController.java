package io.softserve.goadventures.controllers;

import io.softserve.goadventures.dto.UserAuthDto;
import io.softserve.goadventures.errors.UserNotFoundException;
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
//TODO add logging to the all controllers, both to the valid case and to the invalid/exception case
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
    public AuthController(JWTService jwtService, UserService userService,
                          GeneratePasswordService passwordService,
                          MailContentBuilder mailContentBuilder) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.passwordService = passwordService;
        this.mailContentBuilder = mailContentBuilder;
    }

    //TODO Remove or fix this javadoc
    /**
     * @param confirmationToken
     * @return ResponseEntity<User>  authToken *
     */
    @GetMapping("/confirm-account")
    public ResponseEntity<User> confirmUserAccount(@RequestParam("token") String confirmationToken) {
        User user = userService.confirmUser(jwtService.parseToken(confirmationToken));

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setBearerAuth(jwtService.createToken(user.getEmail()));

        return ResponseEntity.ok().headers(responseHeaders).body(user);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody UserAuthDto userAuthDto) throws MessagingException {
        HttpHeaders httpHeaders = new HttpHeaders();
        logger.info(String.valueOf(userAuthDto));

        User user = userService.addUser(userAuthDto);
        String confirmationToken = jwtService.createToken(user.getEmail());

        EmailSenderService emailSenderService = new EmailSenderService(mailContentBuilder);
        emailSenderService.sendEmail(confirmationToken, user);

        return ResponseEntity.ok().headers(httpHeaders).body(user);
    }

    //TODO Remove or fix this javadoc
    /**
     * @param userAuthDto
     * @return
     */
    @PostMapping("/sign-in")
    public ResponseEntity<String> signIn(@RequestBody UserAuthDto userAuthDto) {
        String status = userService.singIn(userAuthDto.getEmail(), userAuthDto.getPassword());

        if (status.equals("User log in")) { //TODO it is a very bad idea to use such strings as a status! 1: Use enum; 2: I am not sure that you rally need them. Return null if there is no User
            String authToken = jwtService.createToken(userAuthDto.getEmail());

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(authToken);
            headers.set("token", authToken);

            return ResponseEntity.ok().headers(headers).body(status);
        } else {
            return ResponseEntity.badRequest().body(status);
        }
    }

    /**
     * @param authToken
     * @return ResponseEntity<String>
     */
    @PutMapping("/sign-out")
    public ResponseEntity<String> signOut(@RequestHeader("Authorization") String authToken) {
        userService.singOut(jwtService.parseToken(authToken));
        return ResponseEntity.ok("See ya");
    }

    @GetMapping("/recovery")
    public ResponseEntity<User> recoveryPassword(@RequestParam("setnewpassword") String token) {
        String email = jwtService.parseRefreshToken(token);

        if (userService.checkingEmail(email)) {
            return ResponseEntity.badRequest().body(null);
        } else {
            try {
                User user = userService.getUserByEmail(email);
                user.setPassword(BCrypt.hashpw(
                        passwordService.generatePassword(email, mailContentBuilder),
                        BCrypt.gensalt()));
                userService.updateUser(user);
                return ResponseEntity.ok().body(user);
            } catch (UserNotFoundException e) {
                e.printStackTrace();
                return ResponseEntity.badRequest().body(null);
            }
        }
    }

    @PostMapping("/sent-recovery-email")
    public ResponseEntity<String> sentRecoveryEmail(@RequestHeader("email") String email) {
        if (userService.checkingEmail(email)) {
            return ResponseEntity.badRequest().body("Email " + email + " is not found.");
        } else {
            try {
                EmailSenderService emailSenderService = new EmailSenderService(mailContentBuilder);
                emailSenderService.sendRecoveryEmail(email, jwtService.refreshToken(email));
                return ResponseEntity.ok().body("emailSenderService ok");
            } catch (MessagingException e) {
                e.printStackTrace();
                return ResponseEntity.badRequest().body("try/catch error: " + e.toString());
            }
        }
    }
}
