package com.cefalo.storyapi.dto;

import java.time.LocalDate;

public class UserDTO {

	private Integer id;
	
	private String name;

	private String number;

	private String email;
	
	private LocalDate created_Date;
	
	public UserDTO() {
		
	}
	
	public UserDTO(Integer id, String name, String number, String email) {
		this.id = id;
		this.name = name;
		this.number = number;
		this.email = email;
	}
	
	public UserDTO(Integer id, String name, String number, String email, LocalDate created_Date) {
		this.id = id;
		this.name = name;
		this.number = number;
		this.email = email;
		this.created_Date = created_Date;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getCreated_Date() {
		return created_Date;
	}

	public void setCreated_Date(LocalDate created_Date) {
		this.created_Date = created_Date;
	} 
}
