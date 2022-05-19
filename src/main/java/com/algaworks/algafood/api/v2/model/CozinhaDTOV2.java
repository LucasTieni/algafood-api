package com.algaworks.algafood.api.v2.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "cozinhas")
@Setter
@Getter
public class CozinhaDTOV2 extends RepresentationModel<CozinhaDTOV2>{
	
//	@JsonView(RestauranteView.Resumo.class)
	private Long idCozinha;
	
//	@JsonView(RestauranteView.Resumo.class)
	private String nomeCozinha;
}
