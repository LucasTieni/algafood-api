package com.algaworks.algafood.api.v2.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.v2.assembler.CidadeAssemblerV2;
import com.algaworks.algafood.api.v2.assembler.CidadeDTOAssemblerV2;
import com.algaworks.algafood.api.v2.model.CidadeDTOV2;
import com.algaworks.algafood.api.v2.model.input.CidadeInputV2;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;

//@Api(tags = "Cidades")
@RestController
@RequestMapping(path = "/v2/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeControllerV2{

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private CadastroCidadeService cadastroCidade;

	@Autowired
	private CidadeDTOAssemblerV2 cidadeDTOAssembler;
	
	@Autowired
	private CidadeAssemblerV2 cidadeAssembler;
	
//	@ApiOperation("Lista as cidades")
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public CollectionModel<CidadeDTOV2> listar() {
		List<Cidade> allCities = cidadeRepository.findAll();
		
		return cidadeDTOAssembler.toCollectionModel(allCities);
	};
	
//	@ApiOperation("Busca uma cidade por Id")
	@ResponseStatus(HttpStatus.CREATED)
	@GetMapping(path = "/{cidadeId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public CidadeDTOV2 buscar(
//			@ApiParam(value="Id de uma Cidade", example = "1") 
			@PathVariable Long cidadeId) {
		Cidade cidade = cadastroCidade.buscarOuFalhar(cidadeId); 
		
		return cidadeDTOAssembler.toModel(cidade);
	}
	
//	@ApiOperation("Cadastra uma cidade")
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeDTOV2 adicionar (
//			@ApiParam(name="corpo", value = "Representação de uma nova cidade") 
		@RequestBody @Valid CidadeInputV2 cidadeInput){
		try {
			System.out.println("passou aqui");
			Cidade cidade = cidadeAssembler.toDomainObject(cidadeInput); 
			
			return cidadeDTOAssembler.toModel(cadastroCidade.salvar(cidade));
			
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
//	@ApiOperation("Atualiza uma cidade por Id")
	@PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public CidadeDTOV2 atualizar(
//			@ApiParam(value = "Id de uma cidade") 
			@PathVariable Long id, 
//			@ApiParam(name="corpo", value = "Representação de uma nova cidade com os novos dados") 
			@RequestBody @Valid CidadeInputV2 cidadeInput) {
		Cidade cidadeAtual = cadastroCidade.buscarOuFalhar(id);
		
		cidadeAssembler.copyToDomainObject(cidadeInput, cidadeAtual);
		
		try {
			return cidadeDTOAssembler.toModel(cadastroCidade.salvar(cidadeAtual));
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
//	@ApiOperation("Exclui uma cidade por Id")
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(
//			@ApiParam(value = "Id de uma cidade a ser excluida")
			@PathVariable Long id){
		cadastroCidade.excluir(id);
	}
	

}







