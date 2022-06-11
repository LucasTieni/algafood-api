package com.algaworks.algafood.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Getter;

//@ApiModel("Problema")
@JsonInclude(Include.NON_NULL)
@Getter
@Builder
public class Problem {
	
//	@ApiModelProperty(example = "400", position = 1)
	private Integer status;
	
//	@ApiModelProperty(example = "https://algaworks.com.br/mensagem-incompreensivel")
	private String type;
	
//	@ApiModelProperty(example = "Mensagem incompreensível")
	private String title;
	
//	@ApiModelProperty(example = "O corpo da requisição está inválido. Verifique erro de sintaxe")
	private String detail;
	
//	@ApiModelProperty(example = "Ocorreu um erro interno inesperado no sistema. Tente novamente e se o problema persistir, entre em contato com o administrador do sistema.")
	private String userMessage;
	
//	@ApiModelProperty(example = "2022-04-22T20:39:51.5262259Z")
	private OffsetDateTime timestamp;
	
//	@ApiModelProperty("Lista de objetos ou campos que geraram erros")
	private List<Object> objects;
	
//	@ApiModel("Objeto problema")
	@Getter
	@Builder
	public static class Object{
		
//		@ApiModelProperty(example = "preco")
		private String name;
		
//		@ApiModelProperty(example = "O preço é obrigatório")
		private String userMessage;
	}
}
