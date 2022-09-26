package com.cefalo.storyapi.dto;

import com.cefalo.storyapi.models.Story;

import java.time.LocalDate;
import java.util.Objects;

public class StoryDTO {
	
	private Integer id;

	private String tittle;
	
	private String description;
	
	private String authorName;
	
	private LocalDate created_Date;

	public StoryDTO() {
		
	}
	
	public StoryDTO(Integer id, String tittle, String description, String authorName) {
		this.id = id;
		this.tittle = tittle;
		this.description = description;
		this.authorName = authorName;
	}
	
	public StoryDTO(Integer id, String tittle, String description, String authorName, LocalDate created_Date) {
		super();
		this.id = id;
		this.tittle = tittle;
		this.description = description;
		this.authorName = authorName;
		this.created_Date = created_Date;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		StoryDTO story = (StoryDTO) o;
		return id.equals(story.id) && authorName.equals(story.authorName);
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + Objects.hashCode(id);
		result = 31 * result + Objects.hashCode(authorName);
		return result;
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

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public LocalDate getCreated_Date() {
		return created_Date;
	}

	public void setCreated_Date(LocalDate created_Date) {
		this.created_Date = created_Date;
	}

}
