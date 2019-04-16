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
    private int phone; //TODO int -> String. see comment to User model
    private String avatarUrl;

    public Profile(String fullName, String userName, String email,String avatarUrl) {
        this.fullName = fullName;
        this.userName = userName;
        this.email = email;
        this.avatarUrl = avatarUrl;
    }
}
