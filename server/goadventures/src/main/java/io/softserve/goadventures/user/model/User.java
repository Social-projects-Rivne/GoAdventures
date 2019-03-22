package io.softserve.goadventures.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.softserve.goadventures.event.model.Event;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "full_name")
    private String fullname;

    @Column(name = "userName")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "location")
    private String location;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private int phone;

    @Column(name = "role")
    private String role;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "status_id")
    private int statusId;

    @ManyToMany(mappedBy = "participants")
    private Set<Event> participantsEvent = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "owner")
    private Set<Event> selfEvents = new HashSet<>();

    public User(String fullname, String email, String password) {
        setFullname(fullname);
        setEmail(email);
        setPassword(password);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", fullname='" + fullname + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", location='" + location + '\'' +
                ", email='" + email + '\'' +
                ", phone=" + phone +
                ", role='" + role + '\'' +
                ", avatar='" + avatar + '\'' +
                ", statusId=" + statusId +
                ", participantsEvent=" + participantsEvent +
                '}';
    }
}
