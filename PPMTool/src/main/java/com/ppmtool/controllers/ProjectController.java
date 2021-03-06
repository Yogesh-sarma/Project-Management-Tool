package com.ppmtool.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ppmtool.entities.Project;
import com.ppmtool.services.ProjectService;
import com.ppmtool.services.ValidationErrorService;

@RestController
@RequestMapping("/api/project")
public class ProjectController {
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private ValidationErrorService validationErrorService;
	
	@PostMapping(value = "", produces="application/json")
	public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult result){

		ResponseEntity<?> errorMap = validationErrorService.MapValidationService(result);
		if(errorMap!=null) return errorMap;
		
		Project proj1 = projectService.saveOrUpdate(project);
		return new ResponseEntity<Project>(proj1, HttpStatus.CREATED);
	}
	
	@GetMapping(value="/{projectId}")
	public ResponseEntity<?> getProjectById(@PathVariable String projectId){
		Project project = projectService.findByProjectIdentifier(projectId);
		
		return new ResponseEntity<Project>(project, HttpStatus.OK);
	}
	
	@GetMapping(value="/all")
	public List<Project> getAllProjects(){
		return projectService.findAllProjects();
	}
	
	@DeleteMapping(value="/delete/{projectIdentifier}")
	public ResponseEntity<?> deleteProject(@PathVariable String projectIdentifier){
		projectService.deleteProjectByIdentifier(projectIdentifier);
		return new ResponseEntity<String>("Project with project identifier '"+projectIdentifier+"' was deleted!", HttpStatus.OK);
	}
}
