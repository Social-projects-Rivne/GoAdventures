package io.softserve.goadventures.user.controller;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Profile {
    private String fullName;
    private String userName;
    private String email;
    private String location;
    private int phone;
    private String avatarUrl;

//    @Override
//    public String toString() {
//        return "Profile{" +
//                "fullname='" + fullname + '\'' +
//                ", username='" + username + '\'' +
//                ", email='" + email + '\'' +
//                '}';
//    }

    public Profile(String fullName, String userName, String email,String avatarUrl) {
        this.fullName = fullName;
        this.userName = userName;
        this.email = email;
        this.avatarUrl = avatarUrl;


    }
}
