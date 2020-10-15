package com.pbidenko.ifocommunalka.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pbidenko.ifocommunalka.entity.Usr;
import com.pbidenko.ifocommunalka.repository.UsrRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	private Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
		
	@Autowired
	UsrRepository usrRepository;

	@Override
	public UserDetails loadUserByUsername(String loginString) throws UsernameNotFoundException {

		Usr usr = usrRepository.findByUsrname(loginString);

		if (usr == null) {
			usr = usrRepository.findByEmail(loginString);
		}
		
		if (usr==null ) {
			logger.info("user not found in db");
			
		}

		return UserDetailsImpl(usr);
	}

	private UserDetails UserDetailsImpl(Usr usr) {
		if(usr == null) {
			return new UserDetailsImpl();
		}
		return new UserDetailsImpl(usr);
	}


}
