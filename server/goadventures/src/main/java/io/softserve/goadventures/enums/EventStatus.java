package io.softserve.goadventures.enums;

public enum EventStatus {
    CREATED(1),
    COMPLETED(2),
    DELETED(4);

    private int eventStatus;

    EventStatus(int status) {
        this.eventStatus = status;
    }

    public int getEventStatus() {
        return eventStatus;
    }
}
