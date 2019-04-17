package io.softserve.goadventures.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    private static Pattern pattern;
    private static Matcher matcher;

    public static boolean validatePassword(final String password){
        pattern = Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})");
        matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static boolean validateEmail(final String hex) {
        pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        matcher = pattern.matcher(hex);
        return matcher.matches();
    }
}