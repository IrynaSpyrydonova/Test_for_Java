package com.pbidenko.ifocommunalka.controller_rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pbidenko.ifocommunalka.entity.UsrProfile;
import com.pbidenko.ifocommunalka.service.ProfileStorageService;

@RestController
public class ProfileRestController {

	@Autowired
	ProfileStorageService profileStorageService;

	@PostMapping(value = "/save_profile", consumes = "application/json")
	public Map<String, String> saveProfile(@RequestBody UsrProfile profileObj) {

		return profileStorageService.save(profileObj);

	}

}
