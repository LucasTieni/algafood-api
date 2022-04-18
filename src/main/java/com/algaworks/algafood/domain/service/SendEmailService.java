package com.algaworks.algafood.domain.service;

import java.util.Map;
import java.util.Set;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;

public interface SendEmailService {
	
	void enviar(Message message);
	
	@Getter
	@Builder
	class Message{
		
		@Singular
		private Set<String> destinatarios;
		
		@NonNull
		private String assunto;
		
		@NonNull
		private String corpo;
		
		@Singular("variavel")
		private Map<String, Object> variaveis;
	}

}
