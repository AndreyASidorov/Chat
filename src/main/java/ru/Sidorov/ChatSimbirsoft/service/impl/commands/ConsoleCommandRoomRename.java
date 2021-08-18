package ru.Sidorov.ChatSimbirsoft.service.impl.commands;

import ru.Sidorov.ChatSimbirsoft.domain.Room;
import ru.Sidorov.ChatSimbirsoft.repo.RoomRepo;
import ru.Sidorov.ChatSimbirsoft.repo.UserRepo;
import ru.Sidorov.ChatSimbirsoft.service.RoomServices;
import ru.Sidorov.ChatSimbirsoft.service.UserService;

import java.security.Principal;

public class ConsoleCommandRoomRename implements ConsoleCommand{

    @Override
    public void execute(String message, Principal principal, boolean isAdmin, boolean isOwner, RoomRepo roomRepo, UserRepo userRepo, RoomServices roomServices, UserService userService, Integer roomId) {
        String newName;
        int startIndex = message.indexOf("{") + 1;
        int endIndex = message.indexOf("}");
        newName = message.substring(startIndex, endIndex);
        if (!newName.equals("")) {
            if (isOwner || isAdmin)
                roomServices.roomRename(newName, roomId);
        }
    }
}
