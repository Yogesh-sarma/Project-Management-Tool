package com.ppm.kanbantool.KanbanTool2.services;


import com.ppm.kanbantool.KanbanTool2.domain.Backlog;
import com.ppm.kanbantool.KanbanTool2.domain.Comment;
import com.ppm.kanbantool.KanbanTool2.domain.ProjectTask;
import com.ppm.kanbantool.KanbanTool2.domain.User;
import com.ppm.kanbantool.KanbanTool2.exceptions.ProjectNotFoundException;
import com.ppm.kanbantool.KanbanTool2.repositories.BacklogRepository;
import com.ppm.kanbantool.KanbanTool2.repositories.ProjectRepository;
import com.ppm.kanbantool.KanbanTool2.repositories.ProjectTaskRepository;
import com.ppm.kanbantool.KanbanTool2.repositories.UserRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ProjectTaskService {

	@Autowired
	private BacklogRepository backlogRepository;

	@Autowired
	private ProjectTaskRepository projectTaskRepository;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private ProjectService projectService;
	
	public Iterable<ProjectTask> findBacklogById(String id, String username) {
		
		projectService.findProjectByIdentifier(id, username);
		
		return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
	}
	
	//get by ID
	public ProjectTask findPTByProjectSequence(String backlog_id, String pt_id, String username) {
		
		//make sure we are searching on an existing backlog
		projectService.findProjectByIdentifier(backlog_id, username);
		
		//make sure that our task exists
		ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);
		
		if(projectTask==null) {
			throw new ProjectNotFoundException("Project Task '"+pt_id+"' not found!");
		}
		
		//make sure that the backlog/project id in the path corresponds to the right project
		if(!projectTask.getProjectIdentifier().equals(backlog_id)) {
			throw new ProjectNotFoundException("Project Task '"+pt_id+"' does not exist on the following Project: '"+backlog_id+"'");
		}


		Hibernate.initialize(projectTask.getComments());

		return projectTask;
	}

	//post
	public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask, String username) {
		
		
			//PTs to be added to a specific project, project != null, BL exists
			Backlog backlog = projectService.findProjectByIdentifier(projectIdentifier, username).getBacklog();
			
			//Set the Backlog to the Project Task
			projectTask.setBacklog(backlog);
			
			//we want our project sequence to be like this: IDPRO-1, IDPRO-2, IDPRO-3 ...150
			Integer BacklogSequence = backlog.getPTSequence();
			
			//Update the Backlog Sequence
			BacklogSequence++;
			backlog.setPTSequence(BacklogSequence);
			
			//Add Sequence to Project Task
			projectTask.setProjectSequence(projectIdentifier+"-"+BacklogSequence);
			projectTask.setProjectIdentifier(projectIdentifier);
			
			//Setting a Initial Priority to the Project Task when is NULL 
			if(projectTask.getPriority()==null || projectTask.getPriority()==0) {
				projectTask.setPriority(3);
			}
			
			//Setting a Initial Status to the Project Task when is NULL 
			if(projectTask.getStatus()=="" || projectTask.getStatus()==null) {
				projectTask.setStatus("TO_DO");
			}

			if(!projectTask.getUsername().isEmpty()){
				User user = userRepository.findByUsername(projectTask.getUsername());
				user.addProjectTask(projectTask);
			}

			
			return projectTaskRepository.save(projectTask);
				
		
	}
	
	//update
	public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String backlog_id, String pt_id, String username) {

		ProjectTask projectTask = this.findPTByProjectSequence(backlog_id, pt_id, username);
		// Fetch the User entity for the given username
		User user = userRepository.findByUsername(updatedTask.getUsername());
		if (user == null) {
			throw new RuntimeException("User not found for username: " + updatedTask.getUsername());
		}

		// Update the fields of the existing ProjectTask entity
		projectTask.setSummary(updatedTask.getSummary());
		projectTask.setAcceptanceCriteria(updatedTask.getAcceptanceCriteria());
		projectTask.setStatus(updatedTask.getStatus());
		projectTask.setPriority(updatedTask.getPriority());
		projectTask.setDueDate(updatedTask.getDueDate());
		projectTask.setProjectIdentifier(updatedTask.getProjectIdentifier());
//		projectTask.setComments(updatedTask.getComments());
		projectTask.setAssignedTo(userRepository.findByUsername(updatedTask.getUsername()));
		projectTask.setUsername(updatedTask.getUsername());

		// Update the relationship with the User
		if (!user.getProjectTasks().contains(projectTask)) {
			user.addProjectTask(projectTask);
		}

		return projectTaskRepository.save(projectTask);
		
	}
	
	
	//delete
	public void deleteByProjectSequence(String backlog_id, String pt_id, String username) {
		ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id, username);
		projectTaskRepository.delete(projectTask);		
	}

	public ProjectTask addComment(String backlogId, String ptId, Comment comment, String username) {
		comment.setUsername(username);
		ProjectTask projectTask = findPTByProjectSequence(backlogId, ptId, username);
		System.out.println(projectTask.getComments().size());
		projectTask.addComment(comment);
		return projectTaskRepository.save(projectTask);
	}
}
