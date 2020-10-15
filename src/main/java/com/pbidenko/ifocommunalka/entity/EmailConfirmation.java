package com.pbidenko.ifocommunalka.entity;

public class EmailConfirmation {

	private String usr;
	private String email;

	public EmailConfirmation() {
		super();
	}

	public EmailConfirmation(String usr, String email) {
		super();
		this.usr = usr;
		this.email = email;
	}

	public String getUsr() {
		return usr;
	}

	public void setUsr(String usr) {
		this.usr = usr;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
