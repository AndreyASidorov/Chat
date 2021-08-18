package ru.Sidorov.ChatSimbirsoft.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.Sidorov.ChatSimbirsoft.domain.Role;
import ru.Sidorov.ChatSimbirsoft.repo.RoleRepo;
import ru.Sidorov.ChatSimbirsoft.service.RoleService;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleRepo roleRepo;

    public void deleteById(Integer id) {
        roleRepo.deleteById(id);
    }

    public void save(Role userRole) {
        roleRepo.save(userRole);
    }

    public List<Role> getAll() {
        return roleRepo.findAll();
    }

    public Optional<Role> findById(Integer id) {
        return roleRepo.findById(id);
    }

    public Optional<Role> findByUserRole(String name) {
        return roleRepo.findByUserRole(name);
    }


}
