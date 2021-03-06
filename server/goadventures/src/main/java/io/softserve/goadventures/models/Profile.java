package io.softserve.goadventures.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Profile {
    private String fullName;
    private String userName;
    private String email;
    private String location;
    private String phone;
    private String avatarUrl;

    public Profile(String fullName, String userName, String email,String avatarUrl) {
        this.fullName = fullName;
        this.userName = userName;
        this.email = email;
        this.avatarUrl = avatarUrl;
    }
}
