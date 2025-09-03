package com.ppm.kanbantool.KanbanTool2.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "user")
public class User implements UserDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @Email(message = "Username needs to be an email")
    @NotBlank(message = "username is required")
    @Column(unique = true)
    private String username;
	
	@NotBlank(message = "Please enter your full name")
	private String fullName;
	
	@NotBlank(message = "Password field is required")
	private String password;
	
	@Transient
	private String confirmPassword;
	
	
	@JsonFormat(pattern = "yyyy-mm-dd")
	private Date create_At;
	
	@JsonFormat(pattern = "yyyy-mm-dd")
	private Date update_At;
	
	//OneToMany with Project Relationship
	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, mappedBy = "user")
	private List<Project> projects = new ArrayList<>();

	@ManyToMany()
	@JoinTable(name = "user_roles",
				joinColumns = @JoinColumn(
						name="user_id", referencedColumnName = "id"),
			    inverseJoinColumns = @JoinColumn(
						name="role_id", referencedColumnName = "id"))
	private Collection<Role> roles;

	@Transient
	private String role;

	@OneToMany(mappedBy = "assignedTo", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ProjectTask> projectTasks = new ArrayList<>();

	public User() {
	}

	public void addProjectTask(ProjectTask projectTask) {
		projectTasks.add(projectTask);
		projectTask.setAssignedTo(this); // Set the bi-directional relationship
	}

	public void removeProjectTask(ProjectTask projectTask) {
		projectTasks.remove(projectTask);
		projectTask.setAssignedTo(null); // Clear the bi-directional relationship
	}

	public List<ProjectTask> getProjectTasks() {
		return projectTasks;
	}

	public void setProjectTasks(List<ProjectTask> projectTasks) {
		for(ProjectTask projectTask: projectTasks){
			addProjectTask(projectTask);
		}
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public Date getCreate_At() {
		return create_At;
	}

	public void setCreate_At(Date create_At) {
		this.create_At = create_At;
	}

	public Date getUpdate_At() {
		return update_At;
	}

	public void setUpdate_At(Date update_At) {
		this.update_At = update_At;
	}


	@PrePersist
	protected void onCreate() {
		this.create_At = new Date();
	}
	
	@PreUpdate
	protected void onUpdate() {
		this.update_At = new Date();
	}

	
	/* User Details Interface methods */
	
	@Override
	@JsonIgnore
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isEnabled() {
		return true;
	}
	
	
}
