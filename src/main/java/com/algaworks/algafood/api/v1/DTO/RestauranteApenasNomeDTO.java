package com.algaworks.algafood.api.v1.DTO;


import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "restaurantes")
@Setter
@Getter
public class RestauranteApenasNomeDTO extends RepresentationModel<RestauranteApenasNomeDTO>{
	
//	@ApiModelProperty(example = "1")
	private Long id;
	
//	@ApiModelProperty(example = "Thai Gourmet")
	private String nome;
	
}
