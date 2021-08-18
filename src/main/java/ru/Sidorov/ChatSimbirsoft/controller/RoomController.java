package ru.Sidorov.ChatSimbirsoft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.Sidorov.ChatSimbirsoft.domain.MessageList;
import ru.Sidorov.ChatSimbirsoft.domain.Room;
import ru.Sidorov.ChatSimbirsoft.domain.User;
import ru.Sidorov.ChatSimbirsoft.dto.MessageListDto;
import ru.Sidorov.ChatSimbirsoft.service.MessageListServices;
import ru.Sidorov.ChatSimbirsoft.service.RoleService;
import ru.Sidorov.ChatSimbirsoft.service.RoomServices;
import ru.Sidorov.ChatSimbirsoft.service.UserService;

import java.security.Principal;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
public class RoomController {
    @Autowired
    MessageListServices messageListServices;
    @Autowired
    RoomServices roomServices;
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;

    //@PreAuthorize("hasAuthority('admin') or hasAuthority('user') or hasAuthority('moderator')")
    @RequestMapping(value = "/room", method = RequestMethod.GET)
    public String room(@Autowired Model model,
                       @Autowired Principal principal,
                       @RequestParam(value = "id", required = false) Integer id) {
        model.addAttribute("rooms", roomServices.getAllRoomsByUser(principal));
        if (id != null) {
            List<MessageListDto> messageListDtos = new ArrayList<MessageListDto>();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            for(MessageList messageList : roomServices.getMessagesByRoom(id)){
                messageListDtos.add(new MessageListDto(messageList.getId(),messageList.getMessage(),formatter.format(messageList.getMessageDate()),messageList.getUser(), messageList.getRoom()));
            }
            model.addAttribute("messageLists", messageListDtos);
            model.addAttribute("roomId", id);
            model.addAttribute("owner", roomServices.findById(id).get().getUserOwner());
            model.addAttribute("usersInRoom", roomServices.usersInRoom(id));
            model.addAttribute("otherUsers", roomServices.usersOutRoom(roomServices.usersInRoom(id), id));
            model.addAttribute("principalRole", userService.findByLogin(principal.getName()).get().getRole().getUserRole());
            model.addAttribute("principal", userService.findByLogin(principal.getName()).get());
            model.addAttribute("ban", roomServices.findById(id).get().getUserBannedSet().stream().filter(user -> user.getLogin().equals(principal.getName())).count() > 0);
            model.addAttribute("userInRoomOrNot", roomServices.findById(id).get().getUserSet().stream().filter(user -> user.getLogin().equals(principal.getName())).count() > 0);
            model.addAttribute("bannedUsersinRoom", roomServices.findById(id).get().getUserBannedSet());
        }
        return "room";
    }

    @PostMapping("/addUserToRoom")
    //@PreAuthorize("hasAuthority('admin') or hasAuthority('user') or hasAuthority('moderator')")
    public String addUserToRoom(
            @RequestParam(name = "userId") Integer userId,
            @RequestParam(name = "roomId") Integer roomId,
            @Autowired Model model, @Autowired Principal principal) {
        roomServices.addUserToRoom(userId, roomId);
        return "redirect:/room?id=" + roomId;
    }

    @PostMapping("/removeUserInRoom")
    //@PreAuthorize("hasAuthority('admin') or hasAuthority('user') or hasAuthority('moderator')")
    public String removeUserInRoom(
            @RequestParam(name = "userId") Integer userId,
            @RequestParam(name = "roomId") Integer roomId,
            @RequestParam(name = "minutes", required = false) Integer minutes,
            @Autowired Model model, @Autowired Principal principal) {
        roomServices.removeUserInRoom(userId, roomId, minutes);
        return "redirect:/room?id=" + roomId;
    }
    @PostMapping("/removeUserInRooms")
    //@PreAuthorize("hasAuthority('admin') or hasAuthority('user') or hasAuthority('moderator')")
    public String removeUserInRooms(
            @RequestParam(name = "userId") Integer userId,
            @RequestParam(name = "minutes", required = false) Integer minutes,
            @Autowired Model model, @Autowired Principal principal) {
        roomServices.removeUserInRooms(userId, minutes);
        return "redirect:/user?id=" + userId;
    }

    @PostMapping("/roomRemove")
    //@PreAuthorize("hasAuthority('admin') or hasAuthority('user') or hasAuthority('moderator')")
    public String removeRoom(
            @RequestParam(name = "roomId") Integer roomId,
            @Autowired Model model, @Autowired Principal principal) {
        roomServices.removeRoom(roomId);
        return "redirect:/room";
    }

    @PostMapping("/roomRename")
    //@PreAuthorize("hasAuthority('admin') or hasAuthority('user') or hasAuthority('moderator')")
    public String roomRename(
            @RequestParam(name = "newName") String newName,
            @RequestParam(name = "roomId") Integer roomId,
            @Autowired Model model, @Autowired Principal principal) {
        roomServices.roomRename(newName, roomId);
        return "redirect:/room?id=" + roomId;
    }

    @PostMapping("/removeMessage")
    //@PreAuthorize("hasAuthority('admin') or hasAuthority('user') or hasAuthority('moderator')")
    public String removeMessage(
            @RequestParam(name = "messageId") Integer messageId,
            @RequestParam(name = "roomId") Integer roomId) {
        messageListServices.deleteById(messageId);
        return "redirect:/room?id=" + roomId;
    }

}
