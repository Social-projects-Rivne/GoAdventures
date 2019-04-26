package io.softserve.goadventures.errors;

import lombok.NoArgsConstructor;

@NoArgsConstructor

public class InvalidPasswordErrorMessage extends RuntimeException{
    public InvalidPasswordErrorMessage(String errorMessage) {
        super(errorMessage);
    }
}
