package com.pbidenko.ifocommunalka.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.pbidenko.ifocommunalka.entity.Roles;
import com.pbidenko.ifocommunalka.entity.Usr;
import com.pbidenko.ifocommunalka.repository.UsrRepository;

@Service
public class UsrStorageService {

	@Autowired
	private UsrRepository usrRepository;

	private Logger logger = LoggerFactory.getLogger(UsrStorageService.class);

	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	public Usr saveNewUser(Usr usr) {

		usr.setPwd(passwordEncoder.encode(usr.getPwd()));

		usr.setEnable(true);
		usr.setRole(Roles.USER);
		usr.setLogged(true);

		try {
			return usrRepository.save(usr);
		} catch (DataIntegrityViolationException exp) {
			logger.info(exp.getClass().getName() + ": " + exp.getMessage());
			return null;
		}
	}

	public boolean emailExists(String usremail) {

		if (usrRepository.findByEmail(usremail) != null)
			return true;
		
		return false;
	}
	
	public boolean usrnameExists(String usrname) {

		if (usrRepository.findByUsrname(usrname) != null)
			return true;
		
		return false;
	}

}
