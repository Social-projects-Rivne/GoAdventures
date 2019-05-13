package io.softserve.goadventures.services;

import io.softserve.goadventures.utils.EmailMessagesNotification;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import javax.mail.MessagingException;

@Component
public class EmailJob extends QuartzJobBean {
    private static final Logger logger = LoggerFactory.getLogger(EmailJob.class);

    @Autowired
    private MailContentBuilder mailContentBuilder;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {
        try {
            EmailSenderService emailSenderService = new EmailSenderService(mailContentBuilder);
            logger.info("Executing Job with key {}", jobExecutionContext.getJobDetail().getKey());
            JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
            String recipientEmail = jobDataMap.getString("email");
            String eventTopic = jobDataMap.getString("eventTopic");
            String startDate = jobDataMap.getString("startDate");
            String location = jobDataMap.getString("location");
            String fullname = jobDataMap.getString("fullname");
            String description = jobDataMap.getString("description");
            String message = jobDataMap.getString("message");

            emailSenderService.eventEmailNotification(recipientEmail,fullname,eventTopic,startDate,location,description, message);
            logger.info("Email sent successfully to " + recipientEmail);

        } catch (MessagingException e) {
            logger.error("MessagingException, " + e.toString());
        }


    }


}
