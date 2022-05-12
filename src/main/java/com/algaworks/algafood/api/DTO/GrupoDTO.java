package com.algaworks.algafood.api.DTO;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "grupos")
@Getter
@Setter
public class GrupoDTO extends RepresentationModel<GrupoDTO>{
	
	private Long id;
	private String nome;
}

