package io.softserve.goadventures.services;

import io.softserve.goadventures.utils.EmailMessagesNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailContentBuilder {
    private TemplateEngine templateEngine;

    @Autowired
    public MailContentBuilder(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String signUp(String message, String confirmationToken) {
        Context context = new Context();
        context.setVariable("fullname", message);
        context.setVariable("confirmationToken", confirmationToken);
        return templateEngine.process("confirm-sign-up", context);
    }

    public String recoveryMail(String recoveryEmail, String recoveryToken) {
        Context context = new Context();
        context.setVariable("recoveryEmail", recoveryEmail);
        context.setVariable("recoveryToken", recoveryToken);
        return templateEngine.process("recovery-email", context);
    }

    public String sendNewPasswordMail(String sendNewPassEmail, String sendNewPassword) {
        Context context = new Context();
        context.setVariable("sendNewPassEmail", sendNewPassEmail);
        context.setVariable("sendNewPassword", sendNewPassword);
        return templateEngine.process("send-new-password", context);
    }
    public String eventEmailNotification(String fullname, String eventTopic,String startDate,String location, String eventDescription,String message){
        Context context = new Context();
        context.setVariable("fullname", fullname);
        context.setVariable("eventTopic", eventTopic);
        context.setVariable("startDate", startDate);
        context.setVariable("location", location);
        context.setVariable("description", eventDescription);
        context.setVariable("message", message);
        if(EmailMessagesNotification.NOTIFICATIONWITHCHANGEDDATE.equals(message)){
            context.setVariable("newDate", "new date: ");
        }
        return templateEngine.process("eventEmailNotification",context);
    }

}
