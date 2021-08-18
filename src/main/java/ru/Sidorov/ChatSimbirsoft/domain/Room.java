package ru.Sidorov.ChatSimbirsoft.domain;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "room", schema = "chat")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "user_owner", referencedColumnName = "id")
    private User userOwner;
    private boolean isPublic;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(schema = "chat",
            name = "room_users",
            joinColumns = {@JoinColumn(name = "room_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}

    )
    Set<User> userSet;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(schema = "chat",
            name = "room_banned_users",
            joinColumns = {@JoinColumn(name = "room_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}

    )
    Set<User> userBannedSet;

    public Set<User> getUserSet() {
        return userSet;
    }

    public void setUserSet(Set<User> userSet) {
        this.userSet = userSet;
    }

    public Set<User> getUserBannedSet() {
        return userBannedSet;
    }

    public void setUserBannedSet(Set<User> userBannedSet) {
        this.userBannedSet = userBannedSet;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUserOwner() {
        return userOwner;
    }

    public void setUserOwner(User userOwner) {
        this.userOwner = userOwner;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return isPublic == room.isPublic && Objects.equals(id, room.id) && Objects.equals(name, room.name) && Objects.equals(userOwner, room.userOwner) && Objects.equals(userSet, room.userSet) && Objects.equals(userBannedSet, room.userBannedSet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, userOwner, isPublic, userSet, userBannedSet);
    }
}
