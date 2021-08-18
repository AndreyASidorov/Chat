package ru.Sidorov.ChatSimbirsoft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.Sidorov.ChatSimbirsoft.domain.Role;
import ru.Sidorov.ChatSimbirsoft.domain.User;
import ru.Sidorov.ChatSimbirsoft.repo.RoleRepo;
import ru.Sidorov.ChatSimbirsoft.repo.UserRepo;

import java.util.Optional;
@org.springframework.web.bind.annotation.RestController
public class RestControllerTest {
    @Autowired
    RoleRepo roleRepo;
    @Autowired
    UserRepo userRepo;

    @PostMapping("/test")
    public User test(
            @RequestParam String name, @RequestParam String login, @RequestParam String password
    ) {
        User user = new User();
        user.setUserName(name);
        user.setLogin(login);
        user.setPassword(password);
        return userRepo.save(user);
    }

    ;

    @GetMapping("/testGet")
    public Optional<User> testGet(@RequestParam Integer id) {
        return userRepo.findById(id);

    }

    @GetMapping("/testFindRole")
    public Optional<Role> testFindRole(@RequestParam String userRole) {
        return roleRepo.findByUserRole(userRole);
    }

    @PostMapping("/makeAdmintest")
    public void makeAdmin(
            @RequestParam Integer id
    ) {
        Optional<User> user = userRepo.findById(id);
        if (user.isPresent()) {
            user.get().setRole(roleRepo.findById(1).get());
            userRepo.save(user.get());
        }


    }
}
