package ru.Sidorov.ChatSimbirsoft.service.impl.commands;

import org.springframework.beans.factory.annotation.Autowired;
import ru.Sidorov.ChatSimbirsoft.domain.Room;
import ru.Sidorov.ChatSimbirsoft.repo.RoomRepo;
import ru.Sidorov.ChatSimbirsoft.repo.UserRepo;
import ru.Sidorov.ChatSimbirsoft.service.RoomServices;
import ru.Sidorov.ChatSimbirsoft.service.UserService;

import java.security.Principal;

public class ConsoleCommandRoomRemove implements ConsoleCommand{

    @Override
    public void execute(String message, Principal principal, boolean isAdmin, boolean isOwner, RoomRepo roomRepo, UserRepo userRepo, RoomServices roomServices, UserService userService, Integer roomId) {
        String roomName;
        int startIndex = message.indexOf("{") + 1;
        int endIndex = message.indexOf("}");
        roomName = message.substring(startIndex, endIndex);
        if (!roomName.equals("")) {
            if (roomRepo.findByName(roomName).orElse(null) != null) {
                Room room = roomRepo.findByName(roomName).get();
                if (room.getUserOwner().equals(userRepo.findByLogin(principal.getName()).get()) || isAdmin)
                    roomServices.removeRoom(room.getId());
            }
        }
    }
}
