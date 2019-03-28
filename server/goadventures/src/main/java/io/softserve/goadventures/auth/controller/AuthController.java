package io.softserve.goadventures.auth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.softserve.goadventures.auth.dtoModels.UserAuthDto;
import io.softserve.goadventures.auth.enums.UserStatus;
import io.softserve.goadventures.auth.service.*;
import io.softserve.goadventures.user.model.User;
import io.softserve.goadventures.user.service.UserNotFoundException;
import io.softserve.goadventures.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServlet;

@CrossOrigin
@RestController
@RequestMapping("auth")
public class AuthController extends HttpServlet {
    private Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final JWTService jwtService;
    private final UserService userService;
    private final CheckEmailService checkEmailService;
    private final GeneratePasswordService passwordService;
    private final MailContentBuilder mailContentBuilder;

    @Autowired
    public AuthController(JWTService jwtService, UserService userService,
                          CheckEmailService checkEmailService,
                          GeneratePasswordService passwordService,
                          MailContentBuilder mailContentBuilder) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.checkEmailService = checkEmailService;
        this.passwordService = passwordService;
        this.mailContentBuilder = mailContentBuilder;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<String> signUp(@RequestBody User user) throws MessagingException {
        HttpHeaders httpHeaders = new HttpHeaders();
        logger.info(String.valueOf(user));
        if (!checkEmailService.checkingEmail(user.getEmail())) {
            user.setStatusId(UserStatus.PENDING.getUserStatus());
            user.setUsername(user.getEmail().split("@")[0]);
            user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
            user.setSelfEvents(null);
            userService.addUser(user);
            logger.info("\nsignUp: New user create.");
            logger.info("\nsignUp detail: \n\t" +
                    user.getFullname() + " : \n\t" +
                    user.getEmail() + " : \n\t" +
                    user.getUsername());

            String confirmationToken = jwtService.createToken(user);

            EmailSenderService emailSenderService = new EmailSenderService(mailContentBuilder);
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
    public ResponseEntity<String> confirmUserAccount(@RequestParam("token") String confirmationToken)
            throws UserNotFoundException {
        User user = userService.getUserByEmail(jwtService.parseToken(confirmationToken));
        if (user != null) {
            String authToken = jwtService.createToken(user);
            System.out.println("AUTH TOKEN  " + authToken);
            user.setStatusId(UserStatus.ACTIVE.getUserStatus());
            userService.updateUser(user);
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.setBearerAuth(authToken);
            return ResponseEntity.ok().headers(responseHeaders).body("User verified");
        } else {
            return ResponseEntity.badRequest().body("The link is invalid or broken!");
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
                if (user.getStatusId() == UserStatus.PENDING.getUserStatus()){
                    return ResponseEntity.badRequest().body("User is not confirm auth!");
                }
                if (user.getStatusId() == UserStatus.BANNED.getUserStatus()){
                    return ResponseEntity.badRequest().body("User is banned");
                }
                if (user.getStatusId() == UserStatus.DELETED.getUserStatus()){
                    return ResponseEntity.badRequest().body("User is deleted");
                }
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

    @GetMapping("/recovery")
    public ResponseEntity<String> recoveryPassword(@RequestParam("setnewpassword") String token) {
        LoggerFactory.getLogger("recovery").info("\n\n\ttoken is: " + token +
                "\nemail is: " + jwtService.parseRefreshToken(token) + "\n");

        String email = jwtService.parseRefreshToken(token);

        if (checkEmailService.checkingEmail(email)) {
            try {
                User user = userService.getUserByEmail(email);
                user.setPassword(BCrypt.hashpw(
                                passwordService.generatePassword(email, mailContentBuilder),
                                BCrypt.gensalt()));
                userService.updateUser(user);
                return ResponseEntity.status(200).body("Password changed.");
            } catch (UserNotFoundException e) {
                e.printStackTrace();
                return ResponseEntity.badRequest().body(e.toString());
            }
        } else {
            return ResponseEntity.badRequest().body("Email " + email + " is not found.");
        }
    }

    @PostMapping("/sent-recovery-email")
    public ResponseEntity<String> sentRecoveryEmail(@RequestHeader("email") String email) {
        String refreshToken = jwtService.refreshToken(email);

        LoggerFactory.getLogger("sent-recovery-email").info("\n\n\ttoken is: " + refreshToken +
                "\nemail is: " + email + "\n");

        if (checkEmailService.checkingEmail(email)) {
            try {
                EmailSenderService emailSenderService = new EmailSenderService(mailContentBuilder);
                emailSenderService.sendRecoveryEmail(email, refreshToken);
                return ResponseEntity.ok().body("emailSenderService ok");
            } catch (MessagingException e) {
                e.printStackTrace();
                return ResponseEntity.badRequest().body("try/catch error: " + e.toString());
            }
        } else {
            return ResponseEntity.badRequest().body("Email " + email + " is not found.");
        }
    }
}
