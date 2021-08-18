package ru.Sidorov.ChatSimbirsoft.service.impl.commands;

import ru.Sidorov.ChatSimbirsoft.domain.User;
import ru.Sidorov.ChatSimbirsoft.repo.RoomRepo;
import ru.Sidorov.ChatSimbirsoft.repo.UserRepo;
import ru.Sidorov.ChatSimbirsoft.service.RoomServices;
import ru.Sidorov.ChatSimbirsoft.service.UserService;

import java.security.Principal;

public class ConsoleCommandUserBan implements ConsoleCommand{
    @Override
    public void execute(String message, Principal principal, boolean isAdmin, boolean isOwner, RoomRepo roomRepo, UserRepo userRepo, RoomServices roomServices, UserService userService, Integer roomId) {
        if (isAdmin) {
            if (message.indexOf("-l") >= 0) {
                String userName;
                int startIndex = message.indexOf("-l {") + 4;
                int endIndex = message.indexOf("}", startIndex);
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
                                roomServices.removeUserInRooms(user.getId(), minutes);
                            }
                        } else
                            roomServices.removeUserInRooms(user.getId(), null);
                    }
                }
            }
        }
    }
}
