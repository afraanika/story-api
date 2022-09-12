package com.cefalo.storyapi.models;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;

import javax.validation.constraints.Pattern;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "users")
public class User implements UserDetails{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotNull(message = "Null Name not accepted")
	@NotBlank(message = "Blank Name not accepted")
	@NotEmpty(message = "Empty Name not accepted")
	@Pattern(message = "Not Valid. Please put valid name", regexp = "^[A-Z][A-Za-z\s]*")
	private String name;
	
	@NotNull(message = "Null Number not accepted")
	@NotBlank(message = "Blank Number not accepted")
	@NotEmpty(message = "Empty Number not accepted")
	@Pattern(message = "Not Valid. Please put valid number", regexp = "[0][1][0-9]{9,10}")
	private String number;
	
	@NotNull(message = "Null Email not accepted")
	@NotBlank(message = "Blank Email not accepted")
	@NotEmpty(message = "Empty Email not accepted")
	@Column(unique = true)
	@Email(message = "Not Valid. Please put valid email", regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", flags = Pattern.Flag.CASE_INSENSITIVE)
	private String email;
	
	@NotNull(message = "Null Password not accepted")
	@NotBlank(message = "Blank Password not accepted")
	@NotEmpty(message = "Empty Password not accepted")
	private String password;
	
	private LocalDate created_Date = LocalDate.now();

	public User() {

	}

	public User(Integer id, String name, String number, String email, String password, LocalDate created_Date) {
		this.id = id;
		this.name = name;
		this.number = number;
		this.email = email;
		this.password = password;
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

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return new HashSet<GrantedAuthority>();
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}