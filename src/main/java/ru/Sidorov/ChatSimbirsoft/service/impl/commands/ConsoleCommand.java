package ru.Sidorov.ChatSimbirsoft.service.impl.commands;

import ru.Sidorov.ChatSimbirsoft.repo.RoomRepo;
import ru.Sidorov.ChatSimbirsoft.repo.UserRepo;
import ru.Sidorov.ChatSimbirsoft.service.RoomServices;
import ru.Sidorov.ChatSimbirsoft.service.UserService;

import java.security.Principal;

public interface ConsoleCommand {
    void execute(String message, Principal principal, boolean isAdmin, boolean isOwner, RoomRepo roomRepo, UserRepo userRepo, RoomServices roomServices, UserService userService, Integer roomId);
}
