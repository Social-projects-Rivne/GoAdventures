package io.softserve.goadventures.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserUpdateDto {
    private String fullname;
    private String username;
    private String location;
    private String avatarUrl;
    private String phone;
    private String password;
    private String newPassword;
}
