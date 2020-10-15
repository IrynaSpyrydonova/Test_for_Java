package com.pbidenko.ifocommunalka.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "usr_profile")
public class UsrProfile {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String fullname;
	
	private String country;
	
	private String gender;
	
	private String city;
	
	private String gsm;
	
	private String userpic;
	
	@ElementCollection(fetch=FetchType.EAGER)
	private Set<String> occupations;
	
	@ElementCollection(fetch=FetchType.EAGER)
	private Set<String> interests;
	
	@ElementCollection(fetch=FetchType.EAGER)
	private Set<String> attachements;
	
	@Column(length=3000)
	private String description;

	@OneToOne
	@JoinColumn(name = "usrId",unique = true, nullable = false)
	private Usr usr;

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getGsm() {
		return gsm;
	}

	public void setGsm(String gsm) {
		this.gsm = gsm;
	}

	public String getUserpic() {
		return userpic;
	}

	public void setUserpic(String userpic) {
		this.userpic = userpic;
	}

	public Set<String> getOccupations() {
		return occupations;
	}

	public void setOccupations(Set<String> occupations) {
		this.occupations = occupations;
	}

	public Set<String> getInterests() {
		return interests;
	}

	public void setInterests(Set<String> interests) {
		this.interests = interests;
	}

	public Set<String> getAttachements() {
		return attachements;
	}

	public void setAttachements(Set<String> attachements) {
		this.attachements = attachements;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Usr getAuth_user() {
		return usr;
	}

	public void setAuth_user(Usr auth_user) {
		this.usr = auth_user;
	}

	public UsrProfile() {
		super();
	}

	public String getGender() {
		return gender;
	}

	public void setSex(String gender) {
		this.gender = gender;
	}

	public UsrProfile(String fullname, String country, String sex, String city, String gsm, String userpic,
			Set<String> occupations, Set<String> interests, Set<String> attachements, String description,
			Usr auth_user) {
		super();
		this.fullname = fullname;
		this.country = country;
		this.gender = sex;
		this.city = city;
		this.gsm = gsm;
		this.userpic = userpic;
		this.occupations = occupations;
		this.interests = interests;
		this.attachements = attachements;
		this.description = description;
		this.usr = auth_user;
	}

	public int getId() {
		return id;
	}

	public Usr getUsr() {
		return usr;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	
}

	