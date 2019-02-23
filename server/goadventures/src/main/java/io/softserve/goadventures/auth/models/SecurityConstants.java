package io.softserve.goadventures.auth.models;

public class SecurityConstants {
  public static final String SECRET = "SecretKey";
  public static final long EXPIRATION_TIME = 864_000_000; // 10 days
  public  static final String TOKEN_PREFIX = "Bearer";
}
