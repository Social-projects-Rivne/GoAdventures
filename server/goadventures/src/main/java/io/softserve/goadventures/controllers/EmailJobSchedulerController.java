package io.softserve.goadventures.controllers;

import io.softserve.goadventures.dto.EventDTO;
import io.softserve.goadventures.dto.ScheduleEmailResponse;
import io.softserve.goadventures.models.Event;
import io.softserve.goadventures.models.EventParticipants;
import io.softserve.goadventures.models.User;
import io.softserve.goadventures.services.EmailJob;
import io.softserve.goadventures.services.EventService;
import io.softserve.goadventures.services.JWTService;
import io.softserve.goadventures.services.UserService;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;

@RestController
public class EmailJobSchedulerController {
    private static final Logger logger = LoggerFactory.getLogger(EmailJobSchedulerController.class);

    @Autowired
    private Scheduler scheduler;

    private EventService eventService;
    private UserService userService;
    private final JWTService jwtService;
    @Autowired
    public EmailJobSchedulerController(EventService eventService, UserService userService, JWTService jwtService) {
        this.eventService = eventService;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/scheduleEmail")
    public ResponseEntity<ScheduleEmailResponse> scheduleEmail(@Valid @RequestHeader("Authorization") String token,@RequestHeader("Role") String role, @RequestBody EventDTO eventDTO) throws SchedulerException {
        logger.info("role- " + role);
        ZoneId defaultZoneId = ZoneId.systemDefault();

        Instant instant = Instant.parse(eventDTO.getStartDate());

        ZonedDateTime zonedDateTime = instant.atZone(defaultZoneId); // zonedDateTimeStartEvent
        logger.info("zoned datetime " + zonedDateTime);

        DateFormat dateFormatToUser = new SimpleDateFormat("dd MMMM HH:mm", Locale.ENGLISH);
        Date dateToUser = Date.from(zonedDateTime.toInstant());
        String eventStartDateToUser = dateFormatToUser.format(dateToUser);// date which showed in user mail

        Event event = eventService.findEventByTopic(eventDTO.getTopic());
        User user = userService.getUserByEmail(jwtService.parseToken(token));

        try {
            if(zonedDateTime.isBefore(ZonedDateTime.now())) {
                logger.error("dateTime must be after current time");
                ScheduleEmailResponse scheduleEmailResponse = new ScheduleEmailResponse(false,
                        "dateTime must be after current time");
                return ResponseEntity.badRequest().body(scheduleEmailResponse);
            }

            JobDetail jobDetail = buildJobDetail(user.getEmail(),event.getTopic(),eventStartDateToUser,event.getLocation(),user.getFullname(),event.getDescription(),role);
            Trigger trigger = buildJobTrigger(jobDetail, zonedDateTime);
            scheduler.scheduleJob(jobDetail, trigger);
            logger.info("scheduler done, trigger will executed at "+ zonedDateTime + " Email recipient  "+ user.getEmail());
            ScheduleEmailResponse scheduleEmailResponse = new ScheduleEmailResponse(true,
                    jobDetail.getKey().getName(), jobDetail.getKey().getGroup(), "Email Scheduled Successfully!");
            return ResponseEntity.ok(scheduleEmailResponse);
        } catch (SchedulerException ex) {
            logger.error("Error scheduling email", ex);

            ScheduleEmailResponse scheduleEmailResponse = new ScheduleEmailResponse(false,
                    "Error scheduling email. Please try later!");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(scheduleEmailResponse);
        }

    }

    private JobDetail buildJobDetail(String email,String eventTopic, String startDate, String location, String fullname, String description, String role) {
        JobDataMap jobDataMap = new JobDataMap();

        jobDataMap.put("email", email);
        jobDataMap.put("eventTopic",eventTopic);
        jobDataMap.put("startDate", startDate);
        jobDataMap.put("location", location);
        jobDataMap.put("fullname", fullname);
        jobDataMap.put("description", description);
        jobDataMap.put("role", role);

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


    @PostMapping("/deleteSchedule")
    public boolean deleteSchedule(@RequestHeader("Authorization") String token, @RequestBody EventDTO eventDTO) throws SchedulerException { //unsubscribe
        String email = jwtService.parseToken(token);
        String name = "trigger" + email + eventDTO.getTopic();

        JobKey key = new JobKey(name,"email-jobs");
        logger.info("delete job with key  " + key);
        return scheduler.deleteJob(key);
    }

    @PostMapping("/updateSchedule")
    public ResponseEntity<?> updateSchedule(@RequestBody EventDTO eventDTO) throws SchedulerException {      //if owner edit date of start event
        Event event = eventService.findEventByTopic(eventDTO.getTopic());
        if(eventDTO.getStartDate().equals(event.getStartDate())){
            logger.info("date not changed");
            return null;
        }
        Set<EventParticipants> participants = event.getParticipants();
        for ( EventParticipants eventParticipant: participants) {
            String email = eventParticipant.getUser().getEmail();   //delete old trigger
            String name = "trigger" + email + event.getTopic();
            JobKey key = new JobKey(name,"email-jobs");
            logger.info("delete job with key  " + key);
            boolean isDelete = scheduler.deleteJob(key);
            logger.info("deletes successfully");
            if(isDelete){
                //update schedule
                ZoneId defaultZoneId = ZoneId.systemDefault();
                Instant instant = Instant.parse(eventDTO.getStartDate());
                logger.info("instance rescheduling " + instant);
                ZonedDateTime zonedDateTime = instant.atZone(defaultZoneId);
                logger.info("zoned datetime,rescheduling " + zonedDateTime);
                DateFormat dateFormatToUser = new SimpleDateFormat("dd MMMM HH:mm", Locale.ENGLISH);
                Date dateToUser = Date.from(zonedDateTime.toInstant());
                String eventStartDateToUser = dateFormatToUser.format(dateToUser);// date which showed in user mail
                if(zonedDateTime.isBefore(ZonedDateTime.now())) {
                    logger.error("dateTime must be after current time");
                    continue;
                }
                JobDetail jobDetail = buildJobDetail(email,event.getTopic(),eventStartDateToUser,event.getLocation(),eventParticipant.getUser().getFullname(),event.getDescription(),"subscribed");
                Trigger trigger = buildJobTrigger(jobDetail, zonedDateTime);
                scheduler.scheduleJob(jobDetail, trigger);
                logger.info("rescheduling done, trigger will executed at "+ zonedDateTime + " Email recipient  " + email );


            }
        }

        return ResponseEntity.ok("triggers changed");

    }



}
