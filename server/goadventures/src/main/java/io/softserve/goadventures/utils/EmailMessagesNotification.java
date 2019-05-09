package io.softserve.goadventures.utils;

import lombok.Data;

@Data
public class EmailMessagesNotification {
    public static String NOTIFICATIONWITHCHANGEDDATE = "Event which you are subscribed to has changed the start date!";
    public static String SIMPLENOTIFICATIONSTARTSOON = "Event which you subscribed starting soon!";
    public static String SIMPLENOTIFICATIONSTARTNOW = "Event which you subscribed starting now!";
}
