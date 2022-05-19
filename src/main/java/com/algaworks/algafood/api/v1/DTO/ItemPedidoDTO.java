package com.algaworks.algafood.api.v1.DTO;


import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "Itens pedido")
@Setter
@Getter
public class ItemPedidoDTO extends RepresentationModel<PedidoResumoDTO>{
	
	private Long produtoId;
	private String produtoNome;
	private Integer quantidade;
	private BigDecimal precoUnitario;
	private BigDecimal precoTotal;
	private String observacao;
}
