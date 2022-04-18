package com.algaworks.algafood.infrastructure.service.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algaworks.algafood.core.email.EmailProperties;
import com.algaworks.algafood.domain.service.SendEmailService;

@Configuration
public class EmailConfig {

		@Autowired
		private EmailProperties emailProperties;
		
		@Bean
		public SendEmailService sendEmailService() {
			
			switch (emailProperties.getImpl()) {
			case FAKE:
				return new FakeSendEmailService();
			case SMTP:
				return new SmtpSendEmailService();
			case SANDBOX:
				return new SandBoxSendEmailService();				
			default:
				return null;
			}
		}
}
