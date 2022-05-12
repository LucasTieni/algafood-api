package com.algaworks.algafood.api;

import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariable.VariableType;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.controller.CidadeController;
import com.algaworks.algafood.api.controller.CozinhaController;
import com.algaworks.algafood.api.controller.EstadoController;
import com.algaworks.algafood.api.controller.FluxoPedidoController;
import com.algaworks.algafood.api.controller.FormaPagamentoController;
import com.algaworks.algafood.api.controller.GrupoController;
import com.algaworks.algafood.api.controller.GrupoPermissaoController;
import com.algaworks.algafood.api.controller.PedidoController;
import com.algaworks.algafood.api.controller.PermissaoController;
import com.algaworks.algafood.api.controller.RestauranteController;
import com.algaworks.algafood.api.controller.RestauranteFormaPagamentoController;
import com.algaworks.algafood.api.controller.RestauranteProdutoController;
import com.algaworks.algafood.api.controller.RestauranteProdutoFotoController;
import com.algaworks.algafood.api.controller.RestauranteUsuarioController;
import com.algaworks.algafood.api.controller.UsuarioController;
import com.algaworks.algafood.api.controller.UsuarioGrupoController;

@Component
public class AlgaLinks {
	
	public static final TemplateVariables PAGE_VARIABLES = new TemplateVariables(
			new TemplateVariable("page", VariableType.REQUEST_PARAM),
			new TemplateVariable("size", VariableType.REQUEST_PARAM),
			new TemplateVariable("sort", VariableType.REQUEST_PARAM));

	public Link linkToPedidos() {
		TemplateVariables filterVariables = new TemplateVariables(
				new TemplateVariable("clienteId", VariableType.REQUEST_PARAM),
				new TemplateVariable("restauranteId", VariableType.REQUEST_PARAM),
				new TemplateVariable("dataCriacaoInicio", VariableType.REQUEST_PARAM),
				new TemplateVariable("dataCriacaoFim", VariableType.REQUEST_PARAM)
				);
		
		String pedidosUrl = WebMvcLinkBuilder.linkTo(PedidoController.class).toUri().toString(); 
		
		return Link.of(UriTemplate.of(pedidosUrl, PAGE_VARIABLES.concat(filterVariables)), "pedidos");
	}
	
	public Link linkToConfirmacaoPedido(String codigoPedido, String rel) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FluxoPedidoController.class)
				.confirmar(codigoPedido)).withRel(rel);
	}
	
	public Link linkToEntregaPedido(String codigoPedido, String rel) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FluxoPedidoController.class)
				.entregue(codigoPedido)).withRel(rel);
	}
	
	public Link linkToCancelamentoPedido(String codigoPedido, String rel) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FluxoPedidoController.class)
				.cancelar(codigoPedido)).withRel(rel);
	}
	
	public Link linkToRestaurante(Long restauranteId) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteController.class)
				.buscar(restauranteId)).withSelfRel();
	}

	public Link linkToRestaurantes(String rel) {
		return WebMvcLinkBuilder.linkTo(RestauranteController.class).withRel(rel);
	}

	public Link linkToRestaurantes() {
		return linkToRestaurantes(IanaLinkRelations.SELF.value());
	}
	
	public Link linkToRestauranteAtivar(Long restauranteId, String rel) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteController.class)
				.activate(restauranteId)).withRel(rel);
	}
	
	public Link linkToRestauranteDesativar(Long restauranteId, String rel) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteController.class)
				.deactivate(restauranteId)).withRel(rel);
	}
	
	public Link linkToRestauranteOpen(Long restauranteId, String rel) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteController.class)
				.open(restauranteId)).withRel(rel);
	}

	public Link linkToRestauranteClose(Long restauranteId, String rel) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteController.class)
				.close(restauranteId)).withRel(rel);
	}
	
	public Link linkToRestauranteResponsaveis(Long restauranteId, String rel) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteUsuarioController.class)
				.listar(restauranteId))
				.withRel(rel);
	}
	
	public Link linkToRestauranteResponsaveis(Long restauranteId) {
		return linkToRestauranteResponsaveis(restauranteId, IanaLinkRelations.SELF.value());
	}
	
	public Link linkToRestauranteResponsaveisAdd(Long restauranteId, String rel) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteUsuarioController.class)
				.add(restauranteId,null))
				.withRel(rel);
	}
	
	public Link linkToRestauranteResponsaveisRemove(Long restauranteId, Long usuarioId, String rel) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteUsuarioController.class)
				.remove(restauranteId,usuarioId))
				.withRel(rel);
	}
	
	public Link linkToRestauranteFormasPagamento(Long restauranteId, String rel) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteFormaPagamentoController.class)
				.listar(restauranteId))
				.withRel(rel);
	}
	
	
	public Link linkToRestauranteFormaPagamentoDisconnect(Long restauranteId, Long formaPagamentoId, String rel) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteFormaPagamentoController.class)
				.disconnect(restauranteId, formaPagamentoId)).withRel(rel);
	}
	
	public Link linkToRestauranteFormaPagamentoConnect(Long restauranteId, String rel) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteFormaPagamentoController.class)
				.connect(restauranteId, null)).withRel(rel);
	}
	
	public Link linkToFormasPagamento(String rel) {
		return WebMvcLinkBuilder.linkTo(FormaPagamentoController.class).withRel(rel);
	}
	
	public Link linkToFormasPagamento() {
		return linkToFormasPagamento(IanaLinkRelations.SELF.value());
	}
	
	public Link linkToRestauranteFormasPagamento(Long restauranteId) {
		return linkToRestauranteFormasPagamento(restauranteId, IanaLinkRelations.SELF.value());
	}
	
	public Link linkToUsuario(Long usuarioId) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class)
				.buscar(usuarioId)).withSelfRel();
	}
	
	public Link linkToUsuarios(String rel) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class)
				.listar()).withRel(rel);
	}
	
	public Link linkToUsuarios() {
		return linkToUsuarios(IanaLinkRelations.SELF.value());
	}
	
	public Link linkToUsuarioGrupo(Long usuarioId, String rel) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioGrupoController.class)
				.listar(usuarioId)).withRel(rel);
	}
	
	public Link linkToUsuarioGrupoAssociacao(Long usuarioId, String rel) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioGrupoController.class)
				.add(usuarioId, null)).withRel(rel);
	}
	
	public Link linkToUsuarioGrupoDesassociacao(Long usuarioId, Long grupoId, String rel) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioGrupoController.class)
				.remove(usuarioId, grupoId)).withRel(rel);
	}
	
	public Link linkToGrupo(String rel) {
		return WebMvcLinkBuilder.linkTo(GrupoController.class).withRel(rel);
	}
	
	public Link linkToGrupo() {
		return linkToGrupo(IanaLinkRelations.SELF.value());
	}
	
	public Link linkToGrupoPermissao(Long grupoId, String rel) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(GrupoPermissaoController.class)
				.listar(grupoId)).withRel(rel);
	}
	
	public Link linkToGrupoPermissao(Long grupoId) {
		return linkToGrupoPermissao(grupoId, IanaLinkRelations.SELF.value());
	}
	
	public Link linkToFormaPagamento(Long formaPagamentoId) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FormaPagamentoController.class)
				.buscar(formaPagamentoId, null)).withSelfRel();
	}
	
	public Link linkToCidade(Long cidadeId) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CidadeController.class)
				.buscar(cidadeId)).withSelfRel();
	}
	
	public Link linkToCidades(String rel) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CidadeController.class)
				.listar()).withRel(rel);
	}
	
	public Link linkToCidades() {
		return linkToCidades(IanaLinkRelations.SELF.value());
	}
	
	public Link linkToEstado(Long estadoId) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EstadoController.class)
				.buscar(estadoId)).withSelfRel();
	}
	
	public Link linkToEstado(String rel) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EstadoController.class)
				.listar()).withRel(rel);
	}
	
	public Link linkToEstados() {
		return linkToEstado(IanaLinkRelations.SELF.value());
	}
	
	public Link linkToProduto(Long restauranteId, Long produtoId, String rel) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteProdutoController.class)
				.buscar(restauranteId, produtoId)).withRel(rel);
	}
	
	public Link linkToProdutos(Long restauranteId, String rel) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteProdutoController.class)
				.listar(restauranteId, null)).withRel(rel);
	}
	
	public Link linkToProdutos(Long restauranteId) {
		return linkToProdutos(restauranteId, IanaLinkRelations.SELF.value());
	}
	
	public Link linkToPedidos(String rel) {
		return WebMvcLinkBuilder.linkTo(PedidoController.class).withRel(rel);
	}
	
	public Link linkToRestauranteProdutoFoto(Long restauranteId, Long produtoId, String rel) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteProdutoFotoController.class)
				.buscar(restauranteId, produtoId)).withRel(rel);
	}
	
	public Link linkToRestauranteProdutoFoto(Long restauranteId, Long produtoId) {
		return linkToRestauranteProdutoFoto(restauranteId, produtoId, IanaLinkRelations.SELF.value());
	}
	
	public Link linkToCozinha(String rel) {
		return WebMvcLinkBuilder.linkTo(CozinhaController.class).withRel(rel);
	}
	
	public Link linkToCozinha(Long cozinhaId) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CozinhaController.class)
				.buscar(cozinhaId)).withSelfRel();
	}
	
	public Link linkToPermissoes(String rel) {
		return WebMvcLinkBuilder.linkTo(PermissaoController.class).withRel(rel);
	}
	
	public Link linkToPermissoes() {
		return linkToPermissoes(IanaLinkRelations.SELF.value());
	}
	
	public Link linkToGrupoPermissaoAssociacao(Long grupoId, String rel) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(GrupoPermissaoController.class)
				.add(grupoId, null)).withRel(rel);
	}
	
	public Link linkToGrupoPermissaoDesassociacao(Long grupoId, Long permissaoId, String rel) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(GrupoPermissaoController.class)
				.remove(grupoId, permissaoId)).withRel(rel);
	}

}
