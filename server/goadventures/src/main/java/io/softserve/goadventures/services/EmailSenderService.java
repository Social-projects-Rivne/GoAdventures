package io.softserve.goadventures.services;

import io.softserve.goadventures.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

@Service
public class EmailSenderService {
    private String username = "goadventuressup@gmail.com";
    private Properties props;
    private Transport transport;
    private Message msg;
    private MailContentBuilder contentBuilder;

    @Autowired
    public EmailSenderService(MailContentBuilder contentBuilder) throws NoSuchProviderException {
        this.contentBuilder = contentBuilder;

        this.props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "false");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.port", "587");

        Session session = Session.getInstance(props, null);
        this.transport = session.getTransport("smtp");
        this.msg = new MimeMessage(session);
        try {
            this.msg.setFrom(new InternetAddress(username, "GoAdventures support"));
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void sendEmail(String confirmationToken, User user) throws MessagingException {
        InternetAddress toAddress = new InternetAddress(user.getEmail());
        msg.setRecipient(Message.RecipientType.TO, toAddress);
        msg.setSubject("Please confirm your account");
        msg.setContent(contentBuilder.signUp(user.getFullname(), confirmationToken), "text/html");
        closeTransport();
    }

    void sendNewPassword(String email, String password) throws MessagingException {
        InternetAddress toAddress = new InternetAddress(email);
        msg.setRecipient(Message.RecipientType.TO, toAddress);
        msg.setSubject("New password for GoAdventure");
        msg.setContent(contentBuilder.sendNewPasswordMail(email, password), "text/html");
        closeTransport();
    }

    public void sendRecoveryEmail(String email, String token) throws MessagingException {
        InternetAddress toAddress = new InternetAddress(email);
        msg.setRecipient(Message.RecipientType.TO, toAddress);
        msg.setSubject("Reset link");
        msg.setContent(contentBuilder.recoveryMail(email, token), "text/html");
        closeTransport();
    }
    public void eventEmailNotification(String email,String fullname, String eventTopic, String startDate, String location, String eventDescription) throws MessagingException {
        InternetAddress toAddress = new InternetAddress(email);
        msg.setRecipient(Message.RecipientType.TO,toAddress);
        msg.setSubject("Event starts soon");
        msg.setContent(contentBuilder.eventEmailNotification(fullname,eventTopic,startDate, location, eventDescription),"text/html");
        closeTransport();
    }

    private void closeTransport() throws MessagingException {
        String password = "Adventures12_";
        transport.connect(props.get("mail.smtp.host").toString(),
                Integer.parseInt(props.get("mail.port").toString()), username, password);
        transport.sendMessage(msg,
                msg.getRecipients(Message.RecipientType.TO));
        transport.close();
    }
}
