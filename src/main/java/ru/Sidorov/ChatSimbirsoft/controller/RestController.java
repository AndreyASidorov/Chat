package ru.Sidorov.ChatSimbirsoft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.Sidorov.ChatSimbirsoft.domain.Role;
import ru.Sidorov.ChatSimbirsoft.domain.User;
import ru.Sidorov.ChatSimbirsoft.dto.UserWebsocketKey;
import ru.Sidorov.ChatSimbirsoft.service.MySecretKeys;
import ru.Sidorov.ChatSimbirsoft.repo.RoleRepo;
import ru.Sidorov.ChatSimbirsoft.repo.UserRepo;

import java.security.Principal;
import java.util.Optional;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    @Autowired
    MySecretKeys mySecretKeys;

    @GetMapping("/getUserKey")
    public UserWebsocketKey getUserKey(@Autowired Principal principal) {
        UserWebsocketKey userWebsocketKey = new UserWebsocketKey(java.util.UUID.randomUUID().toString());
        mySecretKeys.getMySecretKey().put(principal.getName(), userWebsocketKey.getKey());
        return userWebsocketKey;
    }

}
