package io.softserve.goadventures.enums;

public enum EventStatus {
    OPENED(1),
    CLOSED(2),
    DELETED(3);

    private int eventStatus;

    EventStatus(int status) {
        this.eventStatus = status;
    }

    public int getEventStatus() {
        return eventStatus;
    }
}
