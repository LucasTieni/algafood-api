package com.algaworks.algafood.api.DTO;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "cidades")
@Getter
@Setter
public class CidadeResumoDTO extends RepresentationModel<CidadeResumoDTO>{
	
	private Long id;
	private String nome;
	private String estado;
}
