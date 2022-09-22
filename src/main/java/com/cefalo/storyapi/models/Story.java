package com.cefalo.storyapi.models;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "stories")
public class Story {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull(message = "Null Title no accepted")
	@NotBlank(message = "Blank Title no accepted")
	@NotEmpty(message = "Empty Title no accepted")
	private String tittle;
	
	@NotNull(message = "Null Description no accepted")
	@NotBlank(message = "Blank Description no accepted")
	@NotEmpty(message = "Empty Description no accepted")
	@Column(columnDefinition="TEXT")
	private String description;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	private LocalDate created_Date = LocalDate.now();
	
	public Story() {
		
	}
	
	public Story(Integer id, String tittle, String description, User user) {
		this.id = id;
		this.tittle = tittle;
		this.user = user;
		this.description = description;
	}

	public Story(Integer id, @NotNull @NotBlank @NotEmpty String tittle, @NotNull @NotBlank @NotEmpty String description,
			User user, LocalDate created_Date) {
		super();
		this.id = id;
		this.tittle = tittle;
		this.description = description;
		this.user = user;
		this.created_Date = created_Date;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTittle() {
		return tittle;
	}

	public void setTittle(String tittle) {
		this.tittle = tittle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public LocalDate getCreated_Date() {
		return created_Date;
	}

	public void setCreated_Date(LocalDate created_Date) {
		this.created_Date = created_Date;
	}
}
