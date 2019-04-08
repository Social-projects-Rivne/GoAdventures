package io.softserve.goadventures.eventNotification;

import io.softserve.goadventures.auth.service.EmailSenderService;
import io.softserve.goadventures.auth.service.MailContentBuilder;
import io.softserve.goadventures.event.model.Event;
import io.softserve.goadventures.event.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;
import javax.mail.MessagingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@RestController
public class EmailNotification{

    private final Logger logger = LoggerFactory.getLogger(EmailNotification.class);

    @Autowired
    private MailContentBuilder mailContentBuilder;
    private EventService eventService;


    public EmailNotification(MailContentBuilder mailContentBuilder, EventService eventService) {
        this.mailContentBuilder = mailContentBuilder;
        this.eventService = eventService;

    }

    //@Scheduled(cron = "0 0 9 ? * * ") // every day at 9 am

    @Async
    @Scheduled(cron = "0 25 21 ? * * ") // every day at 9 am
    public void emailNotification() throws ParseException, MessagingException {
        EmailSenderService emailSenderService = new EmailSenderService(mailContentBuilder);
        List<Event> events = eventService.findAllEvents();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        for (Event event: events) {
            Date nowUtc = new Date();
            TimeZone timeZone = TimeZone.getTimeZone("GMT");

            Calendar nowDate = Calendar.getInstance(timeZone);
            nowDate.setTime(nowUtc);
            Date dateNowNormal = nowDate.getTime();

            String date = event.getStartDate();
            Date dateOfStartEvent = sdf.parse(date);

            Calendar cal = Calendar.getInstance();
            cal.setTime(dateOfStartEvent);
            int dayOfStartEvent = cal.get(Calendar.DAY_OF_YEAR);
            cal.setTime(dateNowNormal);
            int dayNow = cal.get(Calendar.DAY_OF_YEAR);

            if((dayNow+1) == dayOfStartEvent){
                 emailSenderService.eventEmailNotidication(event.getOwner().getEmail(),event.getOwner().getFullname(),event.getTopic());
                 logger.info("Email sent successfully to "+ event.getOwner().getEmail());

            }

        }

    }




}
