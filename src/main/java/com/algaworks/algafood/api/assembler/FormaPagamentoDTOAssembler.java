package com.algaworks.algafood.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.DTO.FormaPagamentoDTO;
import com.algaworks.algafood.domain.model.FormaPagamento;

@Component
public class FormaPagamentoDTOAssembler {
	@Autowired  
	private ModelMapper modelMapper;
	
	public FormaPagamentoDTO toModel(FormaPagamento formaPagamento) {
				return modelMapper.map(formaPagamento, FormaPagamentoDTO.class);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
	}
	
	public List<FormaPagamentoDTO> toCollectionDTO(Collection<FormaPagamento> formasPagamento){
		return formasPagamento.stream()
				.map(formaPagamento -> toModel(formaPagamento))
				.collect(Collectors.toList());
	}
}
