package io.softserve.goadventures.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "full_name")
    private String fullname;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "location")
    private String location;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "role")
    private String role;

    @Column(name = "avatar")
    private String avatarUrl;

    @Column(name = "status_id")
    private int statusId;
    
    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private Set<Feedback> user_feedback = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    Set<EventParticipants> participants = new HashSet<>();

    @JsonIgnore //TODO it is better to use dto models for json. You need to separate jpa logic from serialization logic.
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
                ", avatar='" + avatarUrl + '\'' +
                ", statusId='" + statusId + '\'' +
                ", participantsEvent='" + participants + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }
}
