package ru.Sidorov.ChatSimbirsoft.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import ru.Sidorov.ChatSimbirsoft.domain.Room;
import ru.Sidorov.ChatSimbirsoft.domain.User;

import java.security.Principal;
import java.util.List;
import java.util.Optional;


public interface UserService {
    void deleteById(Integer id);

    void save(User user);

    Optional<User> findById(Integer id);

    List<User> getAll();

    Optional<User> findByLogin(String login);

    Room createRoom(String name, String isPublic, Principal principal);

    User getUser(Integer id, Principal principal);

    void registration(String login, String userName, String password);

    void ban(Integer userId, Integer roomId);

    void reBan(Integer userId, Integer roomId);

    void userRename(String newName, Integer userId);

    void makeAdmin(Integer userId);

    void makeModer(Integer userId);

    void makeUser(Integer userId);
}
