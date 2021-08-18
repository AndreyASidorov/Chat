package ru.Sidorov.ChatSimbirsoft.service.impl.commands;

import ru.Sidorov.ChatSimbirsoft.repo.RoomRepo;
import ru.Sidorov.ChatSimbirsoft.repo.UserRepo;
import ru.Sidorov.ChatSimbirsoft.service.RoomServices;
import ru.Sidorov.ChatSimbirsoft.service.UserService;

import java.security.Principal;

public class ConsoleCommandRoomCreate implements ConsoleCommand{
    @Override
    public void execute(String message, Principal principal, boolean isAdmin, boolean isOwner, RoomRepo roomRepo, UserRepo userRepo, RoomServices roomServices, UserService userService, Integer roomId) {
        String roomName;
        int startIndex = message.indexOf("{") + 1;
        int endIndex = message.indexOf("}");
        roomName = message.substring(startIndex, endIndex);
        if (!roomName.equals("")) {
            if (message.contains("-c")) {
                userService.createRoom(roomName, "true", principal);
            } else
                userService.createRoom(roomName, null, principal);
        }
    }
}
