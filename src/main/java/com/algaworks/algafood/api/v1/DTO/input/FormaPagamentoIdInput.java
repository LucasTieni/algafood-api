package com.algaworks.algafood.api.v1.DTO.input;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormaPagamentoIdInput {
	
	@NotNull
	private Long id;
}
