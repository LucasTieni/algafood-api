package com.algaworks.algafood.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.AlgaLinks;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class RootEntryPointController {
	
	@Autowired
	private AlgaLinks algaLinks;
	
	@GetMapping
	public RootEntryPointModel root() {
		var rootEntryPointModel = new RootEntryPointModel();
		rootEntryPointModel.add(algaLinks.linkToCozinha("Cozinhas"));
		rootEntryPointModel.add(algaLinks.linkToPedidos("Pedidos"));
		rootEntryPointModel.add(algaLinks.linkToRestaurantes("Restaurantes"));
		rootEntryPointModel.add(algaLinks.linkToGrupo("Grupos"));
		rootEntryPointModel.add(algaLinks.linkToUsuarios("Usuarios"));
		rootEntryPointModel.add(algaLinks.linkToPermissoes("Permissoes"));
		rootEntryPointModel.add(algaLinks.linkToFormasPagamento("FormasPagamento"));
		rootEntryPointModel.add(algaLinks.linkToEstado("Estados"));
		rootEntryPointModel.add(algaLinks.linkToCidades("Cidades"));
		
		return rootEntryPointModel;
	}
	
	private static class RootEntryPointModel extends RepresentationModel<RootEntryPointModel>{
		
	}
	
}
