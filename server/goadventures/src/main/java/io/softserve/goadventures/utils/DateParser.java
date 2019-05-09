package io.softserve.goadventures.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Locale;

public class DateParser {
    private static ZoneId defaultZoneId = ZoneId.systemDefault();

    public static ZonedDateTime dateParser(String date) {
        Instant instant = Instant.parse(date);
        return instant.atZone(defaultZoneId); // zonedDateTimeStartEvent
    }
    public static String dateToUser(ZonedDateTime zonedDateTime){
        DateFormat dateFormatToUser = new SimpleDateFormat("dd MMMM HH:mm", Locale.ENGLISH);
        Date dateToUser = Date.from(zonedDateTime.toInstant());
        return dateFormatToUser.format(dateToUser);
    }
    public static ZonedDateTime dateOfStartEventUserChoose(ZonedDateTime zonedDateTime,String timeToAlert){
        long msStartEvent = zonedDateTime.toInstant().toEpochMilli(); //event start date in ms
        String strs[] = timeToAlert.split(":");
        int hours = Integer.parseInt(strs[0]);
        int minutes = Integer.parseInt(strs[1]);
        long resStartEvents = msStartEvent - (hours * 3600000 + minutes * 60000);

        Instant instance = java.time.Instant.ofEpochMilli(resStartEvents);
        return java.time.ZonedDateTime.ofInstant(instance,defaultZoneId);

    }

}
