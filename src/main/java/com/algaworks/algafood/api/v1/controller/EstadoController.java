package com.algaworks.algafood.api.v1.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.v1.DTO.EstadoDTO;
import com.algaworks.algafood.api.v1.assembler.EstadoDTOAssembler;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.CadastroEstadoService;

@RestController
@RequestMapping("/v1/estados")
public class EstadoController {

	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private CadastroEstadoService cadastroEstado;

	@Autowired
	private EstadoDTOAssembler estadoDTOAssembler;
	
	@CheckSecurity.Estado.PodeConsultar
//	@ApiOperation("Lista os estados")
	@GetMapping
	public CollectionModel<EstadoDTO> listar() {
		List<Estado> allStates = estadoRepository.findAll();
		
		return estadoDTOAssembler.toCollectionModel(allStates);
	}
	
	@CheckSecurity.Estado.PodeConsultar
	@ResponseStatus(HttpStatus.CREATED)
	@GetMapping("/{id}")
	public EstadoDTO buscar(@PathVariable Long id) {
		return estadoDTOAssembler.toModel(cadastroEstado.buscarOuFalhar(id));
	}
	
	@CheckSecurity.Estado.PodeEditar
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> adicionar (@RequestBody @Valid Estado estado){
		try {
			estado = cadastroEstado.salvar(estado);

			return ResponseEntity.status(HttpStatus.CREATED)
					.body(estado);

		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest()
					.body(e.getMessage());
		}
	}

	@CheckSecurity.Estado.PodeEditar
	@PutMapping("/{id}")
	public Estado atualizar(@PathVariable Long id, @RequestBody @Valid Estado estado) {
		Estado estadoAtual = cadastroEstado.buscarOuFalhar(id);
		BeanUtils.copyProperties(estado, estadoAtual, "id");
		return cadastroEstado.salvar(estadoAtual);
	}
	
	@CheckSecurity.Estado.PodeEditar
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id){
		cadastroEstado.excluir(id);
	}

}


