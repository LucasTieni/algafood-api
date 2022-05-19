package com.algaworks.algafood.api.v1.DTO;


import java.math.BigDecimal;
import java.time.OffsetDateTime;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "Resumo pedidos")
@Setter
@Getter
public class PedidoResumoDTO extends RepresentationModel<PedidoResumoDTO>{
	
	private String codigo;
	private BigDecimal subtotal;
	private BigDecimal taxaFrete;
	private BigDecimal valorTotal;
	private String status;
	private OffsetDateTime dataCriacao;
//	private UsuarioDTO cliente;
	private String nomeCliente;
	private RestauranteApenasNomeDTO restaurante;
}




