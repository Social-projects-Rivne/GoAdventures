package io.softserve.goadventures.controllers;

import io.softserve.goadventures.dto.EventDTO;
import io.softserve.goadventures.dto.ScheduleEmailResponse;
import io.softserve.goadventures.errors.WrongNotifyTimeException;
import io.softserve.goadventures.models.Event;
import io.softserve.goadventures.models.EventParticipants;
import io.softserve.goadventures.models.User;
import io.softserve.goadventures.services.*;
import io.softserve.goadventures.utils.DateParser;
import io.softserve.goadventures.utils.EmailMessagesNotification;
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

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.time.*;
import java.util.HashMap;
import java.util.Set;

@RestController
public class EmailJobSchedulerController {
    private static final Logger logger = LoggerFactory.getLogger(EmailJobSchedulerController.class);

    @Autowired
    private Scheduler scheduler;

    private EventService eventService;
    private UserService userService;
    private final JWTService jwtService;
    private final EmailScheduleService emailScheduleService;
    private MailContentBuilder mailContentBuilder;

    @Autowired
    public EmailJobSchedulerController(EventService eventService, UserService userService, JWTService jwtService, EmailScheduleService emailScheduleService, MailContentBuilder mailContentBuilder) {
        this.eventService = eventService;
        this.userService = userService;
        this.jwtService = jwtService;
        this.emailScheduleService = emailScheduleService;
        this.mailContentBuilder = mailContentBuilder;
    }

    @PostMapping("/scheduleEmail")
    public ResponseEntity<ScheduleEmailResponse> scheduleEmail(@Valid @RequestHeader("Authorization") String token,
                                                               @RequestHeader("timeToAlert") String timeToAlert,
                                                               @RequestBody EventDTO eventDTO) throws SchedulerException {

        Event event = eventService.findEventByTopic(eventDTO.getTopic());
        User user = userService.getUserByEmail(jwtService.parseToken(token));

        try {
            emailScheduleService.scheduleEmail(user, event, event.getStartDate(), timeToAlert);
            return ResponseEntity.ok(new ScheduleEmailResponse(true, "scheduler done"));

        }catch (WrongNotifyTimeException ex){
            logger.error("Notify time must be after current time");
            return ResponseEntity.badRequest().body(new ScheduleEmailResponse(false,"Notify time must be after current time"));
        }
        catch (SchedulerException ex) {
            logger.error("Error scheduling email", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ScheduleEmailResponse(false, "Error scheduling email. Please try later!"));
        }

    }

    @PostMapping("/deleteSchedule")
    public ResponseEntity<ScheduleEmailResponse> deleteSchedule(@RequestHeader("Authorization") String token, @RequestBody EventDTO eventDTO) { // unsubscribe
        String email = jwtService.parseToken(token);
        try {
            boolean isDelete = emailScheduleService.deleteSchedule(email, eventDTO.getTopic());
            if (isDelete) {
                logger.info("Job deleted successfully");
                return ResponseEntity.ok(new ScheduleEmailResponse(true, "Job deleted successfully"));
            }
            logger.error("Error deleting job, key not found");
            return ResponseEntity.ok(new ScheduleEmailResponse(false, "Error deleting job, key not found"));

        } catch (SchedulerException e) {
            logger.error("Error deleting job");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ScheduleEmailResponse(false, "Error deleting job"));
        }

    }

    @PostMapping("/updateSchedule")
    public ResponseEntity<?> updateSchedule(@RequestBody EventDTO eventDTO) {      //if owner edit date of start event
        Event event = eventService.findEventByTopic(eventDTO.getTopic());
        if (eventDTO.getStartDate().equals(event.getStartDate())) {
            logger.info("date not changed");
            return null;
        }
        HashMap<String, Boolean> isSuccess = new HashMap<>();
        Set<EventParticipants> participants = event.getParticipants();
        for (EventParticipants eventParticipant : participants) {
            String email = eventParticipant.getUser().getEmail();
            boolean isDelete = false;
            //delete old trigger
            try {
                isDelete = emailScheduleService.deleteSchedule(email, event.getTopic());
                isSuccess.put(email, true);
            } catch (SchedulerException e) {
                logger.error("Error deleting job");
                isSuccess.put(email, false);
            }
            if (isDelete) {
                //send email to user with new date of start event
                try {
                    EmailSenderService emailSenderService = new EmailSenderService(mailContentBuilder);
                    emailSenderService.eventEmailNotification(
                            email, eventParticipant.getUser().getFullname(), event.getTopic(),
                            DateParser.dateToUser(DateParser.dateParser(eventDTO.getStartDate())), event.getLocation(), event.getDescription(), EmailMessagesNotification.NOTIFICATIONWITHCHANGEDDATE); // Notify that event start date is changed

                } catch (MessagingException e) {
                    logger.error("Error, cant sent email to " + email + ", " + e.toString());
                }

            }
        }
        logger.info("subst list email sent success" + isSuccess.toString());
        return ResponseEntity.ok(isSuccess);

    }


}
