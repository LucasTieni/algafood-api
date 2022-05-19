package com.algaworks.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.DTO.PedidoDTO;
import com.algaworks.algafood.api.v1.controller.PedidoController;
import com.algaworks.algafood.domain.model.Pedido;

@Component
public class PedidoDTOAssembler 
	extends RepresentationModelAssemblerSupport<Pedido, PedidoDTO>{

	@Autowired  
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	public PedidoDTOAssembler() {
		super(PedidoController.class, PedidoDTO.class);
	}
	
	@Override
	public PedidoDTO toModel(Pedido pedido) {
		PedidoDTO pedidoDTO = createModelWithId(pedido.getCodigo(), pedido);
		modelMapper.map(pedido, pedidoDTO);
		
		pedidoDTO.add(algaLinks.linkToPedidos());
		
		if (pedido.podeSerConfirmado()){
			pedidoDTO.add(algaLinks.linkToConfirmacaoPedido(pedido.getCodigo(), "confirmar"));
			System.out.println("pedido pode ser confirmado");
		}
		
		if (pedido.podeSerCancelado()){
			pedidoDTO.add(algaLinks.linkToCancelamentoPedido(pedido.getCodigo(), "cancelar"));
			System.out.println("pedido pode ser cancelado");
		}
		
		if (pedido.podeSerEntregue()){
			pedidoDTO.add(algaLinks.linkToEntregaPedido(pedido.getCodigo(), "entregar"));
			System.out.println("pedido pode ser entregue");
		}
		
		
		pedidoDTO.getRestaurante().add(algaLinks.linkToRestaurante(pedido.getRestaurante().getId()));
		
		pedidoDTO.getCliente().add(algaLinks.linkToUsuario(pedido.getCliente().getId()));

		pedidoDTO.getFormaPagamento().add(algaLinks.linkToFormaPagamento(pedido.getFormaPagamento().getId()));
		
		pedidoDTO.getEnderecoEntrega().getCidade().add(algaLinks.linkToCidade(pedido.getEnderecoEntrega().getCidade().getId()));
		
		pedidoDTO.getItens().forEach(item -> {
			item.add(algaLinks.linkToProduto(pedido.getRestaurante().getId(), item.getProdutoId(), "produto"));
		});
		
		return pedidoDTO;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
	}
}
