package com.algaworks.algafood.core.email;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.Setter;

@Validated
@Getter
@Setter
@Component
@ConfigurationProperties("algafood.email")
public class EmailProperties {
	
	@NotNull
	private String remetente;
	
	private Implementacao impl = Implementacao.FAKE;
	
	private SandBox sandBox = new SandBox();
	
	public enum Implementacao{
		SMTP, FAKE, SANDBOX
	}
	
	@Getter
	@Setter
	public class SandBox{
		private String destinatario;
	}
	
}
