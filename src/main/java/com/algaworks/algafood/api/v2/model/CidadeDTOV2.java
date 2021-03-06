package com.algaworks.algafood.api.v2.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "cidades")
@Getter
@Setter
public class CidadeDTOV2 extends RepresentationModel<CidadeDTOV2>{
	
//	@ApiModelProperty(example = "1")
	private Long idCidade;
	
//	@ApiModelProperty(value = "Nome da cidade", example = "Uberlandia")
	private String nomeCidade;
	
	private Long idEstado;
	private String nomeEstado;
}
