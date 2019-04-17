package io.softserve.goadventures.errors;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class WrongImageTypeException extends RuntimeException {
    public WrongImageTypeException(String message) {
        super(message);
    }

    public WrongImageTypeException(String message, Throwable cause) {
        super(message,cause);
    }
}
