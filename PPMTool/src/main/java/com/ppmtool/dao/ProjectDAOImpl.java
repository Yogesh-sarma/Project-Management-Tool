package com.ppmtool.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ppmtool.entities.Project;

@Repository
public class ProjectDAOImpl implements ProjectDAO {

	@Autowired
	private SessionFactory sessionfactory;
	
	@Override
	public List<Project> findAllProjects() {
		Session session = sessionfactory.getCurrentSession();
		
		Query<Project> query = session.createQuery("from Project",Project.class);
		
		List<Project> projects = query.getResultList();
		return projects;
	}

	@Override
	public Project save(Project theProject) {
		Session session = sessionfactory.getCurrentSession();
		
		session.saveOrUpdate(theProject);
		
		return session.get(Project.class, theProject.getId());
	}

	@Override
	public Project findByProjectIdentifier(String projectId) {
		Session session = sessionfactory.getCurrentSession();
		Query<Project> theQuery = session.createQuery("from Project where project_identifier=:projectIdentifier", Project.class);
		theQuery.setParameter("projectIdentifier", projectId);
		Project project = theQuery.getResultList().stream().findFirst().orElse(null);
		return project;
	}

	@Override
	public void deleteProject(Project project) {
		Session session = sessionfactory.getCurrentSession();
		session.delete(project);
	}

	@Override
	public List<Project> searchProjects(String theSearchName) {
		Session currentSession = sessionfactory.getCurrentSession();
		Query<Project> theQuery = null;
		
		if(theSearchName != null && theSearchName.trim().length()>0) {
			theQuery = currentSession.createQuery("from Project where lower(projectName) like :theName", Project.class);
			theQuery.setParameter("theName", "%"+theSearchName.toLowerCase()+"%");
		}
		else {
			theQuery = currentSession.createQuery("from Project",Project.class);
		}
		
		List<Project> projects = theQuery.getResultList();
		
		return projects;
	}


}
