package com.algaworks.algafood.domain.service;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.repository.PedidoRepository;

@Service
public class FluxoPedidoService {

	@Autowired
	private EmitePedidoService emitePedido;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Transactional
	public void confirmar(String codigoPedido) {
		Pedido pedido = emitePedido.buscarOuFalhar(codigoPedido);
		pedido.confirm();
	}
	
	@Transactional
	public void entrega(String codigoPedido) {
		Pedido pedido = emitePedido.buscarOuFalhar(codigoPedido);
		pedido.delivery();
		
		pedidoRepository.save(pedido);
	}
	
	@Transactional
	public void cancelar(String codigoPedido) {
		Pedido pedido = emitePedido.buscarOuFalhar(codigoPedido);
		pedido.cancel();
	}
	
	
}



