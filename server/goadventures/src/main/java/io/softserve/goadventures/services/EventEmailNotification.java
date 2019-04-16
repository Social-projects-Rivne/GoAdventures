package io.softserve.goadventures.services;

import io.softserve.goadventures.models.Event;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;
import javax.mail.MessagingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class EventEmailNotification {

    private final Logger logger = LoggerFactory.getLogger(EventEmailNotification.class);

    @Autowired
    private MailContentBuilder mailContentBuilder;
    private EventService eventService;
    public EventEmailNotification(MailContentBuilder mailContentBuilder, EventService eventService) {
        this.mailContentBuilder = mailContentBuilder;
        this.eventService = eventService;

    }

    @Async
    @Scheduled(cron = "0 44 17 ? * * ") //(cron = "0 0 9 ? * * ") // every day at 9 am
    public void emailNotification() throws ParseException, MessagingException {
        EmailSenderService emailSenderService = new EmailSenderService(mailContentBuilder);
        List<Event> events = eventService.findAllEvents();
        SimpleDateFormat formatDateFormDb = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        DateFormat dateFormatToUser = new SimpleDateFormat("dd MMMM HH:mm", Locale.ENGLISH);
        formatDateFormDb.setTimeZone(TimeZone.getTimeZone("GMT"));
        for (Event event: events) {
            Date dateNow = new Date();
            TimeZone timeZone = TimeZone.getTimeZone("GMT");
            Calendar nowDate = Calendar.getInstance(timeZone);
            nowDate.setTime(dateNow);
            Date dateNowParsed = nowDate.getTime();  //date now

            String date = event.getStartDate();
            Date dateOfStartEvent = formatDateFormDb.parse(date); //date of start event

            int daysBetween = Days.daysBetween(new LocalDate(dateNowParsed),new LocalDate(dateOfStartEvent)).getDays();

            String startDateToUser = dateFormatToUser.format(dateOfStartEvent);
            String eventLocation = "";
            String eventDescription = "";
            if(daysBetween == 1){
                logger.info(" " + event.getDescription() + " " + event.getLocation());
                if (IsCyrylic.isCyrillic(event.getLocation())) {
                    eventLocation = IsCyrylic.toTranslit(event.getLocation());
                }
                if(IsCyrylic.isCyrillic(event.getDescription())){
                    eventDescription = IsCyrylic.toTranslit(event.getDescription());
                }
                logger.info(" " + event.getDescription() + " " + eventLocation);
                emailSenderService.eventEmailNotification(event.getOwner().getEmail(), event.getOwner().getFullname(), event.getTopic(), startDateToUser, eventLocation, eventDescription); //send email to owner event
                //send email to subscribes
                logger.info("Email sent successfully to " + event.getOwner().getEmail());

            }



        }

    }




}
