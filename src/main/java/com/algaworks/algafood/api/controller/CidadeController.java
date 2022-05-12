package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "Cidades")
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
	
	@ApiOperation("Lista as cidades")
	@GetMapping
	public CollectionModel<CidadeDTO> listar() {
		List<Cidade> allCities = cidadeRepository.findAll();
		
		return cidadeDTOAssembler.toCollectionModel(allCities);
	};
	
	@ApiOperation("Busca uma cidade por Id")
	@ResponseStatus(HttpStatus.CREATED)
	@GetMapping("/{cidadeId}")
	public CidadeDTO buscar(@ApiParam(value="Id de uma Cidade", example = "1") @PathVariable Long cidadeId) {
		Cidade cidade = cadastroCidade.buscarOuFalhar(cidadeId); 
		
		return cidadeDTOAssembler.toModel(cidade);
	}
	
	@ApiOperation("Cadastra uma cidade")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeDTO adicionar (@ApiParam(name="corpo", value = "Representação de uma nova cidade") @RequestBody @Valid CidadeInput cidadeInput){
		try {

			Cidade cidade = cidadeAssembler.toDomainObject(cidadeInput); 
			
			return cidadeDTOAssembler.toModel(cadastroCidade.salvar(cidade));
			
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@ApiOperation("Atualiza uma cidade por Id")
	@PutMapping("/{id}")
	public CidadeDTO atualizar(
			@ApiParam(value = "Id de uma cidade") @PathVariable Long id, 
			@ApiParam(name="corpo", value = "Representação de uma nova cidade com os novos dados") @RequestBody @Valid CidadeInput cidadeInput) {
		Cidade cidadeAtual = cadastroCidade.buscarOuFalhar(id);
		
		cidadeAssembler.copyToDomainObject(cidadeInput, cidadeAtual);
		
		try {
			return cidadeDTOAssembler.toModel(cadastroCidade.salvar(cidadeAtual));
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@ApiOperation("Exclui uma cidade por Id")
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@ApiParam(value = "Id de uma cidade a ser excluida") @PathVariable Long id){
		cadastroCidade.excluir(id);
	}
	

}







