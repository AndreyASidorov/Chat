package ru.Sidorov.ChatSimbirsoft.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.Sidorov.ChatSimbirsoft.domain.Room;
import org.springframework.data.repository.CrudRepository;
import ru.Sidorov.ChatSimbirsoft.domain.User;

import java.util.List;
import java.util.Optional;

public interface RoomRepo extends JpaRepository<Room, Integer> {
    Optional<Room> findByName(String name);

    @Query(value = "select r.* from chat.room r\n" +
            "join chat.room_users u on r.id = u.room_id\n" +
            "where u.user_id = :userid", nativeQuery = true)
    List<Room> findByUserId(@Param("userid") User userid);

    List<Room> findByUserOwner(User userOwner);

    List<Room> findByIsPublic(boolean isPublic);
}
