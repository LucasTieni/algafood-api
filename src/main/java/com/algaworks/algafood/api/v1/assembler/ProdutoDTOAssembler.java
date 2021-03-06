package com.algaworks.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.DTO.ProdutoDTO;
import com.algaworks.algafood.api.v1.controller.RestauranteProdutoController;
import com.algaworks.algafood.domain.model.Produto;

@Component
public class ProdutoDTOAssembler 
	extends RepresentationModelAssemblerSupport<Produto, ProdutoDTO>{

	@Autowired  
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	public ProdutoDTOAssembler() {
		super(RestauranteProdutoController.class, ProdutoDTO.class);
	}
	
	public ProdutoDTO toModel(Produto produto) {
		ProdutoDTO produtoDTO = createModelWithId(produto.getId(), produto, produto.getRestaurante().getId());
		
		modelMapper.map(produto, produtoDTO);
		
		produtoDTO.add(algaLinks.linkToProdutos(produto.getRestaurante().getId(), "produtos"));
		
		produtoDTO.add(algaLinks.linkToRestauranteProdutoFoto(produto.getRestaurante().getId(), produto.getId(),"Foto"));
		
		return produtoDTO;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
	}
	
//	public CollectionModel<ProdutoDTO> toCollectionDTO(List<Produto> produtos){
//		return super.toCollectionModel(produtos)
//		
//		
//		return produtos.stream()
//				.map(produto -> toModel(produto))
//				.collect(Collectors.toList());
//	}
}
