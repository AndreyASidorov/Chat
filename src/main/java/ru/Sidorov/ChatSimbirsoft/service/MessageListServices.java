package ru.Sidorov.ChatSimbirsoft.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import ru.Sidorov.ChatSimbirsoft.domain.MessageList;
import ru.Sidorov.ChatSimbirsoft.domain.Room;


import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface MessageListServices {
    void deleteById(Integer id);

    void save(MessageList messageList);

    Optional<MessageList> findById(Integer id);

    List<MessageList> getAll();

    List<MessageList> findByRoom(Room room);

    void messageEdit(String text, Integer roomId, Integer messageId);

    void messageSend(String name, Integer id, Principal principal);

    void messageCommand(String name, Integer id, Principal principal);
}
