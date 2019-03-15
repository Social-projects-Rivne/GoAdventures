package io.softserve.goadventures.profile;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Profile {
    private String fullName;
    private String userName;
    private String email;
    private String location;
    private int phone;

    @Override
    public String toString() {
        return "Profile{" +
                "fullName='" + fullName + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public Profile(String fullName, String userName, String email) {
        this.fullName = fullName;
        this.userName = userName;
        this.email = email;


    }
}
