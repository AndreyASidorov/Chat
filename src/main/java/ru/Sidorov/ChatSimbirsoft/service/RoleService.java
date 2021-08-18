package ru.Sidorov.ChatSimbirsoft.service;

import org.springframework.stereotype.Service;
import ru.Sidorov.ChatSimbirsoft.domain.Role;

import java.util.List;
import java.util.Optional;


public interface RoleService {
    void deleteById(Integer id);

    void save(Role userRole);

    Optional<Role> findById(Integer id);

    List<Role> getAll();

    Optional<Role> findByUserRole(String name);
}
