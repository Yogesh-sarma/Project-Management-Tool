package com.ppm.kanbantool.KanbanTool2.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ProjectTask {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(updatable = false, unique = true)
	private String projectSequence;
	
	@NotBlank(message = "Please include a Project Summary!")
	private String summary;
	
	private String acceptanceCriteria;
	private String status;
	private Integer priority;
	
	//ManyToOne with Backlog
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="backlog_id", updatable = false, nullable = false)
	@JsonIgnore
	private Backlog backlog;
	
	@Column(updatable = false)
	private String projectIdentifier;
	
	@JsonFormat(pattern = "yyyy-mm-dd")
	private Date dueDate;
	
	@JsonFormat(pattern = "yyyy-mm-dd")
	private Date created_At;
	
	@JsonFormat(pattern = "yyyy-mm-dd")
	private Date updated_At;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "projectTask", orphanRemoval = true)
	private List<Comment> comments = new ArrayList<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	@JsonIgnore
	private User assignedTo;

	private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public User getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(User user) {
		this.assignedTo = user;
	}

	public ProjectTask() {
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		for(Comment comment: comments){
			addComment(comment);
		}
	}

	public void addComment(Comment comment) {
		comment.setProjectTask(this); // Set the parent in the child
		this.comments.add(comment);  // Add the child to the parent's list
	}

	public void removeComment(Comment comment) {
		comment.setProjectTask(null); // Break the parent link in the child
		this.comments.remove(comment); // Remove the child from the parent's list
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProjectSequence() {
		return projectSequence;
	}

	public void setProjectSequence(String projectSequence) {
		this.projectSequence = projectSequence;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getAcceptanceCriteria() {
		return acceptanceCriteria;
	}

	public void setAcceptanceCriteria(String acceptanceCriteria) {
		this.acceptanceCriteria = acceptanceCriteria;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public String getProjectIdentifier() {
		return projectIdentifier;
	}

	public void setProjectIdentifier(String projectIdentifier) {
		this.projectIdentifier = projectIdentifier;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
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
	
	public Backlog getBacklog() {
		return backlog;
	}

	public void setBacklog(Backlog backlog) {
		this.backlog = backlog;
	}

	@PrePersist
	protected void onCreate() {
		this.created_At = new Date();
	}
	
	@PreUpdate
	protected void onUpdate() {
		this.updated_At = new Date();
	}

	@Override
	public String toString() {
		return "ProjectTask{" +
				"id=" + id +
				", projectSequence='" + projectSequence + '\'' +
				", summary='" + summary + '\'' +
				", acceptanceCriteria='" + acceptanceCriteria + '\'' +
				", status='" + status + '\'' +
				", priority=" + priority +
				", backlog=" + backlog +
				", projectIdentifier='" + projectIdentifier + '\'' +
				", dueDate=" + dueDate +
				", created_At=" + created_At +
				", updated_At=" + updated_At +
				", comments=" + comments +
				'}';
	}
}
