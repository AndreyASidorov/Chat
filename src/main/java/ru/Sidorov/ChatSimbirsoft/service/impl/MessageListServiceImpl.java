package ru.Sidorov.ChatSimbirsoft.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import ru.Sidorov.ChatSimbirsoft.domain.MessageList;
import ru.Sidorov.ChatSimbirsoft.domain.Role;
import ru.Sidorov.ChatSimbirsoft.domain.Room;
import ru.Sidorov.ChatSimbirsoft.domain.User;
import ru.Sidorov.ChatSimbirsoft.dto.MessageSend;
import ru.Sidorov.ChatSimbirsoft.repo.MessageListRepo;
import ru.Sidorov.ChatSimbirsoft.repo.RoomRepo;
import ru.Sidorov.ChatSimbirsoft.repo.UserRepo;
import ru.Sidorov.ChatSimbirsoft.service.MessageListServices;
import ru.Sidorov.ChatSimbirsoft.service.MySecretKeys;
import ru.Sidorov.ChatSimbirsoft.service.RoomServices;
import ru.Sidorov.ChatSimbirsoft.service.UserService;
import ru.Sidorov.ChatSimbirsoft.service.impl.commands.*;

import javax.transaction.Transactional;
import java.io.IOException;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.*;

@Service
public class MessageListServiceImpl implements MessageListServices {
    @Autowired
    MessageListRepo messageListRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    RoomRepo roomRepo;
    @Autowired
    MySecretKeys mySecretKeys;
    @Autowired
    private SimpMessagingTemplate template;
    @Autowired
    RoomServices roomServices;
    @Autowired
    UserService userService;

    @Override
    public void deleteById(Integer id) {
        messageListRepo.deleteById(id);
    }

    @Override
    public void save(MessageList messageList) {
        messageListRepo.save(messageList);
    }

    @Override
    public Optional<MessageList> findById(Integer id) {
        return messageListRepo.findById(id);
    }

    @Override
    public List<MessageList> getAll() {
        return messageListRepo.findAll();
    }

    @Override
    public List<MessageList> findByRoom(Room room) {
        return messageListRepo.findByRoomOrderByMessageDate(room);
    }

    @Override
    public void messageEdit(String text, Integer roomId, Integer messageId) {
        MessageList messageList = messageListRepo.findById(messageId).get();
        messageList.setMessage(text);
        messageListRepo.save(messageList);
    }

    public void messageSend(String name, Integer id, Principal principal) {
        //banned
        for (User bannedUser : roomRepo.findById(id).get().getUserBannedSet()) {
            if (bannedUser.getLogin().equals(principal.getName())) {
                return;
            }
        }
        MessageList messageList = new MessageList();
        messageList.setMessage(name);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        //TemporalAccessor ta = formatter.parse(ZonedDateTime.now())); // java.time.format.Parsed
        //LocalDateTime messageDate = ZonedDateTime.now().toLocalDateTime();
        messageList.setMessageDate(ZonedDateTime.now().toLocalDateTime()/*.format(formatter)*/);
        messageList.setUser(userRepo.findByLogin(principal.getName()).orElse(null));
        messageList.setRoom(roomRepo.findById(id).get());
        messageListRepo.save(messageList);
        //banned
        List<User> currentUsers = new ArrayList<>();
        Set<User> userSet = new HashSet<>(messageList.getRoom().getUserSet());
        userSet.add(messageList.getRoom().getUserOwner());
        for (User user : userSet) {
            currentUsers.add(user);
        }
        /*userSet.removeAll(messageList.getRoom().getUserBannedSet());
        for(User user : userSet) {
            if (!user.getLogin().equals(principal.getName())) {
                boolean isBanned = false;
                for (User bannedUser : messageList.getRoom().getUserBannedSet()) {
                    if (bannedUser.getLogin().equals(user.getLogin()))
                        isBanned = true;
                }
                if (!isBanned)
                    currentUsers.add(user);
            }
        }*/
        //get keys for websocket
        for (User user : currentUsers) {
            String secretKey = mySecretKeys.getMySecretKey().get(user.getLogin());
            if (secretKey != null) {
                MessageSend messageSend = new MessageSend(messageList.getUser().getUserName(),
                        formatter.format(messageList.getMessageDate()),
                        messageList.getMessage(),
                        messageList.getId(),
                        messageList.getRoom().getId());
                template.convertAndSend("/topic/" + secretKey + "/sendMessage", messageSend);
            }
        }
    }

    @Override
    public void messageCommand(String message, Integer roomId, Principal principal) {
        Role role = userRepo.findByLogin(principal.getName()).get().getRole();
        boolean isOwner = roomRepo.findById(roomId).get().getUserOwner().equals(userRepo.findByLogin(principal.getName()).get());
        boolean isAdmin = role.getUserRole().equals("admin");
        boolean isModerator = role.getUserRole().equals("moderator");
        ConsoleCommand consoleCommand = null;
        try {
            if (isAdmin || isOwner || isModerator) {
                if (message.contains("//room remove")) {
                    consoleCommand = new ConsoleCommandRoomRemove();
                }
                if (message.contains("//room rename")) {
                    consoleCommand = new ConsoleCommandRoomRename();
                }
                if (message.contains("//room connect")) {
                    consoleCommand = new ConsoleCommandRoomConnect();
                }
                if (message.contains("//room disconnect")) {
                    consoleCommand = new ConsoleCommandRoomDisconnect();
                }
                if (message.contains("//user rename")) {
                    consoleCommand = new ConsoleCommandUserRename();
                }
                if (message.contains("//user ban")) {
                    consoleCommand = new ConsoleCommandUserBan();
                }
                if (message.contains("//user moderator")) {
                    consoleCommand = new ConsoleCommandUserModerator();
                }
            }
            if (message.contains("//room create")) {
                consoleCommand = new ConsoleCommandRoomCreate();
            }
            if(consoleCommand != null){
                consoleCommand.execute(message, principal, isAdmin, isOwner, roomRepo, userRepo, roomServices, userService, roomId);
            }
        } catch (Exception e) {
        }
    }
}
