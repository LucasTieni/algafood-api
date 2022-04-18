package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.DTO.UsuarioDTO;
import com.algaworks.algafood.api.assembler.UsuarioDTOAssembler;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/usuarios")
public class RestauranteUsuarioController {

	@Autowired
	private CadastroRestauranteService cadastroRestaurante;
	
	@Autowired
	private UsuarioDTOAssembler usuarioDTOAssembler;
	
	@GetMapping
	public List<UsuarioDTO> listar(@PathVariable Long restauranteId) {
		Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);
		
		return usuarioDTOAssembler.toCollectionDTO(restaurante.getResponsaveis());
	}
	
	@PutMapping("/{userId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void add(@PathVariable Long restauranteId, @PathVariable Long userId) {
		cadastroRestaurante.addUser(restauranteId, userId);
	}
	
	@DeleteMapping("/{userId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remove(@PathVariable Long restauranteId, @PathVariable Long userId) {
		cadastroRestaurante.removeUser(restauranteId, userId);
	}

}
