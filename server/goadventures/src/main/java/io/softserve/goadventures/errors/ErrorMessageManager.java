package io.softserve.goadventures.errors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessageManager {
    private String publicError;
    private  String innerError;
}
