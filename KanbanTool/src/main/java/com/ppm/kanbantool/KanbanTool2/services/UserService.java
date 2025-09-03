package com.ppm.kanbantool.KanbanTool2.services;

import com.ppm.kanbantool.KanbanTool2.domain.Role;
import com.ppm.kanbantool.KanbanTool2.domain.User;
import com.ppm.kanbantool.KanbanTool2.exceptions.UsernameAlreadyExistsException;
import com.ppm.kanbantool.KanbanTool2.repositories.RoleRepository;
import com.ppm.kanbantool.KanbanTool2.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserService {

	

    @Autowired
    private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public User saveUser (User newUser) {
		
		try {
			newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
			//Username has to be unique 
			newUser.setUsername(newUser.getUsername());
			//Don't persist the password
			newUser.setConfirmPassword("");
			newUser.setRoles(Arrays.asList(roleRepository.findByName(newUser.getRole())));

			return userRepository.save(newUser);
			
		}catch(Exception error) {
			throw new UsernameAlreadyExistsException("Username '"+newUser.getUsername()+"' already exists!");
		}

	}

	public List<String> getAllUsernames(){
		List<User> users = (List<User>) userRepository.findAll();
		List<String> usernames = new ArrayList<>();

		for(User user : users){
			usernames.add(user.getUsername());
		}
		return usernames;
	}
	
}
