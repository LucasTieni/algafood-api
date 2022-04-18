package com.algaworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.DTO.FotoProdutoDTO;
import com.algaworks.algafood.domain.model.FotoProduto;

@Component
public class FotoProdutoDTOAssembler {
	
	@Autowired  
	private ModelMapper modelMapper;
	
	public FotoProdutoDTO toModel(FotoProduto fotoProduto) {
		return modelMapper.map(fotoProduto, FotoProdutoDTO.class);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
	}
	
	public List<FotoProdutoDTO> toCollectionDTO(List<FotoProduto> fotoProdutos){
		return fotoProdutos.stream()
				.map(fotoProduto -> toModel(fotoProduto))
				.collect(Collectors.toList());
	}
}
