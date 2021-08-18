package ru.Sidorov.ChatSimbirsoft.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.Sidorov.ChatSimbirsoft.domain.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<Role, Integer> {

    Optional<Role> findByUserRole(String userRole);
}
