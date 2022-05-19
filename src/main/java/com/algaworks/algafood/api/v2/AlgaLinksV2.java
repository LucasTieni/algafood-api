package com.algaworks.algafood.api.v2;

import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.controller.CozinhaController;
import com.algaworks.algafood.api.v2.controller.CidadeControllerV2;

@Component
public class AlgaLinksV2 {
	
	public Link linkToCidades(String rel) {
		return WebMvcLinkBuilder.linkTo(CidadeControllerV2.class).withRel(rel);
	}
	
	public Link linkToCidades() {
		return linkToCidades(IanaLinkRelations.SELF.value());
	}
	
	public Link linkToCozinha(String rel) {
		return WebMvcLinkBuilder.linkTo(CozinhaController.class).withRel(rel);
	}
	
	public Link linkToCozinha() {
		return linkToCozinha(IanaLinkRelations.SELF.value());
	}

}
