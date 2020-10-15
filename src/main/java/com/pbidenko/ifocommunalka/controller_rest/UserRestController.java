package com.pbidenko.ifocommunalka.controller_rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pbidenko.ifocommunalka.entity.Usr;
import com.pbidenko.ifocommunalka.service.UsrStorageService;

@RestController
public class UserRestController {

	private Map<String, Usr> res;

	@Autowired
	UsrStorageService usrStorageService;

	@PostMapping(value = "save_user", consumes = "application/json", produces = "application/json")
	public Map<String, Usr> saveuser(@RequestBody Usr usr) {

		res = new HashMap<String, Usr>();

		res.put("usr", usrStorageService.saveNewUser(usr));

		return res;

	}
	
	@GetMapping(value="email_exists")
	public boolean checkUsrEmail(@RequestParam String usremail) {	
		return usrStorageService.emailExists(usremail) ;
	}
	
	@GetMapping(value="usr_exists")
	public boolean checkUsrName(@RequestParam String usrname) {
		return usrStorageService.usrnameExists(usrname);
	}

}
