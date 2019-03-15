package io.softserve.goadventures.auth.dtoModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthDto {
    private String email;
    private  String password;
    private String username;
    private String fullname;

}
