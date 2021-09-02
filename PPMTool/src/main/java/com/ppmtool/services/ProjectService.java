package com.ppmtool.services;

import java.util.List;

import com.ppmtool.entities.Project;

public interface ProjectService {
	
	public Project saveOrUpdate(Project project);
	public Project findByProjectIdentifier(String projectId);
	public void deleteProjectByIdentifier(String projectId);
	public List<Project> findAllProjects();
}
