package com.ppm.kanbantool.KanbanTool2.services;

import com.ppm.kanbantool.KanbanTool2.domain.Backlog;
import com.ppm.kanbantool.KanbanTool2.domain.Project;
import com.ppm.kanbantool.KanbanTool2.domain.User;
import com.ppm.kanbantool.KanbanTool2.exceptions.ProjectIdException;
import com.ppm.kanbantool.KanbanTool2.exceptions.ProjectNotFoundException;
import com.ppm.kanbantool.KanbanTool2.repositories.BacklogRepository;
import com.ppm.kanbantool.KanbanTool2.repositories.ProjectRepository;
import com.ppm.kanbantool.KanbanTool2.repositories.UserRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService {
	
	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private BacklogRepository backlogRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Transactional
	public Project saveOrUpdateProject(Project project, String projectLeader) {
		if(project.getId() != null) {
			Project existingProject = projectRepository.findByProjectIdentifier(project.getProjectIdentifier());

			if(existingProject != null &&(!existingProject.getProjectLeader().equals(projectLeader))) {
				throw new ProjectNotFoundException("Project not found in your account");
			}else if(existingProject == null) {
				throw new ProjectNotFoundException("Project with ID:'"+project.getProjectIdentifier()+"' cannot be updated because it does not exist!");
			}
			Hibernate.initialize(existingProject.getUser());
		} 
		
		try {
			User leader = userRepository.findByUsername(projectLeader);
			project.setProjectLeader(leader.getUsername());
			
			project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			
			//saving new project
			if(project.getId()==null) {
				List<User> users = new ArrayList<>();
				users.add(leader);
				for(String username: project.getListOfUsers()){
					users.add(userRepository.findByUsername(username));
				}
				project.setUser(users);
				Backlog backlog = new Backlog();
				project.setBacklog(backlog);
				backlog.setProject(project);
				backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			}
			
			//updating project
			if(project.getId()!=null) {
				List<User> users = new ArrayList<>();
				users.add(leader);
				for(String username: project.getListOfUsers()){
					users.add(userRepository.findByUsername(username));
				}
				project.setUser(users);
				project.setBacklog(backlogRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
			}
			
			return projectRepository.save(project);
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
			throw new ProjectIdException("Project ID '"+ project.getProjectIdentifier().toUpperCase()+"' already exists.");
		}
	
	}
	
	//find by ID
	public Project findProjectByIdentifier(String projectId, String username) {
		
		Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
		
		if(project == null) {
			throw new ProjectIdException("Project ID '"+ projectId+"' does not exist.");
		}
		
		if(!project.getUser().contains(userRepository.findByUsername(username))) {
			throw new ProjectNotFoundException("Project not found in your account!");
		}
		
		
		return project;
	}

	public Iterable<User> findAllUserOfAProject(String projectId){
		Project project = projectRepository.findByProjectIdentifier(projectId);
		return project.getUser();
	}

	public List<String> getAllUsernamesOfAProject(String projectId){
		List<User> users = (List<User>) findAllUserOfAProject(projectId);
		List<String> usernames = new ArrayList<>();

		for(User user : users){
			usernames.add(user.getUsername());
		}
		return usernames;
	}

	public Iterable<Project> findAllProjectsOfAUser(String username){
		User user = userRepository.findByUsername(username);
		return user.getProjects();
	}

	//find All
	public Iterable<Project> findAllProjectsByProjectLeader(String username){
		return projectRepository.findAllByProjectLeader(username);
	}
	
	//delete by ID
	public void deleteProjectByIdentifier(String projectId, String username) {
		Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
		if(!project.getProjectLeader().equals(username)){
			throw new ProjectNotFoundException("Project not found in your account!");
		}
		projectRepository.delete(findProjectByIdentifier(projectId, username));
	}
	

}
