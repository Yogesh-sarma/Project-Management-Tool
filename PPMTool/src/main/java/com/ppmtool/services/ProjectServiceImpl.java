package com.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ppmtool.dao.ProjectDAO;
import com.ppmtool.entities.Project;

@Service
public class ProjectServiceImpl implements ProjectService {
	
	@Autowired
	private ProjectDAO projectDao;
	
	@Override
	@Transactional
	public Project saveOrUpdate(Project project) {
		return projectDao.save(project);
	}
	
}
