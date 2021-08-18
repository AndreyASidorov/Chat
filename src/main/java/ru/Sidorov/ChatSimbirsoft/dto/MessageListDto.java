package ru.Sidorov.ChatSimbirsoft.dto;

import ru.Sidorov.ChatSimbirsoft.domain.Room;
import ru.Sidorov.ChatSimbirsoft.domain.User;

import javax.persistence.*;
import java.time.LocalDateTime;

public class MessageListDto {
    public MessageListDto() {
    }

    public MessageListDto(Integer id, String message, String messageDate, User user, Room room) {
        this.id = id;
        this.message = message;
        this.messageDate = messageDate;
        this.user = user;
        this.room = room;
    }

    private Integer id;
    private String message;
    private String messageDate;
    private User user;
    private Room room;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(String messageDate) {
        this.messageDate = messageDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
