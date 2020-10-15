package com.pbidenko.ifocommunalka.controller_rest;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pbidenko.ifocommunalka.entity.EmailConfirmation;
import com.pbidenko.ifocommunalka.entity.LoginToken;
import com.pbidenko.ifocommunalka.entity.Mail;
import com.pbidenko.ifocommunalka.entity.ConfirmationCode;
import com.pbidenko.ifocommunalka.repository.TokenRepository;
import com.pbidenko.ifocommunalka.service.EmailService;

@RestController
public class EmaiRestlController {

	@Autowired
	private TokenRepository tokenRepository;

	@Autowired
	private EmailService emailService;

	@PostMapping(value = "/email_confirmation", consumes="application/json")
	public Map<String, String> processEmailForLoginConfirmation(@RequestBody EmailConfirmation entryForm,
			HttpServletRequest request) {

		Map<String, String> res = new HashMap<String, String>();

		LoginToken loginToken = new LoginToken();

		loginToken.setToken(UUID.randomUUID().toString());
		loginToken.setExpireDate(60);
		loginToken.setUsr(entryForm.getUsr());

		tokenRepository.save(loginToken);

		Mail mail = new Mail();
		mail.setFrom("pavelbidenko2018@gmail.com");
		mail.setTo(entryForm.getEmail());

		mail.setSubject("IfoCommunalka Login Confirmation");

		Map<String, Object> model = new HashMap<>();

		model.put("token", loginToken.getToken());
		model.put("user", loginToken.getUsr());
		model.put("signature", "https://ifocommunalka.be");

		mail.setModel(model);

		if (emailService.sendMailToUser(mail)) {
			res.put("status", "success");
			return res;
		}

		res.put("status", "failure");
		return res;

	}

	@PostMapping(value = "/confirm_my_login", produces = "application/json")
	public Map<String, String> processUUIDConfirmation(@RequestBody ConfirmationCode uuidString) {

		Map<String, String> res = new HashMap<String, String>();

		LoginToken loginToken = tokenRepository.findByToken(uuidString.getCode());

		if (loginToken == null) {
			res.put("response", "Confirmation string is not valid");
			return res;
		}

		if (loginToken.isUsed()) {
			res.put("response", "Confirmation string was already used");
			return res;
		}

		loginToken.setUsed(true);
		tokenRepository.save(loginToken);

		res.put("response", "Confirmed OK");
		return res;
	}

	@GetMapping("/reset-password")
	public String displayResetPasswordPage(@RequestParam(required = false) String token, Model model) {

		LoginToken resetToken = tokenRepository.findByToken(token);

		if (resetToken == null) {
			model.addAttribute("error", "Could not find password reset token.");
		} else if (resetToken.isExpired()) {
			model.addAttribute("error", "Token has expired, please request a new password reset.");
		} else {
			model.addAttribute("token", resetToken.getToken());
		}

		return "reset-password";
	}

}
