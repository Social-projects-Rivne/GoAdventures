package io.softserve.goadventures.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    private static Pattern pattern;
    private static Matcher matcher;

    public static boolean validatePassword(final String password){

//        pattern = Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})");
//        matcher = pattern.matcher(password);
//        return matcher.matches();
        return (password.length()> 4 && password.length()< 19);
    }


}