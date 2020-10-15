package com.pbidenko.ifocommunalka.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/create_account").setViewName("create_account.html");
		registry.addViewController("/profile_foto").setViewName("profile_foto.html");
		registry.addViewController("/filing_profile.html").setViewName("filing_profile.html");
		registry.addViewController("/create_password").setViewName("create_password.html");
		
		}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**");
	}

}
