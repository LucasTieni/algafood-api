package com.algaworks.algafood.api.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.DTO.FormaPagamentoDTO;
import com.algaworks.algafood.api.v1.assembler.FormaPagamentoDTOAssembler;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping(value = "/v1/restaurantes/{restauranteId}/formas-pagamento")
public class RestauranteFormaPagamentoController {

	@Autowired
	private AlgaLinks algaLinks;
	
	@Autowired
	private CadastroRestauranteService cadastroRestaurante;
	
	@Autowired
	private FormaPagamentoDTOAssembler formaPagamentoDTOAssembler;
	
	@GetMapping
	public  CollectionModel<FormaPagamentoDTO> listar(@PathVariable Long restauranteId) {
		Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);
		
		CollectionModel<FormaPagamentoDTO> formasPagamentoDTO = formaPagamentoDTOAssembler
				.toCollectionDTO(restaurante.getFormasPagamento())
				.removeLinks()
				.add(algaLinks.linkToRestauranteFormasPagamento(restauranteId))
				.add(algaLinks.linkToRestauranteFormaPagamentoConnect(restauranteId, "Associar")); 
		
		formasPagamentoDTO.getContent().forEach(formaPagamentoDTO -> {
			formaPagamentoDTO.add(algaLinks.linkToRestauranteFormaPagamentoDisconnect(restauranteId, formaPagamentoDTO.getId(),
					"Desassociar"));
		});
		
		return formasPagamentoDTO;
	}
	
	@PutMapping("/{formaPagamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> connect(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
		cadastroRestaurante.connectPaymentMode(restauranteId, formaPagamentoId);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{formaPagamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> disconnect(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
		cadastroRestaurante.disconnectPaymentMode(restauranteId, formaPagamentoId);
		return ResponseEntity.noContent().build();
	}

}
