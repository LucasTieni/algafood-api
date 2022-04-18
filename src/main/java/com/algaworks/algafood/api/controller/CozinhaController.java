package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

import com.algaworks.algafood.api.DTO.CozinhaDTO;
import com.algaworks.algafood.api.DTO.input.CozinhaInput;
import com.algaworks.algafood.api.assembler.CozinhaAssembler;
import com.algaworks.algafood.api.assembler.CozinhaDTOAssembler;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;

@RestController
@RequestMapping(value = "/cozinhas")
public class CozinhaController {

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	
	@Autowired
	private CozinhaDTOAssembler cozinhaDTOAssembler;
	
	@Autowired
	private CozinhaAssembler cozinhaAssembler;
	
	@GetMapping
	public Page<CozinhaDTO> listar(Pageable pageable) {
		Page<Cozinha> pageKitchens = cozinhaRepository.findAll(pageable);
		
		List<CozinhaDTO> kitchensDTO = cozinhaDTOAssembler.toCollectionDTO(pageKitchens.getContent());
		
		Page<CozinhaDTO> kitchensDTOPage = new PageImpl<>(kitchensDTO, pageable, pageKitchens.getTotalElements());
		
		return kitchensDTOPage;
	}

	@ResponseStatus(HttpStatus.CREATED)
	@GetMapping("/{id}")
	public CozinhaDTO buscar(@PathVariable Long id) {
		return cozinhaDTOAssembler.toModel(cadastroCozinha.buscarOuFalhar(id));
	}		

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cozinha adicionar(@RequestBody @Valid CozinhaInput cozinhaInput) {
		return cadastroCozinha.salvar(cozinhaAssembler.toDomainObject(cozinhaInput));
	}

	@PutMapping("/{id}")
	public Cozinha atualizar(@PathVariable Long id, @RequestBody Cozinha cozinha) {
		Cozinha cozinhaAtual = cadastroCozinha.buscarOuFalhar(id);
		BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");
		return cadastroCozinha.salvar(cozinhaAtual);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id){
			cadastroCozinha.excluir(id);
	}
}
