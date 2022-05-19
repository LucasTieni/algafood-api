package com.algaworks.algafood.api.v1.DTO.input;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CozinhaIdInput {
	
	@NotNull
	private Long id;
}
