package io.softserve.goadventures.errors;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidEmailException extends RuntimeException {
    public InvalidEmailException(String message) {
        super(message);
    }
}
