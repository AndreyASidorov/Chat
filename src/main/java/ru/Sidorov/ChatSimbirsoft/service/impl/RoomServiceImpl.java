package ru.Sidorov.ChatSimbirsoft.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.Sidorov.ChatSimbirsoft.domain.*;
import ru.Sidorov.ChatSimbirsoft.repo.AddUserScheduleRepo;
import ru.Sidorov.ChatSimbirsoft.repo.MessageListRepo;
import ru.Sidorov.ChatSimbirsoft.repo.RoomRepo;
import ru.Sidorov.ChatSimbirsoft.repo.UserRepo;
import ru.Sidorov.ChatSimbirsoft.service.RoomServices;

import javax.transaction.Transactional;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomServices {
    @Autowired
    RoomRepo roomRepo;
    @Autowired
    MessageListRepo messageListRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    AddUserScheduleRepo addUserScheduleRepo;

    @Override
    public void deleteById(Integer id) {
        roomRepo.deleteById(id);
    }

    @Override
    public void save(Room room) {
        roomRepo.save(room);

    }

    @Override
    public Optional<Room> findById(Integer id) {
        return roomRepo.findById(id);
    }

    @Override
    public List<Room> getAll() {
        return roomRepo.findAll();
    }

    @Override
    public List<Room> findByIsPublic(boolean isPublic) {
        return roomRepo.findByIsPublic(isPublic);
    }

    @Override
    public List<Room> findByUserId(User userid) {
        return roomRepo.findByUserId(userid);
    }

    @Override
    public List<Room> findByUserOwner(User userOwner) {
        return roomRepo.findByUserOwner(userOwner);
    }

    @Override
    public void roomRename(String newName, Integer roomId) {
        Room room = roomRepo.findById(roomId).get();
        room.setName(newName);
        roomRepo.save(room);
    }

    @Override
    public void removeRoom(Integer roomId) {
        List<MessageList> messageLists = new ArrayList<>(messageListRepo.findByRoomOrderByMessageDate(roomRepo.findById(roomId).get()));
        for (MessageList message : messageLists) {
            messageListRepo.deleteById(message.getId());
        }
        roomRepo.deleteById(roomId);
    }

    @Override
    public void removeUserInRoom(Integer userId, Integer roomId, Integer minutes) {
        Room room = roomRepo.findById(roomId).get();
        Set<User> userSet = new HashSet<>(room.getUserSet());
        userSet.remove(userRepo.findById(userId).get());
        room.setUserSet(userSet);
        roomRepo.save(room);
        if(minutes != null){
            AddUserSchedule addUserSchedule = new AddUserSchedule();
            addUserSchedule.setUserId(userId);
            addUserSchedule.setRoomId(roomId);
            addUserSchedule.setTime(LocalDateTime.now().plusMinutes(minutes));
            addUserScheduleRepo.save(addUserSchedule);
        }
    }

    @Override
    @Transactional
    public void addUserToRoom(Integer userId, Integer roomId) {
        Room room = roomRepo.findById(roomId).get();
        Set<User> userSets = room.getUserSet();
        Set<User> userSet = new HashSet<>(userSets != null ? userSets : new HashSet<>());
        userSet.add(userRepo.findById(userId).get());
        room.setUserSet(userSet);
        roomRepo.save(room);
    }

    @Override
    public List<Room> getAllRoomsByUser(Principal principal) {
        Role role = userRepo.findByLogin(principal.getName()).get().getRole();
        Set<Room> roomsSet = new HashSet<>();
        if (role.getUserRole().equals("admin")) {
            roomsSet = new HashSet<>(roomRepo.findAll());
        } else
        {
            roomsSet = new HashSet<>(roomRepo.findByIsPublic(true));
            roomsSet.addAll(roomRepo.findByUserOwner(userRepo.findByLogin(principal.getName()).get()));
            roomsSet.addAll(roomRepo.findByUserId(userRepo.findByLogin(principal.getName()).get()));
        }
        List<Room> rooms = new ArrayList<>(roomsSet);
        rooms.sort((o1, o2) -> {
            return (o1.getId() > o2.getId()) ? 1 : -1;});
        return rooms;
    }

    @Override
    public List<MessageList> getMessagesByRoom(Integer id) {
        List<MessageList> messageLists = new ArrayList<>(messageListRepo.findByRoomOrderByMessageDate(roomRepo.findById(id).get()));
        for (MessageList messageList : messageLists) {
            if (messageList.getUser() == null) {
                User user = new User();
                user.setUserName("");
                messageList.setUser(user);
            }
        }
        return messageLists;
    }

    @Override
    public List<User> usersOutRoom(List<User> usersInRoom, Integer id) {
        List<User> otherUsers = new ArrayList<>();
        for (User user : userRepo.findAll()) {
            if (!usersInRoom.contains(user)) {
                otherUsers.add(user);
            }
        }
        otherUsers = otherUsers
                .stream()
                .filter(user -> !user
                        .getId()
                        .equals(roomRepo.findById(id).get().getUserOwner().getId()))
                .collect(Collectors.toList());
        return otherUsers;
    }

    @Override
    public List<User> usersInRoom(Integer id) {
        List<User> usersInRoom = new ArrayList<>(roomRepo.findById(id).get().getUserSet());
        Collections.sort(usersInRoom);
        return usersInRoom;
    }

    @Override
    public void removeUserInRooms(Integer userId, Integer minutes) {
        List<Room> listRooms = roomRepo.findByUserId(userRepo.findById(userId).get());
        for (Room room: listRooms) {
            Set<User> userSet = new HashSet<>(room.getUserSet());
            userSet.remove(userRepo.findById(userId).get());
            room.setUserSet(userSet);
            roomRepo.save(room);
            if(minutes != null){
                AddUserSchedule addUserSchedule = new AddUserSchedule();
                addUserSchedule.setUserId(userId);
                addUserSchedule.setRoomId(room.getId());
                addUserSchedule.setTime(LocalDateTime.now().plusMinutes(minutes));
                addUserScheduleRepo.save(addUserSchedule);
            }
        }
    }
}
