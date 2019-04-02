package io.softserve.goadventures.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthDto {
    private String email;
    private String password;
    private String fullname;
}
