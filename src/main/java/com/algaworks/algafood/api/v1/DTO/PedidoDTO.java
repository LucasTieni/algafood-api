package com.algaworks.algafood.api.v1.DTO;


import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "pedidos")
@Setter
@Getter
public class PedidoDTO extends RepresentationModel<PedidoResumoDTO>{
	
	private String codigo;
	private BigDecimal subtotal;
	private BigDecimal taxaFrete;
	private BigDecimal valorTotal;
	private String status;
	private OffsetDateTime dataCriacao;
	private OffsetDateTime dataConfirmacao;
	private OffsetDateTime dataCancelamento;
	private OffsetDateTime dataEntrega;
	private UsuarioDTO cliente;
	private RestauranteApenasNomeDTO restaurante;
	private FormaPagamentoDTO formaPagamento;
	private EnderecoDTO enderecoEntrega;
	private List<ItemPedidoDTO> itens;	
}




