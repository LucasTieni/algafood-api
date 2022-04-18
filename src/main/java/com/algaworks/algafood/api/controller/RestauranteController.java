package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.algaworks.algafood.api.DTO.RestauranteDTO;
import com.algaworks.algafood.api.DTO.input.RestauranteInput;
import com.algaworks.algafood.api.DTO.view.RestauranteView;
import com.algaworks.algafood.api.assembler.RestauranteAssembler;
import com.algaworks.algafood.api.assembler.RestauranteDTOAssembler;
import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
@RequestMapping(value = "/restaurantes")
public class RestauranteController {

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CadastroRestauranteService cadastroRestaurante;
	
	@Autowired
	private RestauranteAssembler restauranteAssembler;
	
	@Autowired
	private RestauranteDTOAssembler restauranteDTOAssembler;
		
	
	@GetMapping
	public List<RestauranteDTO> listar() {
		return restauranteDTOAssembler.toCollectionDTO(restauranteRepository.findAll());
	}
	
//	@JsonView(RestauranteView.Resumo.class)
//	@GetMapping(params = "projecao=resumo")
//	public List<RestauranteDTO> listarResumido() {
//		return listar();
//	}
//	
	@JsonView(RestauranteView.ApenasNome.class)
	@GetMapping(params = "projecao=apenas-nome")
	public List<RestauranteDTO> listarApenasNomes() {
		return listar();
	}
	
//	@GetMapping
//	public MappingJacksonValue listar(@RequestParam(required=false) String projecao) {
//		List<Restaurante> restaurantes = restauranteRepository.findAll();
//		List<RestauranteDTO> restaurantesDTO = restauranteDTOAssembler.toCollectionDTO(restaurantes);
//		
//		MappingJacksonValue restaurantesWrapper = new MappingJacksonValue(restaurantesDTO);
//		
//		restaurantesWrapper.setSerializationView(RestauranteView.Resumo.class);
//		
//		if ("apenas-nome".equalsIgnoreCase(projecao)) {
//			restaurantesWrapper.setSerializationView(RestauranteView.ApenasNome.class);
//		} else if ("completo".equalsIgnoreCase(projecao)) {
//			restaurantesWrapper.setSerializationView(null);			
//		}
//		
//		
//		return restaurantesWrapper;
//	}

	@ResponseStatus(HttpStatus.CREATED)
	@GetMapping("/{id}")
	public RestauranteDTO buscar(@PathVariable Long id) {
		Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(id);
		
		return restauranteDTOAssembler.toModel(restaurante);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RestauranteDTO adicionar (@RequestBody @Valid RestauranteInput restauranteInput){
		try {
			Restaurante restaurante = restauranteAssembler.toDomainObject(restauranteInput);
			
			return restauranteDTOAssembler.toModel(cadastroRestaurante.salvar(restaurante));
		} catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	@PutMapping("/{id}")
	public RestauranteDTO atualizar(@PathVariable Long id, @RequestBody @Valid RestauranteInput restauranteInput) {
		Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(id);
		
		restauranteAssembler.copyToDomainObject(restauranteInput, restauranteAtual);
		
		try {
			return restauranteDTOAssembler.toModel(cadastroRestaurante.salvar(restauranteAtual));
		} catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Restaurante> remover(@PathVariable Long id){
		try {
			cadastroRestaurante.excluir(id);

			return ResponseEntity.noContent().build();

		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();

		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();

		}
	}
	
	@PutMapping("/{restauranteId}/active")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void activate(@PathVariable Long restauranteId) {
		cadastroRestaurante.activate(restauranteId);
	}
	
	@DeleteMapping("/{restauranteId}/deactive")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deactivate(@PathVariable Long restauranteId) {
		cadastroRestaurante.deactivate(restauranteId);
	}
	
	@PutMapping("/{restauranteId}/open")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void open(@PathVariable Long restauranteId) {
		cadastroRestaurante.open(restauranteId);
	}
	
	@PutMapping("/{restauranteId}/close")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void close(@PathVariable Long restauranteId) {
		cadastroRestaurante.close(restauranteId);
	}
	
	@PutMapping("/multiactivation")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void multiactivation(@RequestBody List<Long> restauranteIds) {
		try {
			cadastroRestaurante.activate(restauranteIds);
		} catch (RestauranteNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}	
	}
	
	@DeleteMapping("/multideactivation")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void multideactivation(@RequestBody List<Long> restauranteIds) {
		try {
			cadastroRestaurante.deactivate(restauranteIds);
		} catch (RestauranteNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
		
	}
	
}
