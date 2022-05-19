package com.algaworks.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.DTO.UsuarioDTO;
import com.algaworks.algafood.api.v1.controller.UsuarioController;
import com.algaworks.algafood.domain.model.Usuario;

@Component
public class UsuarioDTOAssembler 
	extends RepresentationModelAssemblerSupport<Usuario, UsuarioDTO>{
	
	@Autowired  
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	public UsuarioDTOAssembler() {
		super(UsuarioController.class, UsuarioDTO.class);
	}

	
	public UsuarioDTO toModel(Usuario usuario) {
		
		UsuarioDTO usuarioDTO = createModelWithId(usuario.getId(), usuario);
		
		modelMapper.map(usuario, usuarioDTO);
		
		usuarioDTO.add(algaLinks.linkToUsuarios("usuarios"));
		
		usuarioDTO.add(algaLinks.linkToUsuarioGrupo(usuarioDTO.getId(), "grupos-usuarios"));
		
		return usuarioDTO;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
	}
	
	@Override
	public CollectionModel<UsuarioDTO> toCollectionModel(Iterable<? extends Usuario> entities) {
		return super.toCollectionModel(entities)
				.add(algaLinks.linkToUsuarios());
	}
}

