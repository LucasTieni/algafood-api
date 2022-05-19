package com.algaworks.algafood.api.v2.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v2.AlgaLinksV2;
import com.algaworks.algafood.api.v2.controller.CidadeControllerV2;
import com.algaworks.algafood.api.v2.model.CidadeDTOV2;
import com.algaworks.algafood.domain.model.Cidade;

@Component
public class CidadeDTOAssemblerV2 
	extends RepresentationModelAssemblerSupport<Cidade, CidadeDTOV2>{
	
	public CidadeDTOAssemblerV2() {
		super(CidadeControllerV2.class, CidadeDTOV2.class);
	}

	@Autowired  
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinksV2 algaLinksV2;
	
	@Override
	public CidadeDTOV2 toModel(Cidade cidade) {
		CidadeDTOV2 cidadeDTO = createModelWithId(cidade.getId(), cidade);
		
		modelMapper.map(cidade, cidadeDTO);
		
		cidadeDTO.add(algaLinksV2.linkToCidades("cidades"));
		
		return cidadeDTO;
	}
	
	@Override
	public CollectionModel<CidadeDTOV2> toCollectionModel(Iterable<? extends Cidade> entities) {
		return super.toCollectionModel(entities)
				.add(algaLinksV2.linkToCidades());
	}
}
