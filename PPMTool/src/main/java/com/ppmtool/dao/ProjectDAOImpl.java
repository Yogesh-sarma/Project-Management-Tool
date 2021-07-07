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
	public List<Project> getProjects() {
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
	public Project findById(Long theId) {
		Session session = sessionfactory.getCurrentSession();
		
		Project project = session.get(Project.class, theId);
		return project;
	}

	@Override
	public void deleteById(Long theId) {
		Session session = sessionfactory.getCurrentSession();
		Query<Project> theQuery= session.createQuery("delete from Customer where id=:theCustomerId",Project.class);
		theQuery.setParameter("theCustomerId", theId);
		theQuery.executeUpdate();

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
