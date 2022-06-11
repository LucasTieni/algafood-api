package com.algaworks.algafood.api.v1.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.v1.DTO.PedidoDTO;
import com.algaworks.algafood.api.v1.DTO.PedidoResumoDTO;
import com.algaworks.algafood.api.v1.DTO.input.PedidoInput;
import com.algaworks.algafood.api.v1.assembler.PedidoAssembler;
import com.algaworks.algafood.api.v1.assembler.PedidoDTOAssembler;
import com.algaworks.algafood.api.v1.assembler.PedidoResumoDTOAssembler;
import com.algaworks.algafood.core.data.PageWrapper;
import com.algaworks.algafood.core.data.PageableTranslator;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.core.security.CheckSecurity;
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
@RequestMapping(value = "/v1/pedidos")
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
	
	@Autowired
	private PagedResourcesAssembler<Pedido> pagedResourcesAssembler;
	
	@Autowired
	private AlgaSecurity algaSecurity;
	
	@CheckSecurity.Pedidos.PodePesquisar
	@GetMapping
	public PagedModel<PedidoResumoDTO> pesquisar(PedidoFilter pedidoFilter, 
			@PageableDefault(size=10) Pageable pageable) {
				
		Pageable pageableTranslated = translatePageable(pageable);
		
		Page<Pedido> pagePedidos = pedidoRepository.findAll(
				PedidoSpecs.usandoFiltro(pedidoFilter), pageableTranslated);
		
		pagePedidos = new PageWrapper<>(pagePedidos, pageable);
		
		return pagedResourcesAssembler.toModel(pagePedidos, pedidoResumoDTOAssembler);
	}
	
	@CheckSecurity.Pedidos.PodeBuscar
	@GetMapping("/{codigoPedido}")
	public PedidoDTO buscar(@PathVariable String codigoPedido) {
		Pedido pedido = cadastroPedido.buscarOuFalhar(codigoPedido);
		return pedidoDTOAssembler.toModel(pedido);
	}
	
	@CheckSecurity.Pedidos.PodeCriar
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PedidoDTO adicionar (@RequestBody @Valid PedidoInput pedidoInput){
		try {
			Pedido pedido = pedidoAssembler.toDomainObject(pedidoInput);
			
	        pedido.setCliente(new Usuario());
	        pedido.getCliente().setId(algaSecurity.getUsuarioId());
			
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
