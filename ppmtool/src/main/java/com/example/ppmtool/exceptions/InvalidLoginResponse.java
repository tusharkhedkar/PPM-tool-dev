package com.example.ppmtool.exceptions;

public class InvalidLoginResponse {

	private String username;
	private String password;

	public InvalidLoginResponse() {
		this.username = "INVALID USERNAME";
		this.password = "INVALID PASSWORD";
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
