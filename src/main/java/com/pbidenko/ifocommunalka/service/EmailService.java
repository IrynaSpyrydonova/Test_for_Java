package com.pbidenko.ifocommunalka.service;

import java.nio.charset.StandardCharsets;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.pbidenko.ifocommunalka.entity.Mail;

@Service
public class EmailService {
	
	private Logger log = LoggerFactory.getLogger(EmailService.class);
	
	@Autowired
    private JavaMailSender javaMailSender;
    
    @Autowired
    private SpringTemplateEngine templateEngine;

   public boolean sendMailToUser(Mail mail) {
	   
	   	boolean status = false;
	   	
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

            Context context = new Context();
            context.setVariables(mail.getModel());
            String html = templateEngine.process("email/loginconfirmation_tmp", context);

            helper.setTo(mail.getTo());
            helper.setText(html, true);
            helper.setSubject(mail.getSubject());
            helper.setFrom(mail.getFrom());	

            javaMailSender.send(message);
            status = true;
            return status;
        } catch (Exception e){
        	log.error("Email was not sent: " + e.getMessage());
        	return status;
            
        }
    }

}