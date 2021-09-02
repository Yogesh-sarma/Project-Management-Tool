package com.ppmtool.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ppmtool.dao.ProjectDAO;
import com.ppmtool.entities.Project;
import com.ppmtool.exceptions.ProjectIdException;

@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {
	
	@Autowired
	private ProjectDAO projectDao;
	
	@Override
	public Project saveOrUpdate(Project project) {
		try{
			project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			return projectDao.save(project);
		} catch(Exception ex){
			throw new ProjectIdException("Project ID '"+project.getProjectIdentifier().toUpperCase()+"' already exists!");
		}
	}

	@Override
	public Project findByProjectIdentifier(String projectId) {
		Project project = projectDao.findByProjectIdentifier(projectId);
		
		if(project == null){
			throw new ProjectIdException("Project ID '"+projectId+"' doesn't exist!");
		}
		return projectDao.findByProjectIdentifier(projectId);
	}

	@Override
	public void deleteProjectByIdentifier(String projectId){
		Project project = findByProjectIdentifier(projectId);
		projectDao.deleteProject(project);
	}
	
	@Override
	public List<Project> findAllProjects() {

		return projectDao.findAllProjects();
	}
	
}
