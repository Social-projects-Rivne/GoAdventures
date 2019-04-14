package io.softserve.goadventures.services;

import io.softserve.goadventures.models.Event;
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
    //private IsCyrylic isCyrylic;
    public EventEmailNotification(MailContentBuilder mailContentBuilder, EventService eventService) {
        this.mailContentBuilder = mailContentBuilder;
        this.eventService = eventService;

    }

    @Async
    @Scheduled(cron = "0 58 01 ? * * ") //(cron = "0 0 9 ? * * ") // every day at 9 am
    public void emailNotification() throws ParseException, MessagingException {
        EmailSenderService emailSenderService = new EmailSenderService(mailContentBuilder);
        List<Event> events = eventService.findAllEvents();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        DateFormat dateFormat = new SimpleDateFormat("dd MMMM HH:mm", Locale.ENGLISH);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        for (Event event: events) {
            Date nowUtc = new Date();
            TimeZone timeZone = TimeZone.getTimeZone("GMT");

            Calendar nowDate = Calendar.getInstance(timeZone);
            nowDate.setTime(nowUtc);
            Date dateNowNormal = nowDate.getTime();  //date now

            String date = event.getStartDate();
            Date dateOfStartEvent = sdf.parse(date); //date of start event

            Calendar cal = Calendar.getInstance();
            cal.setTime(dateOfStartEvent);
            int dayOfStartEvent = cal.get(Calendar.DAY_OF_YEAR);
            int yearStartEvent = cal.get(Calendar.YEAR);
            cal.setTime(dateNowNormal);
            int dayNow = cal.get(Calendar.DAY_OF_YEAR);
            int yearNow = cal.get(Calendar.YEAR);

            logger.info("Year now: " +yearNow + " yeat start event + " +yearStartEvent);

            String startDateToUser = dateFormat.format(dateOfStartEvent);
            String eventLocation = "";
            if(((yearNow == yearStartEvent) && (dayNow +1 == dayOfStartEvent)) || ((yearNow+1 == yearStartEvent) && ((dayNow == 365 || dayOfStartEvent == 366) && dayOfStartEvent==1))){
                logger.info(" " + event.getDescription() + " " + event.getLocation());
                if (IsCyrylic.isCyrillic(event.getLocation())) {
                    eventLocation = IsCyrylic.toTranslit(event.getLocation());
                }
                logger.info(" " + event.getDescription() + " " + eventLocation);
                emailSenderService.eventEmailNotidication(event.getOwner().getEmail(), event.getOwner().getFullname(), event.getTopic(), startDateToUser, eventLocation, event.getDescription()); //send email owner event
                logger.info("Email sent successfully to " + event.getOwner().getEmail());

            }



        }

    }




}
