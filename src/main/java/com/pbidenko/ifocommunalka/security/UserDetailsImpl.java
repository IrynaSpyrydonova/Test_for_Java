package com.pbidenko.ifocommunalka.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.pbidenko.ifocommunalka.entity.Roles;
import com.pbidenko.ifocommunalka.entity.Usr;

public class UserDetailsImpl implements UserDetails {

	private static final long serialVersionUID = 1L;

	private String username;
	private String password;
	private boolean enabled;

	private String email;

	public UserDetailsImpl(Usr usr) {
		this.username = usr.getUsrname();
		this.password = usr.getPwd();
		this.enabled = usr.isEnable();
		this.email = usr.getEmail();
	}

	public UserDetailsImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(Roles.ADMIN.toString(), Roles.USER.toString()).stream()
				.map(item -> new SimpleGrantedAuthority(item)).collect(Collectors.toSet());
	}

	@Override
	public String getPassword() {

		return password;
	}

	@Override
	public String getUsername() {

		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {

		return enabled;
	}

	public String getEmail() {
		return email;
	}

}
