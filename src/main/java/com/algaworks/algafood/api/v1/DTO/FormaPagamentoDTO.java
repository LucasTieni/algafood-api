package com.algaworks.algafood.api.v1.DTO;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "formasPagamento")
@Setter
@Getter
public class FormaPagamentoDTO extends RepresentationModel<PedidoResumoDTO>{
	
	private Long id;
	private String descricao;
}
