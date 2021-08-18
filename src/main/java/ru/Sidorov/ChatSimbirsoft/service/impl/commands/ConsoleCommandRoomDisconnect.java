package ru.Sidorov.ChatSimbirsoft.service.impl.commands;

import ru.Sidorov.ChatSimbirsoft.domain.Room;
import ru.Sidorov.ChatSimbirsoft.domain.User;
import ru.Sidorov.ChatSimbirsoft.repo.RoomRepo;
import ru.Sidorov.ChatSimbirsoft.repo.UserRepo;
import ru.Sidorov.ChatSimbirsoft.service.RoomServices;
import ru.Sidorov.ChatSimbirsoft.service.UserService;

import java.security.Principal;

public class ConsoleCommandRoomDisconnect implements ConsoleCommand{
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
                                if (message.indexOf("-m") >= 0) {
                                    Integer minutes;
                                    startIndex = message.indexOf("-m {") + 4;
                                    endIndex = message.indexOf("}", startIndex);
                                    minutes = Integer.valueOf(message.substring(startIndex, endIndex));
                                    if (minutes != null) {
                                        roomServices.removeUserInRoom(user.getId(), room.getId(), minutes);
                                    }
                                } else
                                    roomServices.removeUserInRoom(user.getId(), room.getId(), null);
                            }
                        }
                    } else
                        roomServices.removeUserInRoom(userRepo.findByLogin(principal.getName()).get().getId(), room.getId(), null);
                }
            }
        }
    }
}
