package com.ppm.kanbantool.KanbanTool2;

import com.ppm.kanbantool.KanbanTool2.domain.Privilege;
import com.ppm.kanbantool.KanbanTool2.domain.Role;
import com.ppm.kanbantool.KanbanTool2.domain.User;
import com.ppm.kanbantool.KanbanTool2.repositories.PrivilegeRepository;
import com.ppm.kanbantool.KanbanTool2.repositories.RoleRepository;
import com.ppm.kanbantool.KanbanTool2.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Component
public class SetupDataLoader implements
        ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup)
            return;
        Privilege readPrivilege
                = createPrivilegeIfNotFound("READ_PRIVILEGE");
        Privilege writePrivilege
                = createPrivilegeIfNotFound("WRITE_PRIVILEGE");

        List<Privilege> adminPrivileges = Arrays.asList(
                readPrivilege, writePrivilege);
        createRoleIfNotFound("ROLE_MANAGER", adminPrivileges);
        createRoleIfNotFound("ROLE_MEMBER", Arrays.asList(readPrivilege));

        Role adminRole = roleRepository.findByName("ROLE_MANAGER");
        User user = userRepository.findByUsername("Admin@gmail.com");
        if(user == null) {
            user = new User();
            user.setUsername("Admin@gmail.com");
            user.setFullName("Admin");
            user.setPassword(passwordEncoder.encode("admin"));
            user.setRoles(Arrays.asList(adminRole));
            userRepository.save(user);
        }

        alreadySetup = true;
    }

    @Transactional
    public Privilege createPrivilegeIfNotFound(String name) {

        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege();
            privilege.setName(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    public void createRoleIfNotFound(
            String name, Collection<Privilege> privileges) {

        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role();
            role.setName(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
    }
}
