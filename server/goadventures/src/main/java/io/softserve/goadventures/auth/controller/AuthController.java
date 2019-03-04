package io.softserve.goadventures.auth.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.softserve.goadventures.auth.dto.UserDto;
import io.softserve.goadventures.auth.enums.UserStatus;
import io.softserve.goadventures.auth.service.JWTService;
import io.softserve.goadventures.user.model.User;
import io.softserve.goadventures.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import io.softserve.goadventures.auth.dtoModels.UserAuthDto;

import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @Autowired
    public AuthController(JWTService jwtService, UserService userService) {
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

    private void emailConfirmation(User user) throws MessagingException, UnsupportedEncodingException {
        String confirmationToken = jwtService.createToken(user);
        String username ="goadventuressup@gmail.com";
        String password = "Adventures12_";

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "false");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.port", "587");
        //props.put("mail.smtp.connectiontimeout", "5000");
        //props.put("mail.smtp.timeout", "5000");

        Session session = Session.getInstance(props, null);

        Transport transport = session.getTransport("smtp");
        Message msg = new MimeMessage(session);

        InternetAddress from = new InternetAddress("goadventuressup@gmail.com ", "GoAdventures support");
        msg.setFrom(from);
        InternetAddress toAddress = new InternetAddress(user.getEmail());
        msg.setRecipient(Message.RecipientType.TO, toAddress);
        msg.setSubject("Please confirm your account");

        MimeMultipart multipart = new MimeMultipart("related");

        BodyPart messageBodyPart = new MimeBodyPart();
        String htmlText =
                "<head>" +
                "<style type=\"text/css\">" +
                ".red{color:#FF0000;} " +
                ".pink{color:#8B008B;}" +
                ".button {" +
                "  display: inline-block;" +
                "  padding: 15px 25px;" +
                "  font-size: 24px;" +
                "  cursor: pointer;" +
                "  text-align: center;" +
                "  text-decoration: none;" +
                "  outline: none;" +
                "  color: #fff;" +
                "  background-color: #4CAF50;" +
                "  border: none;" +
                "  border-radius: 15px;" +
                "  box-shadow: 0 9px #999;" +
                "}" +
                ".button:hover {background-color: #3e8e41}" +
                ".button:active {" +
                "  background-color: #3e8e41;" +
                "  box-shadow: 0 5px #666;" +
                "  transform: translateY(4px);" +
                "}" +
                "</style>" +
                "</head>" +
                //"<body><img src='cid:confirmImage'>" +
                "<hr align=\"center\" width=\"90%\" size=\"1\"/>" +
                //"<hr align = \"left\" width=\"1\" size=\"500\">" +
                "<h1 class=red align=\"center\">" + "Welcome to GoAdventures" + "</h1>" +
                "<h4 class=pink align=\"center\">" + "We need to make sure you're you! " + "</h4>" +
                "<br></br>" +
                "<hr align=\"center\" width=\"90%\" size=\"1\"/>" +
                "<br></br>" + "<br></br>" + "<br></br>" +"<br></br>" + "<br></br>" +
                "<h3 class=pink align=\"center\">" + "Hi " + user.getFullname() + "," + "</h3>" +
                "<h3 class=pink align=\"center\">" + "Thanks for signing up for GoAdventures!" + "</h3>" +
                "<h3 class=pink align=\"center\">" + "A Quick Click to Confirm" + "</h3>" +
                "<h3 class=pink align=\"center\">" + "Your Account" + "</h3>" +
                "<p align=\"center\">" +
                "<a href=http://localhost:3001/auth/confirm-account?token=" + confirmationToken + ">" + "<button class=button>Confirm Account</button></a></p>" +
                "<br></br>" + "<br></br>" + "<br></br>" + "<br></br>" + "<br></br>" + "<br></br>" + "<br></br>" +
                "<hr align=\"center\" width=\"90%\" size=\"1\"/>" +
                "<br></br>" + "<br></br>" +
                "<h4 class=pink align = \"center\">" + "All Rights Reserved" + "</h4>";

        messageBodyPart.setContent(htmlText, "text/html");

        multipart.addBodyPart(messageBodyPart);

        //messageBodyPart = new MimeBodyPart();
        //DataSource fds = new FileDataSource(
          //      "/home/luxobscr/GoAdventures/client/src/assets/images/Stripe-email.png");

        //messageBodyPart.setDataHandler(new DataHandler(fds));
        //messageBodyPart.setHeader("Content-ID", "<confirmImage>");

        //multipart.addBodyPart(messageBodyPart);
        msg.setContent(multipart);

        transport.connect(props.get("mail.smtp.host").toString(),
                Integer.parseInt(props.get("mail.port").toString()), username, password);
        transport.sendMessage(msg,
                msg.getRecipients(Message.RecipientType.TO));
        transport.close();
        //Transport.send(msg);
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

    @PostMapping("/sign-in")
    public ResponseEntity<String>  signIn(@RequestBody UserAuthDto userAuthDto) throws JsonProcessingException {

      logger.info("add: Entered data = name = " + "; email = " + userAuthDto.getEmail() + "; password = " + userAuthDto.getPassword());

      User user = userService.getUserByEmail(userAuthDto.getEmail());
      String authToken = jwtService.createToken(user);
      if (user != null) {
        logger.info("checkEmail: " + user.toString());
        logger.info("Glory to Ukraine========"+userAuthDto.getPassword());
        if (BCrypt.checkpw(userAuthDto.getPassword(),user.getPassword())){


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
