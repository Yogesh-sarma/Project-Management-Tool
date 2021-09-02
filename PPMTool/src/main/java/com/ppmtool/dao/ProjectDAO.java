package com.ppmtool.dao;

import java.util.List;

import com.ppmtool.entities.Project;

public interface ProjectDAO {
	public List<Project> findAllProjects();

	public Project save(Project theProject);

	public Project findByProjectIdentifier(String projectId);

	public void deleteProject(Project project);

	public List<Project> searchProjects(String theSearchName);

}
