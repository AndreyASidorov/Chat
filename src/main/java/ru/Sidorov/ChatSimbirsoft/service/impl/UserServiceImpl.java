package ru.Sidorov.ChatSimbirsoft.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.Sidorov.ChatSimbirsoft.domain.Room;
import ru.Sidorov.ChatSimbirsoft.domain.User;
import ru.Sidorov.ChatSimbirsoft.repo.RoleRepo;
import ru.Sidorov.ChatSimbirsoft.repo.RoomRepo;
import ru.Sidorov.ChatSimbirsoft.repo.UserRepo;
import ru.Sidorov.ChatSimbirsoft.service.UserService;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    RoomRepo roomRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    RoleRepo roleRepo;

    @Override
    public void deleteById(Integer id) {
        userRepo.deleteById(id);
    }

    @Override
    public void save(User user) {
        userRepo.save(user);
    }

    @Override
    public Optional<User> findById(Integer id) {
        return userRepo.findById(id);
    }

    @Override
    public List<User> getAll() {
        return userRepo.findAll();
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return userRepo.findByLogin(login);
    }

    @Override
    public Room createRoom(String name, String isPublic, Principal principal) {
        Room room = new Room();
        room.setUserOwner(userRepo.findByLogin(principal.getName()).get());
        room.setName(name != "" ? name : "default");
        room.setPublic(isPublic != null);
        Set<User> userSet = new HashSet<>();
        //userSet.add(room.getUserOwner());
        if (isPublic != null) {
            for (User user : userRepo.findAll()) {
                userSet.add(user);
            }
        }
        room.setUserSet(userSet);
        roomRepo.save(room);
        return room;
    }

    @Override
    public User getUser(Integer id, Principal principal) {
        User user;
        if (id != null) {
            user = userRepo.findById(id).get();
        } else user = userRepo.findByLogin(principal.getName()).get();
        return user;
    }

    @Override
    public void registration(String login, String userName, String password) {
        User user = new User();
        user.setLogin(login);
        user.setUserName(userName);
        user.setPassword(password);
        user.setActive(true);
        user.setRole(roleRepo.findByUserRole("user").orElse(null));
        userRepo.save(user);
    }

    @Override
    public void ban(Integer userId, Integer roomId) {
        Set<User> set = roomRepo.findById(roomId).get().getUserBannedSet();
        Room room = roomRepo.findById(roomId).get();
        set.add(userRepo.findById(userId).get());
        room.setUserBannedSet(set);
        roomRepo.save(room);
    }

    @Override
    public void reBan(Integer userId, Integer roomId) {
        Set<User> set = roomRepo.findById(roomId).get().getUserBannedSet();
        Room room = roomRepo.findById(roomId).get();
        set.remove(userRepo.findById(userId).get());
        room.setUserBannedSet(set);
        roomRepo.save(room);
    }

    @Override
    public void userRename(String newName, Integer userId) {
        User user = userRepo.findById(userId).get();
        user.setUserName(newName);
        userRepo.save(user);
    }

    @Override
    public void makeAdmin(Integer userId) {
        User user = userRepo.findById(userId).get();
        user.setRole(roleRepo.findById(1).get());
        userRepo.save(user);
    }

    @Override
    public void makeModer(Integer userId) {
        User user = userRepo.findById(userId).get();
        user.setRole(roleRepo.findById(2).get());
        userRepo.save(user);
    }

    @Override
    public void makeUser(Integer userId) {
        User user = userRepo.findById(userId).get();
        user.setRole(roleRepo.findById(3).get());
        userRepo.save(user);
    }
}
