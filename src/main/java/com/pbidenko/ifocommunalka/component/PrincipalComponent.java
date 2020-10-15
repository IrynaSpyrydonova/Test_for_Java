package com.pbidenko.ifocommunalka.component;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;


public class PrincipalComponent {

	private String usrname;

	public PrincipalComponent() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			this.usrname = ((UserDetails) principal).getUsername();
		} else {
			this.usrname = principal.toString();
		}
	}

	public String getUsrname() {
		return usrname;
	}

	public void setUsrname(String usrname) {
		this.usrname = usrname;
	}
	
	

}
