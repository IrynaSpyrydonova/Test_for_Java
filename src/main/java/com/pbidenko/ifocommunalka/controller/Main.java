package com.pbidenko.ifocommunalka.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Main {
	
	@GetMapping("/")
	public String index() {	
		return "index";
	}
	
	@GetMapping("/profile")
	public String profile() {	
		return "profile";
	}

	@GetMapping("/login")
	public String login() {	
		return "login";
	}
	
}
