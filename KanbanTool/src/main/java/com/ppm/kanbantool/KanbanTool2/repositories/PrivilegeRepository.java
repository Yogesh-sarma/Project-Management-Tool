package com.ppm.kanbantool.KanbanTool2.repositories;


import com.ppm.kanbantool.KanbanTool2.domain.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

    Privilege findByName(String name);

    @Override
    void delete(Privilege privilege);

}
