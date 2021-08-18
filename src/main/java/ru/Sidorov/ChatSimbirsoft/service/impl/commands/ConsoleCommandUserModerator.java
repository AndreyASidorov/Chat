package ru.Sidorov.ChatSimbirsoft.service.impl.commands;

import ru.Sidorov.ChatSimbirsoft.domain.User;
import ru.Sidorov.ChatSimbirsoft.repo.RoomRepo;
import ru.Sidorov.ChatSimbirsoft.repo.UserRepo;
import ru.Sidorov.ChatSimbirsoft.service.RoomServices;
import ru.Sidorov.ChatSimbirsoft.service.UserService;

import java.security.Principal;

public class ConsoleCommandUserModerator implements ConsoleCommand {
    @Override
    public void execute(String message, Principal principal, boolean isAdmin, boolean isOwner, RoomRepo roomRepo, UserRepo userRepo, RoomServices roomServices, UserService userService, Integer roomId) {
        String userName;
        int startIndex = message.indexOf("{") + 1;
        int endIndex = message.indexOf("}", startIndex);
        userName = message.substring(startIndex, endIndex);
        if (!userName.equals("")) {
            if (userRepo.findByLogin(userName).orElse(null) != null) {
                User user = userRepo.findByLogin(userName).get();
                if (isAdmin) {
                    if (message.contains("-n")) {
                        userService.makeModer(user.getId());
                    }
                    if (message.contains("-d")) {
                        userService.makeUser(user.getId());
                    }
                }
            }
        }
    }
}
