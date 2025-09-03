package com.ppm.kanbantool.KanbanTool2.web;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import com.ppm.kanbantool.KanbanTool2.domain.Project;
import com.ppm.kanbantool.KanbanTool2.services.MapValidationErrorService;
import com.ppm.kanbantool.KanbanTool2.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/project")
@CrossOrigin
public class ProjectController {

	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private MapValidationErrorService mapValidationErrorService;
	
	@PostMapping("/create")
	@Secured("ROLE_MANAGER")
	public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult result, Principal principal){
		
		ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
		if(errorMap!=null) {
			return errorMap;
		}
		
		Project project1 = projectService.saveOrUpdateProject(project, principal.getName());
		return new ResponseEntity<>(project1, HttpStatus.CREATED);
		
	}
	
	@GetMapping("/{projectId}")
	public ResponseEntity<?> getProjectById(@PathVariable String projectId, Principal principal){
		Project project = projectService.findProjectByIdentifier(projectId, principal.getName());
		return new ResponseEntity<>(project, HttpStatus.OK);
	}

	@GetMapping("/{projectId}/users")
	public ResponseEntity<?> getUsersOfAProject(@PathVariable String projectId){
		return new ResponseEntity<>(projectService.findAllUserOfAProject(projectId), HttpStatus.OK);
	}

	@GetMapping("/{projectId}/usernames")
	public ResponseEntity<?> getUsernamesOfAProject(@PathVariable String projectId){
		List<String> usernames = projectService.getAllUsernamesOfAProject(projectId);
		for(String user: usernames){
			System.out.println(user);
		}
		return new ResponseEntity<>(usernames, HttpStatus.OK);
	}
	
	@GetMapping("/all")
	public ResponseEntity<?> getAllProjects(Principal principal){
		return new ResponseEntity<>(projectService.findAllProjectsOfAUser(principal.getName()), HttpStatus.OK);
	}


	@Secured("ROLE_MANAGER")
	@DeleteMapping("/{projectId}")
	public ResponseEntity<?> deleteProject(@PathVariable String projectId, Principal principal){
		projectService.deleteProjectByIdentifier(projectId, principal.getName());
		return new ResponseEntity<String> ("Project with ID: '"+projectId+"' was deleted.", HttpStatus.OK);
	}
	
	
}
