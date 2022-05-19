package com.algaworks.algafood.api.v1.controller;

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

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.DTO.PermissaoDTO;
import com.algaworks.algafood.api.v1.assembler.PermissaoDTOAssembler;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.service.CadastroGrupoService;

@RestController
@RequestMapping(value = "/v1/grupos/{gruposId}/permissoes")
public class GrupoPermissaoController {

	@Autowired
	private CadastroGrupoService cadastroGrupo;
	
	@Autowired
	private PermissaoDTOAssembler permissaoDTOAssembler;
	
	@Autowired
	private AlgaLinks algaLinks;

	
	@GetMapping
	public CollectionModel<PermissaoDTO> listar(@PathVariable Long gruposId) {
		Grupo grupo = cadastroGrupo.buscarOuFalhar(gruposId);
		
		CollectionModel<PermissaoDTO> permissoesDTO = permissaoDTOAssembler.toCollectionDTO(grupo.getPermissoes())
				.removeLinks()
				.add(algaLinks.linkToGrupoPermissao(grupo.getId()))
				.add(algaLinks.linkToGrupoPermissaoAssociacao(gruposId, "Associar")); 
		
		permissoesDTO.getContent().forEach(permissaoDTO -> {
			permissaoDTO.add(algaLinks.linkToGrupoPermissaoDesassociacao(gruposId, permissaoDTO.getId(), "Desassociar"));
		});
		
		return permissoesDTO;
	}
	
	@PutMapping("/{permissaoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> add(@PathVariable Long gruposId, @PathVariable Long permissaoId) {
		cadastroGrupo.addPermission(gruposId, permissaoId);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{permissaoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> remove(@PathVariable Long gruposId, @PathVariable Long permissaoId) {
		cadastroGrupo.removePermission(gruposId, permissaoId);
		return ResponseEntity.noContent().build();
	}

}
