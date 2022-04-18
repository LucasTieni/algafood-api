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

import com.algaworks.algafood.api.DTO.CidadeDTO;
import com.algaworks.algafood.api.DTO.input.CidadeInput;
import com.algaworks.algafood.api.assembler.CidadeAssembler;
import com.algaworks.algafood.api.assembler.CidadeDTOAssembler;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;

@RestController
@RequestMapping(value = "/cidades")
public class CidadeController{

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private CadastroCidadeService cadastroCidade;

	@Autowired
	private CidadeDTOAssembler cidadeDTOAssembler;
	
	@Autowired
	private CidadeAssembler cidadeAssembler;
	
	@GetMapping
	public List<CidadeDTO> listar() {
		return cidadeDTOAssembler.toCollectionDTO(cidadeRepository.findAll());
	}

	@ResponseStatus(HttpStatus.CREATED)
	@GetMapping("/{id}")
	public CidadeDTO buscar(@PathVariable Long id) {
		Cidade cidade = cadastroCidade.buscarOuFalhar(id); 

		return cidadeDTOAssembler.toModel(cidade);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeDTO adicionar (@RequestBody @Valid CidadeInput cidadeInput){
		try {
			
			Cidade cidade = cidadeAssembler.toDomainObject(cidadeInput); 
			
			return cidadeDTOAssembler.toModel(cadastroCidade.salvar(cidade));
			
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@PutMapping("/{id}")
	public CidadeDTO atualizar(@PathVariable Long id, @RequestBody @Valid CidadeInput cidadeInput) {
		Cidade cidadeAtual = cadastroCidade.buscarOuFalhar(id);
		
		cidadeAssembler.copyToDomainObject(cidadeInput, cidadeAtual);
		
		try {
			return cidadeDTOAssembler.toModel(cadastroCidade.salvar(cidadeAtual));
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id){
		cadastroCidade.excluir(id);
	}
	

}







