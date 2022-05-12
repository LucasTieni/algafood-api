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
import com.algaworks.algafood.api.DTO.GrupoDTO;
import com.algaworks.algafood.api.assembler.GrupoDTOAssembler;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;

@RestController
@RequestMapping(value = "/usuarios/{usuarioId}/grupos")
public class UsuarioGrupoController {

	@Autowired
	private CadastroUsuarioService cadastroUsuario;
	
	@Autowired
	private GrupoDTOAssembler grupoDTOAssembler;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	@GetMapping
	public CollectionModel<GrupoDTO> listar(@PathVariable Long usuarioId) {
		Usuario usuario = cadastroUsuario.buscarOuFalhar(usuarioId);
		
		CollectionModel<GrupoDTO> gruposDTO = grupoDTOAssembler.toCollectionDTO(usuario.getGrupos())
				.removeLinks()
				.add(algaLinks.linkToUsuarioGrupoAssociacao(usuarioId, "Associar"));
		
		gruposDTO.getContent().forEach(grupoDTO -> {
			grupoDTO.add(algaLinks.linkToUsuarioGrupoDesassociacao(usuarioId, grupoDTO.getId(), "Desassociar"));
		});
		
		return gruposDTO;
	}
	
	@PutMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> add(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
		cadastroUsuario.addGroup(usuarioId, grupoId);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> remove(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
		cadastroUsuario.removeGroup(usuarioId, grupoId);
		return ResponseEntity.noContent().build();
	}

}
