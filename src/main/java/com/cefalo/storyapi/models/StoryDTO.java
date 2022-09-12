package com.cefalo.storyapi.models;

import java.time.LocalDate;

public class StoryDTO {
	
	private Integer id;

	private String tittle;
	
	private String description;
	
	private UserDTO author;
	
	private LocalDate created_Date;

	public StoryDTO() {
		
	}
	
	public StoryDTO(Integer id, String tittle, String description, UserDTO userDTO, LocalDate created_Date) {
		super();
		this.id = id;
		this.tittle = tittle;
		this.description = description;
		this.author = userDTO;
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

	public UserDTO getAuthor() {
		return author;
	}

	public void setAuthor(UserDTO userDTO) {
		this.author = userDTO;
	}

	public LocalDate getCreated_Date() {
		return created_Date;
	}

	public void setCreated_Date(LocalDate created_Date) {
		this.created_Date = created_Date;
	}

}
