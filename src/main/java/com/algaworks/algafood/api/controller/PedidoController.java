package com.algaworks.algafood.api.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.DTO.PedidoDTO;
import com.algaworks.algafood.api.DTO.PedidoResumoDTO;
import com.algaworks.algafood.api.DTO.input.PedidoInput;
import com.algaworks.algafood.api.assembler.PedidoAssembler;
import com.algaworks.algafood.api.assembler.PedidoDTOAssembler;
import com.algaworks.algafood.api.assembler.PedidoResumoDTOAssembler;
import com.algaworks.algafood.core.data.PageableTranslator;
import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.PedidoNaoEncontradoException;
import com.algaworks.algafood.domain.filter.PedidoFilter;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.domain.service.EmitePedidoService;
import com.algaworks.algafood.infrastructure.repository.spec.PedidoSpecs;


@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController {

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private EmitePedidoService cadastroPedido;
	
	@Autowired
	private PedidoDTOAssembler pedidoDTOAssembler;
	
	@Autowired
	private PedidoAssembler pedidoAssembler;

	@Autowired
	private PedidoResumoDTOAssembler pedidoResumoDTOAssembler;
	
	@GetMapping
	public Page<PedidoResumoDTO> pesquisar(PedidoFilter pedidoFilter, @PageableDefault(size=10) Pageable pageable) {
		pageable = translatePageable(pageable);
		
		Page<Pedido> pageOrders = pedidoRepository.findAll(PedidoSpecs.usandoFiltro(pedidoFilter), pageable);
		
		List<PedidoResumoDTO> ordersResumoDTO = pedidoResumoDTOAssembler.toCollectionDTO(pageOrders.getContent());
		
		Page<PedidoResumoDTO> ordersResumoDTOPage = new PageImpl<>(ordersResumoDTO, pageable, pageOrders.getTotalElements());
		
		return ordersResumoDTOPage;
	}

	@ResponseStatus(HttpStatus.CREATED)
	@GetMapping("/{codigoPedido}")
	public PedidoDTO buscar(@PathVariable String codigoPedido) {
		Pedido pedido = cadastroPedido.buscarOuFalhar(codigoPedido);
		return pedidoDTOAssembler.toModel(pedido);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PedidoDTO adicionar (@RequestBody @Valid PedidoInput pedidoInput){
		try {
			Pedido pedido = pedidoAssembler.toDomainObject(pedidoInput);
			
	        pedido.setCliente(new Usuario());
	        pedido.getCliente().setId(5L);
			
			return pedidoDTOAssembler.toModel(cadastroPedido.salvar(pedido));
		} catch (PedidoNaoEncontradoException | CidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}
	
	private Pageable translatePageable (Pageable apiPageable) {
		var map = Map.of(
				"codigo", "codigo",
				"subtotal", "subtotal",
				"taxaFrete", "taxaFrete",
				"valorTotal", "valorTotal",
				"dataCriacao", "dataCriacao",
				"restaurante.nome", "restaurante.nome",
				"restaurante.id", "restaurante.id",
				"cliente.id", "cliente.id",
				"cliente.nome", "cliente.nome"
				);
		
		 
		return PageableTranslator.translate(apiPageable, map);
	}
}