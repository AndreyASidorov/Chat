package ru.Sidorov.ChatSimbirsoft.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.Sidorov.ChatSimbirsoft.domain.MessageList;
import org.springframework.data.repository.CrudRepository;
import ru.Sidorov.ChatSimbirsoft.domain.MessageList;
import ru.Sidorov.ChatSimbirsoft.domain.Room;

import java.util.List;

public interface MessageListRepo extends JpaRepository<MessageList, Integer> {

    List <MessageList> findByRoomOrderByMessageDate(Room room);

}
