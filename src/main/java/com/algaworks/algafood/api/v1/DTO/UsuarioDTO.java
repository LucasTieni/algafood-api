package com.algaworks.algafood.api.v1.DTO;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "usuarios")
@Setter
@Getter
public class UsuarioDTO extends RepresentationModel<UsuarioDTO>{

	private Long id;
	private String nome;
	private String email;
//	private String senha;
//	private OffsetDateTime dataCadastro;
//	private List<Grupo> grupos = new ArrayList<>();
}
