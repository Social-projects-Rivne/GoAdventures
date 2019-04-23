package io.softserve.goadventures.controllers;

import io.softserve.goadventures.dto.EventDTO;
import io.softserve.goadventures.dto.ScheduleEmailResponse;
import io.softserve.goadventures.services.EmailJob;
import io.softserve.goadventures.services.EventService;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.text.ParseException;
import java.time.*;
import java.util.Date;
import java.util.UUID;

@RestController
public class EmailJobSchedulerController {
    private static final Logger logger = LoggerFactory.getLogger(EmailJobSchedulerController.class);

    @Autowired
    private Scheduler scheduler;


    private EventService eventService;
    @Autowired
    public EmailJobSchedulerController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/scheduleEmail")
    public ResponseEntity<ScheduleEmailResponse> scheduleEmail(@Valid @RequestBody EventDTO eventDTO) throws SchedulerException {
        ZoneId defaultZoneId = ZoneId.systemDefault();

        Instant instant = Instant.parse(eventDTO.getStartDate());
        logger.info("instant " + instant.toString());

        ZonedDateTime zonedDateTime = instant.atZone(defaultZoneId); // zonedDateTimeStartEvent
        logger.info("zoned datetime " + zonedDateTime);

        try {
            if(zonedDateTime.isBefore(ZonedDateTime.now())) {
                ScheduleEmailResponse scheduleEmailResponse = new ScheduleEmailResponse(false,
                        "dateTime must be after current time");
                return ResponseEntity.badRequest().body(scheduleEmailResponse);
            }
            //add fullname, eventTopic, StartDate, location, description
            JobDetail jobDetail = buildJobDetail("vladislav.osnovyn@gmail.com");
            Trigger trigger = buildJobTrigger(jobDetail, zonedDateTime);
            scheduler.scheduleJob(jobDetail, trigger);
            logger.info("scheduler done");
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


    private JobDetail buildJobDetail(String email) {
        JobDataMap jobDataMap = new JobDataMap();

        //add fullname, eventTopic, StartDate, location, description
        jobDataMap.put("email", email);
        jobDataMap.put("subject","event starts soon");
        jobDataMap.put("body", "body22");

        return JobBuilder.newJob(EmailJob.class)
                .withIdentity(UUID.randomUUID().toString(), "email-jobs")
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
