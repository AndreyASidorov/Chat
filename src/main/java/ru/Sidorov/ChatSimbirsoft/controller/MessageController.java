package ru.Sidorov.ChatSimbirsoft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.Sidorov.ChatSimbirsoft.domain.MessageList;
import ru.Sidorov.ChatSimbirsoft.service.MessageListServices;
import ru.Sidorov.ChatSimbirsoft.service.RoomServices;
import ru.Sidorov.ChatSimbirsoft.service.UserService;

import java.security.Principal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class MessageController {
    @Autowired
    MessageListServices messageListServices;

    @Autowired
    UserService userService;

    @Autowired
    RoomServices roomServices;

    @PostMapping("/messageSend")
    //@PreAuthorize("hasAuthority('admin') or hasAuthority('user') or hasAuthority('moderator')")
    public String messageSend(
            @RequestParam(name = "message") String name,
            @RequestParam(name = "roomId") Integer roomId,
            @Autowired Model model, @Autowired Principal principal) {
        if(name.indexOf("//") == 0){
            messageListServices.messageCommand(name, roomId, principal);
            return roomServices.findById(roomId).orElse(null) == null ?  "redirect:/room" :
            "redirect:/room?id=" + roomId;
        }
        messageListServices.messageSend(name, roomId, principal);
        return "redirect:/room?id=" + roomId;
    }
    @PostMapping("/messageEdit")
    //@PreAuthorize("hasAuthority('admin') or hasAuthority('user') or hasAuthority('moderator')")
    public String messageEdit(
            @RequestParam(name = "newText") String newText,
            @RequestParam(name = "roomId") Integer roomId,
            @RequestParam(name = "messageId") Integer messageId,
            @Autowired Model model, @Autowired Principal principal) {
        messageListServices.messageEdit(newText, roomId, messageId);
        return "redirect:/room?id=" + roomId;
    }
}
