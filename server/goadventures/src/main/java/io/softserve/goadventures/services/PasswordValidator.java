package io.softserve.goadventures.services;

import org.springframework.stereotype.Service;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//TODO this is not really a service. This logic can be moved to some util class
@Service
public class PasswordValidator{
    private Pattern pattern;
    private Matcher matcher;
    private static final String PASSWORD_PATTERN =
            "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";

    public PasswordValidator(){
        pattern = Pattern.compile(PASSWORD_PATTERN);
    }

    public boolean validatePassword(final String password){
        matcher = pattern.matcher(password);
        return matcher.matches();
    }
}