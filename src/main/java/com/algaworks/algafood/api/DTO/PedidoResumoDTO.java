package com.algaworks.algafood.api.DTO;


import java.math.BigDecimal;
import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;

//@JsonFilter("pedidoFilter")
@Setter
@Getter
public class PedidoResumoDTO {
	
	private String codigo;
	private BigDecimal subtotal;
	private BigDecimal taxaFrete;
	private BigDecimal valorTotal;
	private String status;
	private OffsetDateTime dataCriacao;
//	private UsuarioDTO cliente;
	private String nomeCliente;
	private RestauranteResumoDTO restaurante;
}




