package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.DTO.GrupoDTO;
import com.algaworks.algafood.api.controller.GrupoController;
import com.algaworks.algafood.domain.model.Grupo;

@Component
public class GrupoDTOAssembler 
	extends RepresentationModelAssemblerSupport<Grupo, GrupoDTO>{
	
	@Autowired  
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	public GrupoDTOAssembler() {
		super(GrupoController.class, GrupoDTO.class);
	}
	
	public GrupoDTO toModel(Grupo grupo) {
		GrupoDTO grupoDTO = createModelWithId(grupo.getId(), grupo);
		
		modelMapper.map(grupo, grupoDTO);
		
		grupoDTO.add(algaLinks.linkToGrupo("grupos"));
		
		grupoDTO.add(algaLinks.linkToGrupoPermissao(grupoDTO.getId(), "permissoes"));
		
		
		return grupoDTO;       
	}
	
	public CollectionModel<GrupoDTO> toCollectionDTO(Iterable<? extends Grupo> entities){
		return super.toCollectionModel(entities)
				.add(algaLinks.linkToGrupo());
	}
}
