package com.algaworks.algafood.api.v2.controller;

import javax.validation.Valid;

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

import com.algaworks.algafood.api.v2.assembler.CozinhaAssemblerV2;
import com.algaworks.algafood.api.v2.assembler.CozinhaDTOAssemblerV2;
import com.algaworks.algafood.api.v2.model.CozinhaDTOV2;
import com.algaworks.algafood.api.v2.model.input.CozinhaInputV2;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;

@RestController
@RequestMapping(value = "/v2/cozinhas")
public class CozinhaControllerV2 {

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	
	@Autowired
	private CozinhaDTOAssemblerV2 cozinhaDTOAssembler;
	
	@Autowired
	private CozinhaAssemblerV2 cozinhaAssembler;
	
	@Autowired
	private PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;
	
	@Deprecated
	@GetMapping
	public PagedModel<CozinhaDTOV2> listar(Pageable pageable) {
		Page<Cozinha> pageKitchens = cozinhaRepository.findAll(pageable);
		
		PagedModel<CozinhaDTOV2> cozinhasPagedModel = pagedResourcesAssembler
				.toModel(pageKitchens, cozinhaDTOAssembler);
		
		return cozinhasPagedModel;
	}

	@ResponseStatus(HttpStatus.CREATED)
	@GetMapping("/{id}")
	public CozinhaDTOV2 buscar(@PathVariable Long id) {
		return cozinhaDTOAssembler.toModel(cadastroCozinha.buscarOuFalhar(id));
	}		

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaDTOV2 adicionar(@RequestBody @Valid CozinhaInputV2 cozinhaInput) {
		Cozinha cozinhaSalva = cadastroCozinha.salvar(cozinhaAssembler.toDomainObject(cozinhaInput));
		
		return cozinhaDTOAssembler.toModel(cozinhaSalva);
	}

	@PutMapping("/{id}")
	public CozinhaDTOV2 atualizar(@PathVariable Long id, @RequestBody Cozinha cozinha) {
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
