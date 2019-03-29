package io.softserve.goadventures.errors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Service
public class InvalidPasswordErrorMessage extends RuntimeException{
    private String ErrorMessage = "Current password is wrong!";
}
