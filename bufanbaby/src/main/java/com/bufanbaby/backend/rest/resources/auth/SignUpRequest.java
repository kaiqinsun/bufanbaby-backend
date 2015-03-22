package com.bufanbaby.backend.rest.resources.auth;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

public class SignUpRequest {

	private String firstName;

	private String lastName;

	@NotNull
	private String username;

	@Email
	@NotNull
	private String email;

	@Length(min = 8, max = 30)
	@NotNull
	private String password;

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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return String.format(
				"SignUpRequest [firstName=%s, lastName=%s, username=%s, email=%s, password=%s]",
				firstName, lastName, username, email, password);
	}
}
