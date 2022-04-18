package com.algaworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.DTO.PedidoDTO;
import com.algaworks.algafood.domain.model.Pedido;

@Component
public class PedidoDTOAssembler {

	@Autowired  
	private ModelMapper modelMapper;
	
	public PedidoDTO toModel(Pedido pedido) {
				return modelMapper.map(pedido, PedidoDTO.class);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
	}
	
	public List<PedidoDTO> toCollectionDTO(List<Pedido> pedidos){
		return pedidos.stream()
				.map(pedido -> toModel(pedido))
				.collect(Collectors.toList());
	}
}
