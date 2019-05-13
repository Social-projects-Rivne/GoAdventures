package io.softserve.goadventures.errors;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class WrongNotifyTimeException extends RuntimeException{
    public WrongNotifyTimeException(String message) {
        super(message);
    }
}
