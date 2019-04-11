package io.softserve.goadventures.errors;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FileSizeException extends RuntimeException{
    public FileSizeException(String message) {
        super(message);
    }
}
