package com.pbidenko.ifocommunalka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.pbidenko.ifocommunalka.component.PrincipalComponent;
import com.pbidenko.ifocommunalka.entity.UsrProfile;
import com.pbidenko.ifocommunalka.service.ProfileStorageService;

@Controller
public class Profile {

	@Autowired
	ProfileStorageService profileStorageService;

	@GetMapping("/my_profile")
	public String profile(Model model) {
		
		String usrname = new PrincipalComponent().getUsrname();
		
		UsrProfile profile = profileStorageService.findByUsr(usrname);

		if (profile == null) {
			return "profile_form";
		}

		String occupationString = String.join(", ", profile.getOccupations());
		String interestString = String.join(", ", profile.getInterests());
				
		model.addAttribute("profile", profile);
		model.addAttribute("occupations",occupationString);
		model.addAttribute("interestString",interestString);
		
		return "my_profile";

	}

}
