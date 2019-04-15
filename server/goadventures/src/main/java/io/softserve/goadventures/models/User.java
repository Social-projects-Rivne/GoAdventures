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

    // TODO it is a bad idea to use int as phone number, because int max is 2,147,483,647 and it is less then 380977777777
    // It would be better to use string with a regex validation
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
//TODO to perform the best quality of hashCode and equals methods for objects using the fields that could be changed is prohibited.
// I believe that password could be changed. Also the name could be changed (not sure if you have such a functionality).
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
