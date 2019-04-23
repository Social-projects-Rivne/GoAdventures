package io.softserve.goadventures.services;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

@Component
public class EmailJob extends QuartzJobBean {
    private static final Logger logger = LoggerFactory.getLogger(EmailJob.class);

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MailContentBuilder mailContentBuilder;

    @Autowired
    private MailProperties mailProperties;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            EmailSenderService emailSenderService = new EmailSenderService(mailContentBuilder);
            logger.info("Executing Job with key {}", jobExecutionContext.getJobDetail().getKey());
            JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
            String subject = jobDataMap.getString("subject");
            String body = jobDataMap.getString("body");
            String recipientEmail = jobDataMap.getString("email");

            emailSenderService.eventEmailNotification(recipientEmail,"Dodik","Roflotyrick","12312312date","Selo","Description");
            logger.info("Email sent successfully to " + recipientEmail);

        } catch (NoSuchProviderException e) {
            logger.error(e.toString());
        } catch (MessagingException e) {
            logger.error(e.toString());
        }


    }

//    private void sendMail(String fromEmail, String toEmail, String subject, String body) {
//        try {
//            logger.info("Sending Email to {}", toEmail);
//            MimeMessage message = mailSender.createMimeMessage();
//
//            MimeMessageHelper messageHelper = new MimeMessageHelper(message, StandardCharsets.UTF_8.toString());
//            messageHelper.setSubject(subject);
//            messageHelper.setText(body, true);
//            messageHelper.setFrom(fromEmail);
//            messageHelper.setTo(toEmail);
//
//            mailSender.send(message);
//        } catch (MessagingException ex) {
//            logger.error("Failed to send email to {}", toEmail);
//        }
//    }

}
