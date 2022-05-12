package com.algaworks.algafood.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.DTO.PermissaoDTO;
import com.algaworks.algafood.api.assembler.PermissaoDTOAssembler;
import com.algaworks.algafood.domain.repository.PermissaoRepository;

@RestController
@RequestMapping(value = "/permissoes", produces = MediaType.APPLICATION_JSON_VALUE)
public class PermissaoController {
	
	@Autowired
	private PermissaoRepository permissaoRepository;
	
	@Autowired
	private PermissaoDTOAssembler permissaoDTOAssembler;
	
	@GetMapping
	public CollectionModel<PermissaoDTO> listar() {
		return permissaoDTOAssembler.toCollectionDTO(permissaoRepository.findAll());
	}
	
}
