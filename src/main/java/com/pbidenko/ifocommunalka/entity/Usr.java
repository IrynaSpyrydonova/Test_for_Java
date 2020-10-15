package com.pbidenko.ifocommunalka.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Usr {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "userid")
	private int id;

	@Column(name = "username", unique = true)
	private String usrname;

	@Column(name = "password")
	private String pwd;

	@Column(name = "email", unique = true)
	private String email;

	@Column(name = "login_status", columnDefinition = "boolean default false")
	private boolean logged;

	@Column(name = "enabled", columnDefinition = "boolean default true")
	private boolean enable;

	@Column(name = "locked", columnDefinition = "boolean default false")
	private boolean locked;

	@Column(name = "role")
	@Enumerated(EnumType.STRING)
	private Roles role;

	public Usr() {

	}

	public Usr(String usrname, String pwd, String email, boolean logged, boolean enable) {
		super();
		this.usrname = usrname;
		this.pwd = pwd;
		this.email = email;
		this.logged = logged;
		this.enable = enable;
	}

	public String getUsrname() {
		return usrname;
	}

	public void setUsrname(String usrname) {
		this.usrname = usrname;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isLogged() {
		return logged;
	}

	public void setLogged(boolean logged) {
		this.logged = logged;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public int getId() {
		return id;
	}

	public Roles getRole() {
		return role;
	}

	public void setRole(Roles role) {
		this.role = role;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

}
