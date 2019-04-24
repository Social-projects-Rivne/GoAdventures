package io.softserve.goadventures.services;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;


@Component
public class EmailJob extends QuartzJobBean {
    private static final Logger logger = LoggerFactory.getLogger(EmailJob.class);

    @Autowired
    private MailContentBuilder mailContentBuilder;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
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
            String role = jobDataMap.getString("role");

            emailSenderService.eventEmailNotification(recipientEmail,fullname,eventTopic,startDate,location,description,role);
            logger.info("Email sent successfully to " + recipientEmail);

        } catch (NoSuchProviderException e) {
            logger.error(e.toString());
        } catch (MessagingException e) {
            logger.error(e.toString());
        }


    }

}
