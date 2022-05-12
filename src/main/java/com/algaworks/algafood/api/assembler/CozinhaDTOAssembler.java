package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.DTO.CozinhaDTO;
import com.algaworks.algafood.api.controller.CozinhaController;
import com.algaworks.algafood.domain.model.Cozinha;

@Component
public class CozinhaDTOAssembler extends RepresentationModelAssemblerSupport<Cozinha, CozinhaDTO>{
	

	@Autowired  
	private ModelMapper modelMapper;
	
	@Autowired 
	private AlgaLinks algaLinks;
	
	public CozinhaDTOAssembler() {
		super(CozinhaController.class, CozinhaDTO.class);
	}
	
	@Override
	public CozinhaDTO toModel(Cozinha cozinha) {
		CozinhaDTO cozinhaDTO = createModelWithId(cozinha.getId(), cozinha);
		
		modelMapper.map(cozinha, cozinhaDTO);
		
		cozinhaDTO.add(algaLinks.linkToCozinha("cozinhas"));
		
		return cozinhaDTO;
	}
	
}
