package ru.Sidorov.ChatSimbirsoft.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "app_user", schema = "chat")
public class User implements Comparable<User>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String login;
    @Column(name = "user_name")
    private String userName;
    private String password;
    private boolean isActive;
    @ManyToOne
    @JoinColumn(name = "user_role_id", referencedColumnName = "id")
    private Role role;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id) && login.equals(user.login) && userName.equals(user.userName) && password.equals(user.password) && role.equals(user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, userName, password, role);
    }

    @Override
    public int compareTo(User user) {
        return user.id >= this.id ? -1 : 0;
    }
}
