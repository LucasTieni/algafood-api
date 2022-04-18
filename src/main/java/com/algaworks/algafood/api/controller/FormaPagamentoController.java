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

import com.algaworks.algafood.api.DTO.FormaPagamentoDTO;
import com.algaworks.algafood.api.DTO.input.FormaPagamentoInput;
import com.algaworks.algafood.api.assembler.FormaPagamentoAssembler;
import com.algaworks.algafood.api.assembler.FormaPagamentoDTOAssembler;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.FormaPagamentoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import com.algaworks.algafood.domain.service.CadastroFormaPagamentoService;

@RestController
@RequestMapping(value = "/formaspagamento")
public class FormaPagamentoController {
	
	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;
	
	@Autowired
	private CadastroFormaPagamentoService cadastroFormaPagamento;
	
	@Autowired
	private FormaPagamentoDTOAssembler formaPagamentoDTOAssembler;

	@Autowired
	private FormaPagamentoAssembler formaPagamentoAssembler;
	
	@GetMapping
	public List<FormaPagamentoDTO> listar() {
		return formaPagamentoDTOAssembler.toCollectionDTO(formaPagamentoRepository.findAll());
	}

	@ResponseStatus(HttpStatus.CREATED)
	@GetMapping("/{formaPagamentoId}")
	public FormaPagamentoDTO buscar(@PathVariable Long formaPagamentoId) {
		FormaPagamento formaPagamento = cadastroFormaPagamento.buscarOuFalhar(formaPagamentoId); 

		return formaPagamentoDTOAssembler.toModel(formaPagamento);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public FormaPagamentoDTO adicionar (@RequestBody @Valid FormaPagamentoInput formaPagamentoInput){
		try {
			
			FormaPagamento formaPagamento = formaPagamentoAssembler.toDomainObject(formaPagamentoInput); 
			
			return formaPagamentoDTOAssembler.toModel(cadastroFormaPagamento.salvar(formaPagamento));
			
		} catch (FormaPagamentoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@PutMapping("/{id}")
	public FormaPagamentoDTO atualizar(@PathVariable Long id, @RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
		FormaPagamento formaPagamentoAtual = cadastroFormaPagamento.buscarOuFalhar(id);
		
		formaPagamentoAssembler.copyToDomainObject(formaPagamentoInput, formaPagamentoAtual);
		
		try {
			return formaPagamentoDTOAssembler.toModel(cadastroFormaPagamento.salvar(formaPagamentoAtual));
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id){
		cadastroFormaPagamento.excluir(id);
	}
	
}
