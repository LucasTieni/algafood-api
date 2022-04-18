package com.algaworks.algafood.infrastructure.service.email;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FakeSendEmailService extends SmtpSendEmailService{

	@Override
	public void enviar(Message message) {
		String corpo = processTemplate(message);
		
		log.info("[FAKE EMAIL] para: {}\n{}", message.getDestinatarios(),corpo);
	}

}
