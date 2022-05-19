package com.algaworks.algafood.api.v1.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.DTO.FotoProdutoDTO;
import com.algaworks.algafood.api.v1.controller.RestauranteProdutoFotoController;
import com.algaworks.algafood.domain.model.FotoProduto;

@Component
public class FotoProdutoDTOAssembler 
	extends RepresentationModelAssemblerSupport<FotoProduto, FotoProdutoDTO>{
	
	@Autowired  
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	public FotoProdutoDTOAssembler() {
		super(RestauranteProdutoFotoController.class, FotoProdutoDTO.class);
	}
	
	public FotoProdutoDTO toModel(FotoProduto fotoProduto) {
//		FotoProdutoDTO fotoProdutoDTO = createModelWithId(fotoProduto.getId(), fotoProduto);
		
		FotoProdutoDTO fotoProdutoDTO = modelMapper.map(fotoProduto, FotoProdutoDTO.class);
				
		fotoProdutoDTO.add(algaLinks.linkToRestauranteProdutoFoto(fotoProduto.getRestauranteId(),
				fotoProduto.getProduto().getId()));
		
		fotoProdutoDTO.add(algaLinks.linkToProduto(fotoProduto.getRestauranteId(),
				fotoProduto.getProduto().getId(), "produto"));
		
		return fotoProdutoDTO;
	}
	
	public List<FotoProdutoDTO> toCollectionDTO(List<FotoProduto> fotoProdutos){
		return fotoProdutos.stream()
				.map(fotoProduto -> toModel(fotoProduto))
				.collect(Collectors.toList());
	}
}
