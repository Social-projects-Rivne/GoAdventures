package io.softserve.goadventures.errors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor

public class InvalidPasswordErrorMessage extends RuntimeException{
    //private String ErrorMessage = "Current password is wrong!";
    public InvalidPasswordErrorMessage(String errorMessage) {
        super(errorMessage);
    }
}
