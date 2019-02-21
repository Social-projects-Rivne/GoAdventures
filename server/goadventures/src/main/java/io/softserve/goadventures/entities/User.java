package io.softserve.goadventures.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = 'users')
public class User {
    @Id
    @Column(name = 'id')
    private int id;

    @Column(name = 'fullname')
    private String fullname;

    @Column(name = 'username')
    private String username;

    @Column(name = 'password')
    private String password;

    @Column(name = 'location')
    private String location;

    @Column(name = 'email')
    private String email;

    @Column(name = 'phone')
    private int phone;

    @Column(name = 'role')
    private String role;

    @Column(name = 'avatar')
    private String avatar;

    @Column(name = 'status_id')
    private int statusId;

    public User() {
    }

    public User(String fullname, String username, String password, String location, String email, int phone, String role, String avatar, int statusId) {
        this.fullname = fullname;
        this.username = username;
        this.password = password;
        this.location = location;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.avatar = avatar;
        this.statusId = statusId;
    }

    public int getId() {
        return id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }
}
