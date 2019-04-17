package io.softserve.goadventures.enums;

public enum UserStatus {
    PENDING(0),
    ACTIVE(1),
    UNACTIVE(2),
    DELETED(4),
    BANNED(5),
    LOGGING(6),
    WRONGPASSWORD(7);

    private int userStatus;

    UserStatus(int status) {
        this.userStatus = status;
    }

    public int getUserStatus() {
        return userStatus;
    }
}
