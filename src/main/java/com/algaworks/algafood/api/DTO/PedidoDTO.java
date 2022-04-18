package com.algaworks.algafood.api.DTO;


import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PedidoDTO {
	
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
	private RestauranteResumoDTO restaurante;
	private FormaPagamentoDTO formaPagamento;
	private EnderecoDTO enderecoEntrega;
	private List<ItemPedidoDTO> itens;	
}




