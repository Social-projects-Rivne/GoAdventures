package io.softserve.goadventures.user.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "User Not Found")
public class UserNotFoundException extends Exception {
    private static final long serialVersionUID = 200L;

    public UserNotFoundException(String message){
        super(message);
    }
}
