package com.algaworks.algafood.api.v1.DTO;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "cidades")
@Getter
@Setter
public class CidadeDTO extends RepresentationModel<CidadeDTO>{
	
//	@ApiModelProperty(example = "1")
	private Long id;
	
//	@ApiModelProperty(value = "Nome da cidade", example = "Uberlandia")
	private String nome;
	
	private EstadoDTO estado;
}
