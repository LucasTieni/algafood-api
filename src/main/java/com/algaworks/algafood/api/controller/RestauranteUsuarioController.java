package com.algaworks.algafood.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.DTO.UsuarioDTO;
import com.algaworks.algafood.api.assembler.UsuarioDTOAssembler;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;

import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/usuarios")
public class RestauranteUsuarioController {

	@Autowired
	private CadastroRestauranteService cadastroRestaurante;
	
	@Autowired
	private UsuarioDTOAssembler usuarioDTOAssembler;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	@GetMapping
	public CollectionModel<UsuarioDTO> listar(@ApiParam(value = "ID do restaurante", example = "1", required = true) 
			@PathVariable Long restauranteId) {
		Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);
		
		CollectionModel<UsuarioDTO> usuariosDTO = usuarioDTOAssembler.toCollectionModel(restaurante.getResponsaveis())
			.removeLinks()
			.add(algaLinks.linkToRestauranteResponsaveis(restauranteId))
			.add(algaLinks.linkToRestauranteResponsaveisAdd(restauranteId, "Associar"));
		
		usuariosDTO.getContent().forEach(usuarioDTO -> {
			usuarioDTO.add(algaLinks.linkToRestauranteResponsaveisRemove(restauranteId, usuarioDTO.getId(),
					"Desassociar"));
		});
		
		return usuariosDTO;
	}
	
	@PutMapping("/{userId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> add(@PathVariable Long restauranteId, @PathVariable Long userId) {
		cadastroRestaurante.addUser(restauranteId, userId);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{userId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> remove(@PathVariable Long restauranteId, @PathVariable Long userId) {
		cadastroRestaurante.removeUser(restauranteId, userId);
		return ResponseEntity.noContent().build();
	}

}
