package com.algaworks.algafood.api.DTO.input;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeInput {
	
	@NotBlank
	private String nome;
	
	@Valid
	@NotNull
	private EstadoInput estado;
}