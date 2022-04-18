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

import com.algaworks.algafood.api.DTO.PermissaoDTO;
import com.algaworks.algafood.api.assembler.PermissaoDTOAssembler;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.service.CadastroGrupoService;

@RestController
@RequestMapping(value = "/grupos/{gruposId}/permissoes")
public class GrupoPermissaoController {

	@Autowired
	private CadastroGrupoService cadastroGrupo;
	
	@Autowired
	private PermissaoDTOAssembler permissaoDTOAssembler;
	
	@GetMapping
	public List<PermissaoDTO> listar(@PathVariable Long gruposId) {
		Grupo grupo = cadastroGrupo.buscarOuFalhar(gruposId);
		
		return permissaoDTOAssembler.toCollectionDTO(grupo.getPermissoes());
	}
	
	@PutMapping("/{permissaoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void add(@PathVariable Long gruposId, @PathVariable Long permissaoId) {
		cadastroGrupo.addPermission(gruposId, permissaoId);
	}
	
	@DeleteMapping("/{permissaoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remove(@PathVariable Long gruposId, @PathVariable Long permissaoId) {
		cadastroGrupo.removePermission(gruposId, permissaoId);
	}

}
