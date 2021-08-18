package ru.Sidorov.ChatSimbirsoft.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.Sidorov.ChatSimbirsoft.domain.Role;
import ru.Sidorov.ChatSimbirsoft.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer> {
    Optional<User> findByUserName(String userName);

    Optional<User> findByLoginAndPassword(String login, String password);

    Optional<User> findByLogin(String login);

}
