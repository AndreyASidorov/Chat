package ru.Sidorov.ChatSimbirsoft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.Sidorov.ChatSimbirsoft.domain.Room;
import ru.Sidorov.ChatSimbirsoft.domain.User;
import ru.Sidorov.ChatSimbirsoft.service.RoleService;
import ru.Sidorov.ChatSimbirsoft.service.RoomServices;
import ru.Sidorov.ChatSimbirsoft.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.*;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    RoomServices roomServices;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(@Autowired HttpServletRequest request, @Autowired HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "login";
    }
    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String Registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(
            @RequestParam(name = "login") String login,
            @RequestParam(name = "userName") String userName,
            @RequestParam(name = "password") String password,
            @Autowired Model model) {
        userService.registration(login, userName, password);
        return "login";
    }

    @PostMapping("/createRoom")
    @PreAuthorize("hasAuthority('admin') or hasAuthority('user') or hasAuthority('moderator')")
    public String createRoom(
            @RequestParam(name = "roomName") String name,
            @RequestParam(name = "public", required = false) String isPublic,
            @Autowired Principal principal) {
        Room room = userService.createRoom(name, isPublic, principal);
        return "redirect:/room?id=" + room.getId();
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String main(
            @RequestParam(name = "name", required = false) String name,
            Model model) {
        List<User> users = new ArrayList<>(userService.getAll());
        model.addAttribute("userAll", users);
        return "users";
    }

    //@PreAuthorize("hasAuthority('admin') or hasAuthority('user') or hasAuthority('moderator')")
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String user(@Autowired Model model, @Autowired Principal principal, @RequestParam(value = "id", required = false) Integer id) {
        User user = userService.getUser(id, principal);
        model.addAttribute("user", user);
        model.addAttribute("principal", userService.findByLogin(principal.getName()).get());
        return "user";
    }
    @PostMapping("/ban")
    public String ban(
            @RequestParam(name = "userId") Integer userId,
            @RequestParam(name = "roomId") Integer roomId,
            @Autowired Principal principal) {
        userService.ban(userId, roomId);
        return "redirect:/room?id=" + roomId;
    }
    @PostMapping("/reBan")
    public String reBan(
            @RequestParam(name = "userId") Integer userId,
            @RequestParam(name = "roomId") Integer roomId,
            @Autowired Principal principal) {
        userService.reBan(userId, roomId);
        return "redirect:/room?id=" + roomId;
    }
    @PostMapping("/userRename")
    //@PreAuthorize("hasAuthority('admin') or hasAuthority('user') or hasAuthority('moderator')")
    public String userRename(
            @RequestParam(name = "newName") String newName,
            @RequestParam(name = "userId") Integer userId,
            @Autowired Model model, @Autowired Principal principal) {
        userService.userRename(newName, userId);
        return "redirect:/user?id=" + userId;
    }
    @PostMapping("/makeAdmin")
    //@PreAuthorize("hasAuthority('admin') or hasAuthority('user') or hasAuthority('moderator')")
    public String makeAdmin(
            @RequestParam(name = "userId") Integer userId,
            @Autowired Model model, @Autowired Principal principal) {
        userService.makeAdmin(userId);
        return "redirect:/user?id=" + userId;
    }
    @PostMapping("/makeModer")
    //@PreAuthorize("hasAuthority('admin') or hasAuthority('user') or hasAuthority('moderator')")
    public String makeModer(
            @RequestParam(name = "userId") Integer userId,
            @Autowired Model model, @Autowired Principal principal) {
        userService.makeModer(userId);
        return "redirect:/user?id=" + userId;
    }
    @PostMapping("/makeUser")
    //@PreAuthorize("hasAuthority('admin') or hasAuthority('user') or hasAuthority('moderator')")
    public String makeUser(
            @RequestParam(name = "userId") Integer userId,
            @Autowired Model model, @Autowired Principal principal) {
        userService.makeUser(userId);
        return "redirect:/user?id=" + userId;
    }

}


