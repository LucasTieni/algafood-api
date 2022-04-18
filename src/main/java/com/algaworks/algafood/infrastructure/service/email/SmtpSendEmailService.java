package com.algaworks.algafood.infrastructure.service.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.algaworks.algafood.core.email.EmailProperties;
import com.algaworks.algafood.domain.service.SendEmailService;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class SmtpSendEmailService implements SendEmailService{

	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private EmailProperties emailProperties;
	
	@Autowired
	private Configuration freemarkerConfig;
	
	@Override
	public void enviar(Message message) {
		try {
			MimeMessage mimeMessage = createMimeMessage(message);
			
			System.out.println("SMPT enviar");
			mailSender.send(mimeMessage);
			
		} catch (Exception e) {
			throw new EmailException("Não foi possível enviar e-mail", e);
		}
	}

	protected MimeMessage createMimeMessage(Message message) throws MessagingException {
		String body = processTemplate(message);
		
		MimeMessage mimeMessage = mailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
		helper.setFrom(emailProperties.getRemetente());
		helper.setTo(message.getDestinatarios().toArray(new String[0]));
		helper.setSubject(message.getAssunto());
		helper.setText(body, true);
		
		return mimeMessage;
	}
	
	protected String processTemplate(Message message) {
		try {
			Template template = freemarkerConfig.getTemplate(message.getCorpo());
			
			return FreeMarkerTemplateUtils.processTemplateIntoString(template, 
					message.getVariaveis());
			
		} catch (Exception e) {
			throw new EmailException("Não foi possível importar o template do email.", e);
		
		}
	}
	
}
