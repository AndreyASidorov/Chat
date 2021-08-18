package ru.Sidorov.ChatSimbirsoft.service.impl.commands;

import ru.Sidorov.ChatSimbirsoft.domain.Room;
import ru.Sidorov.ChatSimbirsoft.domain.User;
import ru.Sidorov.ChatSimbirsoft.repo.RoomRepo;
import ru.Sidorov.ChatSimbirsoft.repo.UserRepo;
import ru.Sidorov.ChatSimbirsoft.service.RoomServices;
import ru.Sidorov.ChatSimbirsoft.service.UserService;

import java.security.Principal;

public class ConsoleCommandRoomConnect implements ConsoleCommand {
    @Override
    public void execute(String message, Principal principal, boolean isAdmin, boolean isOwner, RoomRepo roomRepo, UserRepo userRepo, RoomServices roomServices, UserService userService, Integer roomId) {
        String roomName;
        int startIndex = message.indexOf("{") + 1;
        int endIndex = message.indexOf("}");
        roomName = message.substring(startIndex, endIndex);
        if (!roomName.equals("")) {
            if (roomRepo.findByName(roomName).orElse(null) != null) {
                Room room = roomRepo.findByName(roomName).get();
                if (isAdmin || room.getUserOwner().equals(userRepo.findByLogin(principal.getName()).get())) {
                    if (message.indexOf("-l") >= 0) {
                        String userName;
                        startIndex = message.indexOf("-l {") + 4;
                        endIndex = message.indexOf("}", startIndex);
                        userName = message.substring(startIndex, endIndex);
                        if (userName != "") {
                            if (userRepo.findByLogin(userName).orElse(null) != null) {
                                User user = userRepo.findByLogin(userName).get();
                                roomServices.addUserToRoom(user.getId(), room.getId());
                            }
                        }
                    } else
                        roomServices.addUserToRoom(userRepo.findByLogin(principal.getName()).get().getId(), room.getId());
                }
            }
        }
    }
}
