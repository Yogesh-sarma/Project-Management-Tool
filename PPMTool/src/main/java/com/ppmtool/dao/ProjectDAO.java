package com.ppmtool.dao;

import java.util.List;

import com.ppmtool.entities.Project;

public interface ProjectDAO {
	public List<Project> getProjects();

	public Project save(Project theProject);

	public Project findById(Long theId);

	public void deleteById(Long theId);

	public List<Project> searchProjects(String theSearchName);

}
