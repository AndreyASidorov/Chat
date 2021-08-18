package ru.Sidorov.ChatSimbirsoft.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import ru.Sidorov.ChatSimbirsoft.domain.MessageList;
import ru.Sidorov.ChatSimbirsoft.domain.Room;
import ru.Sidorov.ChatSimbirsoft.domain.User;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RoomServices {
    void deleteById(Integer id);

    void save(Room room);

    Optional<Room> findById(Integer id);

    List<Room> getAll();

    List<Room> findByIsPublic(boolean isPublic);

    List<Room> findByUserId(@Param("userid") User userid);

    List<Room> findByUserOwner(User userOwner);

    void roomRename(String newName, Integer roomId);

    void removeRoom(Integer roomId);

    void removeUserInRoom(Integer userId, Integer roomId, Integer minutes);

    void addUserToRoom(Integer userId, Integer roomId);

    List<Room> getAllRoomsByUser (Principal principal);

    List<MessageList> getMessagesByRoom (Integer id);

    List<User> usersOutRoom (List<User> usersInRoom, Integer id);

    List<User> usersInRoom (Integer id);

    void removeUserInRooms(Integer userId, Integer minutes);
}
