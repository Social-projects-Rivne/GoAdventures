package io.softserve.goadventures.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private int phone;

    @Column(name = "role")
    private String role;

    @Column(name = "avatar")
    private String avatarUrl;

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
        return "\nUser{" +
                "\n\tid=" + id +
                ", \n\tfullname='" + fullname + '\'' +
                ", \n\tusername='" + username + '\'' +
                ", \n\tpassword='" + password + '\'' +
                ", \n\tlocation='" + location + '\'' +
                ", \n\temail='" + email + '\'' +
                ", \n\tphone=" + phone +
                ", \n\trole='" + role + '\'' +
                ", \n\tavatar='" + avatarUrl + '\'' +
                ", \n\tstatusId=" + statusId + '\'' +
                ", \n\tparticipantsEvent=" + participantsEvent + '\'' +
                "\n}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                phone == user.phone &&
                username.equals(user.username) &&
                password.equals(user.password) &&
                email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, email, phone);
    }
}
