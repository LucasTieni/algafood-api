package com.algaworks.algafood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {
	DADOS_INVALIDOS("dados-invalidos", "Dados Inválidos"),
	ERRO_DE_SISTEMA("erro-de-sistema", "Erro de sistema"),
	PARAMETRO_INVALIDO("parametro-invalido", "parâmetro inválido"),
	MENSAGEM_INCOMPREENSIVEL("mensagem-incompreensivel", "Mensagem incompreensível"),
	RECURSO_NAO_ENCONTRADA("recurso-nao-encontrada", "Recurso não encontrado"),
	ENTIDADE_EM_USO("entidade_em_uso", "Entidade em uso"),
	ERRO_NEGOCIO("erro_negocio", "Violação de regra de negócio"),
	ACESSO_NEGADO("/acesso-negado", "Acesso Negado");
	
	private String title;
	private String uri;
	
	ProblemType(String path, String title){
		this.uri = "https://algaworks.com.br/" + path;
		this.title = title;
	}
	
}
