package io.softserve.goadventures.services;

import io.softserve.goadventures.controllers.EmailJobSchedulerController;
import io.softserve.goadventures.errors.WrongNotifyTimeException;
import io.softserve.goadventures.models.Event;
import io.softserve.goadventures.models.User;
import io.softserve.goadventures.utils.DateParser;
import io.softserve.goadventures.utils.EmailMessagesNotification;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.ZonedDateTime;
import java.util.Date;

@Service
public class EmailScheduleService {
    private static final Logger logger = LoggerFactory.getLogger(EmailJobSchedulerController.class);

    @Autowired
    private Scheduler scheduler;

    public void scheduleEmail(User user, Event event, String eventStartDate, String timeToAlert) throws SchedulerException {
        ZonedDateTime zonedDateTimeOfStartEvent = DateParser.dateParser(eventStartDate);// start event date
        String eventStartDateToUser = DateParser.dateToUser(zonedDateTimeOfStartEvent);// date that will be shown to the user
        String message =  null;
        ZonedDateTime zonedDateTimeStartEventUserChoose = null;
        if(timeToAlert.equals("00:00")){
            message = EmailMessagesNotification.SIMPLENOTIFICATIONSTARTNOW;
            zonedDateTimeStartEventUserChoose = zonedDateTimeOfStartEvent;
        }else{
            message = EmailMessagesNotification.SIMPLENOTIFICATIONSTARTSOON;
            zonedDateTimeStartEventUserChoose = DateParser.dateOfStartEventUserChoose(zonedDateTimeOfStartEvent, timeToAlert); // start event date, user choose
        }

        if (zonedDateTimeStartEventUserChoose.isBefore(ZonedDateTime.now())){
            throw new WrongNotifyTimeException("Notify time must be after current time");
        }
        logger.info("pararram");

        JobDetail jobDetail = buildJobDetail(user.getEmail(),event.getTopic(),eventStartDateToUser,event.getLocation(),user.getFullname(),event.getDescription(),message);
        Trigger trigger = buildJobTrigger(jobDetail, zonedDateTimeStartEventUserChoose);
        scheduler.scheduleJob(jobDetail, trigger);
        logger.info("Scheduler done, trigger will executed at " + zonedDateTimeStartEventUserChoose + ", Email recipient  " + user.getEmail());

    }
    public boolean deleteSchedule(String email, String eventTopic) throws SchedulerException {
        String name = "trigger" + email + eventTopic;
        JobKey key = new JobKey(name, "email-jobs");
        return scheduler.deleteJob(key);
    }

    private JobDetail buildJobDetail(String email, String eventTopic, String startDate, String location, String fullname, String description,String message) {
        JobDataMap jobDataMap = new JobDataMap();

        jobDataMap.put("email", email);
        jobDataMap.put("eventTopic",eventTopic);
        jobDataMap.put("startDate", startDate);
        jobDataMap.put("location", location);
        jobDataMap.put("fullname", fullname);
        jobDataMap.put("description", description);
        jobDataMap.put("message", message);

        return JobBuilder.newJob(EmailJob.class)
                .withIdentity("trigger" + email + eventTopic, "email-jobs")
                .withDescription("Send Email Job")
                .usingJobData(jobDataMap)
                .storeDurably()
                .build();
    }

    private Trigger buildJobTrigger(JobDetail jobDetail, ZonedDateTime startAt) {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(jobDetail.getKey().getName(), "email-triggers")
                .withDescription("Send Email Trigger")
                .startAt(Date.from(startAt.toInstant()))
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow())
                .build();
    }


}
