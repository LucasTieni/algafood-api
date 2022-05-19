package com.algaworks.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.DTO.PermissaoDTO;
import com.algaworks.algafood.api.v1.controller.PermissaoController;
import com.algaworks.algafood.domain.model.Permissao;

@Component
public class PermissaoDTOAssembler 
	extends RepresentationModelAssemblerSupport<Permissao, PermissaoDTO>{
	
	@Autowired  
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	public PermissaoDTOAssembler() {
		super(PermissaoController.class, PermissaoDTO.class);
	}
	
	public PermissaoDTO toModel(Permissao permissao) {
		
		return modelMapper.map(permissao, PermissaoDTO.class);
	}
	
	public CollectionModel<PermissaoDTO> toCollectionDTO(Iterable<? extends Permissao> entities){
		return super.toCollectionModel(entities).add(algaLinks.linkToPermissoes());
	}
}
