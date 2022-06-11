package com.algaworks.algafood.api.v1.DTO;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "estados")
@Getter
@Setter
public class EstadoDTO extends RepresentationModel<EstadoDTO>{
	
//	@ApiModelProperty(example = "1")
	private Long id;
	
//	@ApiModelProperty(example = "Minas Gerais")
	private String nome;
}
