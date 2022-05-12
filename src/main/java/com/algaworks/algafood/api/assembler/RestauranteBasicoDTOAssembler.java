package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.DTO.RestauranteBasicoDTO;
import com.algaworks.algafood.api.controller.RestauranteController;
import com.algaworks.algafood.domain.model.Restaurante;

@Component
public class RestauranteBasicoDTOAssembler 
	extends RepresentationModelAssemblerSupport<Restaurante, RestauranteBasicoDTO>{

	@Autowired  
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	public RestauranteBasicoDTOAssembler() {
		super(RestauranteController.class, RestauranteBasicoDTO.class);
	}
	
	public RestauranteBasicoDTO toModel(Restaurante restaurante) {
		RestauranteBasicoDTO restauranteDTO = createModelWithId(restaurante.getId(), restaurante);
		
		modelMapper.map(restaurante, restauranteDTO);      

		restauranteDTO.add(algaLinks.linkToRestaurantes("restaurantes"));
		
		restauranteDTO.getCozinha().add(algaLinks.linkToCozinha(restaurante.getCozinha().getId()));
				
		return restauranteDTO;   		
	}
	
	public CollectionModel<RestauranteBasicoDTO> toCollectionDTO(Iterable <? extends Restaurante> entities){
		return super.toCollectionModel(entities).add(algaLinks.linkToRestaurantes());
	}
}
