package com.cefalo.storyapi.models;

import java.time.LocalDate;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Email;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Pattern(message = "Not Valid. Please put valid name", regexp = "[A-Za-z\s]+")
	private String name;
	
	@Pattern(message = "Not Valid. Please put valid number", regexp = "[0][1][0-9]{9,10}")
	private String number;

	@Column(unique = true)
	@Email(message = "Not Valid. Please put valid email", regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", flags = Pattern.Flag.CASE_INSENSITIVE)
	private String email;
	
	@Pattern(message = "Not Valid. Please put valid password : must contain one capital letter, one small letter and one number", regexp = "(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[A-Za-z0-9@$!%*?#&]{8,}")
	private String password;
	
	private LocalDate created_Date = LocalDate.now();

	public User() {

	}

	public User(int id, String name, String number, String email, String password, LocalDate created_Date) {
		this.id = id;
		this.name = name;
		this.number = number;
		this.email = email;
		this.password = password;
		this.created_Date = created_Date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDate getCreated_Date() {
		return created_Date;
	}

	public void setCreated_Date(LocalDate created_Date) {
		this.created_Date = created_Date;
	}
}