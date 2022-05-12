package com.algaworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.DTO.RestauranteDTO;
import com.algaworks.algafood.api.controller.RestauranteController;
import com.algaworks.algafood.domain.model.Restaurante;

@Component
public class RestauranteDTOAssembler 
	extends RepresentationModelAssemblerSupport<Restaurante, RestauranteDTO>{

	@Autowired  
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	public RestauranteDTOAssembler() {
		super(RestauranteController.class, RestauranteDTO.class);
	}
	
	public RestauranteDTO toModel(Restaurante restaurante) {
		RestauranteDTO restauranteDTO = createModelWithId(restaurante.getId(), restaurante);
		
		modelMapper.map(restaurante, restauranteDTO);      

		restauranteDTO.add(algaLinks.linkToRestaurantes("restaurantes"));
		
		restauranteDTO.getCozinha().add(algaLinks.linkToCozinha(restaurante.getCozinha().getId()));

		if (restauranteDTO.getEndereco() != null
				&& restauranteDTO.getEndereco().getCidade() != null) {
			restauranteDTO.getEndereco().getCidade().add(
					algaLinks.linkToCidade(restaurante.getEndereco().getCidade().getId()));
		}
		
		restauranteDTO.add(algaLinks.linkToRestauranteResponsaveis(restaurante.getId(), "responsaveis"));
		
		restauranteDTO.add(algaLinks.linkToRestauranteFormasPagamento(restaurante.getId(), "formas-pagamento"));
		
		if (restaurante.ativacaoPermitida()){
			restauranteDTO.add(algaLinks.linkToRestauranteAtivar(restaurante.getId(), "Ativar"));
		} else {
			restauranteDTO.add(algaLinks.linkToRestauranteDesativar(restaurante.getId(), "Desativar"));
		}
		
		if (restaurante.aberturaPermitida()) {
			restauranteDTO.add(algaLinks.linkToRestauranteOpen(restaurante.getId(), "Abrir"));
		} else {
			restauranteDTO.add(algaLinks.linkToRestauranteClose(restaurante.getId(), "Fechar"));
		}
		
		restauranteDTO.add(algaLinks.linkToProdutos(restaurante.getId(), "produtos"));
				
		return restauranteDTO;   		
	}
	
	public List<RestauranteDTO> toCollectionDTO(List<Restaurante> restaurantes){
		return restaurantes.stream()
				.map(restaurante -> toModel(restaurante))
				.collect(Collectors.toList());
	}
}
