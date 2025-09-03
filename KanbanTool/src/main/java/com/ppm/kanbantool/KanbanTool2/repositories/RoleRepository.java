package com.ppm.kanbantool.KanbanTool2.repositories;

import com.ppm.kanbantool.KanbanTool2.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);

    @Override
    void delete(Role role);

}