package ru.AndreySidorov.ChatSimbirSoftTests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.Sidorov.ChatSimbirsoft.Application;
import ru.Sidorov.ChatSimbirsoft.domain.Role;
import ru.Sidorov.ChatSimbirsoft.domain.User;
import ru.Sidorov.ChatSimbirsoft.repo.RoleRepo;
import ru.Sidorov.ChatSimbirsoft.repo.RoomRepo;
import ru.Sidorov.ChatSimbirsoft.repo.UserRepo;
import ru.Sidorov.ChatSimbirsoft.service.RoomServices;
import ru.Sidorov.ChatSimbirsoft.service.UserService;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class RoomTest {
    @Autowired
    RoleRepo roleRepo;
    @Autowired
    UserService userService;
    @Autowired
    UserRepo userRepo;
    @Autowired
    RoomRepo roomRepo;
    @Autowired
    RoomServices roomServices;

    @Transactional
    public void createRole(String roleName){
        Role role = new Role();
        role.setUserRole(roleName);
        roleRepo.save(role);
    }

    public Principal getTestPrincipal(){
        return new Principal() {
            @Override
            public String getName() {
                return "test";
            }
        };
    }

    @Transactional
    public void registerTestUser(){
        userService.registration("test","test","test");
        userService.registration("test1","test1","test1");
        userService.registration("test2","test2","test2");
    }

    @Transactional
    public void createRoom(){
        userService.createRoom("testRoom", null, getTestPrincipal());
    }

    @Before
    @Transactional
    public void createRoles(){
        roomRepo.deleteAll();
        userRepo.deleteAll();
        roleRepo.deleteAll();
        createRole("moderator");
        createRole("user");
        createRole("admin");

    }

    @Test
    public void testUsersOutRoom()
    {
        registerTestUser();
        createRoom();
        List<User> list = roomServices.usersOutRoom(new ArrayList<User>(), roomRepo.findByName("testRoom").get().getId());
        Assert.assertEquals(2, list.size());
    }
}
