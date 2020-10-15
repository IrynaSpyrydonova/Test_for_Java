package com.pbidenko.ifocommunalka.controller_rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pbidenko.ifocommunalka.entity.Usr;
import com.pbidenko.ifocommunalka.repository.UsrRepository;

@RestController
public class Test {
	
	@Autowired
	UsrRepository usrRepository;

	@GetMapping("/users")
	public List<Usr> getUsers() {
		List<Usr> res = new ArrayList<Usr>();
		
		usrRepository.findAll().forEach(item->res.add(item));
		return res;
	}
	
	
}
