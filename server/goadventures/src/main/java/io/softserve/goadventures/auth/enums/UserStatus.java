package io.softserve.goadventures.auth.enums;

public enum UserStatus {
  PENDING(0),
  ACTIVE(1),
  UNACTIVE(2),
  DELETED(4),
  BANNED(5);

  private int userStatus;

  UserStatus(int status) {
   this.userStatus = status;
  }

  public int getUserStatus() {
    return userStatus;
  }
}
