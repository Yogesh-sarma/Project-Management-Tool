package com.ppmtool.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="project")
public class Project {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="project_name")
	@NotBlank(message = "Project name cannot be blank")		
	private String projectName;
	
	@Column(name="project_identifier", updatable = false, unique = true)
	@NotBlank(message="Project Identifier cannot be blank")
	@Size(min = 4, max = 5, message="Please use 4-5 character identifier")
	private String projectIdentifier;
	
	@Column(name="description")
	@NotBlank(message = "Project description is required")
	private String description;
	
	@Column(name="start_date")
	@JsonFormat(pattern = "dd-mm-yyyy")
	private Date start_date;
	
	@Column(name="end_date")
	@JsonFormat(pattern = "dd-mm-yyyy")
	private Date end_date;
	
	@Column(name="created_at")
	@JsonFormat(pattern = "dd-mm-yyyy")
	private Date created_At;
	
	@Column(name="updated_at")
	@JsonFormat(pattern = "dd-mm-yyyy")
	private Date updated_At;
	
	public Project(){
		
	}
	
	@PrePersist
	protected void onCreate(){
		this.created_At = new Date();
	}
	
	@PreUpdate
	protected void onUpdate(){
		this.updated_At = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectIdentifier() {
		return projectIdentifier;
	}

	public void setProjectIdentifier(String projectIdentifier) {
		this.projectIdentifier = projectIdentifier;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}

	public Date getCreated_At() {
		return created_At;
	}

	public void setCreated_At(Date created_At) {
		this.created_At = created_At;
	}

	public Date getUpdated_At() {
		return updated_At;
	}

	public void setUpdated_At(Date updated_At) {
		this.updated_At = updated_At;
	}
	
}
