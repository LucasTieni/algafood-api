package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.DTO.GrupoDTO;
import com.algaworks.algafood.api.DTO.input.GrupoInput;
import com.algaworks.algafood.api.assembler.GrupoAssembler;
import com.algaworks.algafood.api.assembler.GrupoDTOAssembler;
import com.algaworks.algafood.domain.exception.GrupoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.repository.GrupoRepository;
import com.algaworks.algafood.domain.service.CadastroGrupoService;

@RestController
@RequestMapping(value = "/grupos")
public class GrupoController {
	
	@Autowired
	private CadastroGrupoService cadastroGrupo;
	
	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private GrupoDTOAssembler grupoDTOAssembler;
	
	@Autowired
	private GrupoAssembler grupoAssembler;
	
	@GetMapping
	public List<GrupoDTO> listar() {
		return grupoDTOAssembler.toCollectionDTO(grupoRepository.findAll());
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@GetMapping("/{id}")
	public GrupoDTO buscar(@PathVariable Long id) {
		Grupo grupo = cadastroGrupo.buscarOuFalhar(id);
		return grupoDTOAssembler.toModel(grupo);
	}		
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public GrupoDTO adicionar(@RequestBody @Valid GrupoInput grupoInput) {
		Grupo grupo = grupoAssembler.toDomainObject(grupoInput);
		return grupoDTOAssembler.toModel(cadastroGrupo.salvar(grupo));
	}
	
	@PutMapping("/{id}")
	public GrupoDTO atualizar(@PathVariable Long id, @RequestBody GrupoInput grupoInput) {
		Grupo grupoAtual = cadastroGrupo.buscarOuFalhar(id);
		grupoAssembler.copyToDomainObject(grupoInput, grupoAtual);
		
		try {
			return grupoDTOAssembler.toModel(cadastroGrupo.salvar(grupoAtual));
		} catch (GrupoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id){
			cadastroGrupo.excluir(id);
	}
}
