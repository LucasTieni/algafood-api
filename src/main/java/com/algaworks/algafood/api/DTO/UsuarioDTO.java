package com.algaworks.algafood.api.DTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UsuarioDTO {

	private Long id;
	private String nome;
	private String email;
//	private String senha;
//	private OffsetDateTime dataCadastro;
//	private List<Grupo> grupos = new ArrayList<>();
}
