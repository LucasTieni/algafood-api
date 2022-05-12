package com.algaworks.algafood.api.DTO;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "Foto Produto")
@Getter
@Setter
public class FotoProdutoDTO extends RepresentationModel<FotoProdutoDTO>{
	
	private String nomeArquivo;
	private String descricao;
	private String contentType;
	private Long tamanho;
}
