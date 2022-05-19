package com.algaworks.algafood.api.v1.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
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

import com.algaworks.algafood.api.exceptionhandler.ApiExceptionHandler;
import com.algaworks.algafood.api.v1.DTO.CozinhaDTO;
import com.algaworks.algafood.api.v1.DTO.input.CozinhaInput;
import com.algaworks.algafood.api.v1.assembler.CozinhaAssembler;
import com.algaworks.algafood.api.v1.assembler.CozinhaDTOAssembler;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/v1/cozinhas")
public class CozinhaController {

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	
	@Autowired
	private CozinhaDTOAssembler cozinhaDTOAssembler;
	
	@Autowired
	private CozinhaAssembler cozinhaAssembler;
	
	@Autowired
	private PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;
	
	@GetMapping
	public PagedModel<CozinhaDTO> listar(Pageable pageable) {
		
		if (true) {
			throw new RuntimeException("Teste de exception");
		}
		
		Page<Cozinha> pageKitchens = cozinhaRepository.findAll(pageable);
		
		PagedModel<CozinhaDTO> cozinhasPagedModel = pagedResourcesAssembler
				.toModel(pageKitchens, cozinhaDTOAssembler);
		
		return cozinhasPagedModel;
	}

	@ResponseStatus(HttpStatus.CREATED)
	@GetMapping("/{id}")
	public CozinhaDTO buscar(@PathVariable Long id) {
		return cozinhaDTOAssembler.toModel(cadastroCozinha.buscarOuFalhar(id));
	}		

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaDTO adicionar(@RequestBody @Valid CozinhaInput cozinhaInput) {
		Cozinha cozinhaSalva = cadastroCozinha.salvar(cozinhaAssembler.toDomainObject(cozinhaInput));
		
		return cozinhaDTOAssembler.toModel(cozinhaSalva);
	}

	@PutMapping("/{id}")
	public CozinhaDTO atualizar(@PathVariable Long id, @RequestBody Cozinha cozinha) {
		Cozinha cozinhaAtual = cadastroCozinha.buscarOuFalhar(id);
		BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");
		
		return cozinhaDTOAssembler.toModel(cadastroCozinha.salvar(cozinhaAtual));
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id){
			cadastroCozinha.excluir(id);
	}
}
