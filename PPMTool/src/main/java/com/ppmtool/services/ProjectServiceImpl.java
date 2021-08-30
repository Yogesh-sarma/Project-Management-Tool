package com.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ppmtool.dao.ProjectDAO;
import com.ppmtool.entities.Project;
import com.ppmtool.exceptions.ProjectIdException;

@Service
public class ProjectServiceImpl implements ProjectService {
	
	@Autowired
	private ProjectDAO projectDao;
	
	@Override
	@Transactional
	public Project saveOrUpdate(Project project) {
		try{
			project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			return projectDao.save(project);
		} catch(Exception ex){
			throw new ProjectIdException("Project ID '"+project.getProjectIdentifier().toUpperCase()+"' already exists! ");
		}
	}
	
}
