package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.DTO.ProdutoDTO;
import com.algaworks.algafood.api.DTO.input.ProdutoInput;
import com.algaworks.algafood.api.assembler.ProdutoAssembler;
import com.algaworks.algafood.api.assembler.ProdutoDTOAssembler;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.ProdutoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import com.algaworks.algafood.domain.service.CadastroProdutoService;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private CadastroProdutoService cadastroProduto;
	
	@Autowired
	private ProdutoAssembler produtoAssembler;
	
	@Autowired
	private ProdutoDTOAssembler produtoDTOAssembler;
	
	@Autowired
	private CadastroRestauranteService cadastroRestaurante;
	
	@GetMapping
	public List<ProdutoDTO> listar(@PathVariable Long restauranteId, @RequestParam(required = false) boolean incluirInativos) {
		Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);
		
		List<Produto> allProducts = null;
		
		if (incluirInativos) {
			allProducts = produtoRepository.findByRestaurante(restaurante);
		} else {
			allProducts = produtoRepository.findAtivosByRestaurante(restaurante);
		}
		
		return produtoDTOAssembler.toCollectionDTO(allProducts);
	}

	@ResponseStatus(HttpStatus.CREATED)
	@GetMapping("/{produtoId}")
	public ProdutoDTO buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		Produto produto = cadastroProduto.buscarOuFalhar(restauranteId, produtoId);
		return produtoDTOAssembler.toModel(produto);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProdutoDTO adicionar (@PathVariable Long restauranteId, @RequestBody @Valid ProdutoInput produtoInput){
		try {
			Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);
			
			Produto produto = produtoAssembler.toDomainObject(produtoInput);
			produto.setRestaurante(restaurante);
			
			return produtoDTOAssembler.toModel(cadastroProduto.salvar(produto));
		} catch (RestauranteNaoEncontradoException | ProdutoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	@PutMapping("/{produtoId}")
	public ProdutoDTO atualizar(@PathVariable Long restauranteId, @PathVariable Long produtoId,
			@RequestBody @Valid ProdutoInput produtoInput) {
		Produto produtoAtual = cadastroProduto.buscarOuFalhar(restauranteId, produtoId);
		
		produtoAssembler.copyToDomainObject(produtoInput, produtoAtual);
		
		try {
			return produtoDTOAssembler.toModel(cadastroProduto.salvar(produtoAtual));
		} catch (RestauranteNaoEncontradoException | ProdutoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage());
		}
	}

//	@DeleteMapping("/{id}")
//	public ResponseEntity<Produto> remover(@PathVariable Long id){
//		try {
//			cadastroProduto.excluir(id);
//
//			return ResponseEntity.noContent().build();
//
//		} catch (EntidadeNaoEncontradaException e) {
//			return ResponseEntity.notFound().build();
//
//		} catch (EntidadeEmUsoException e) {
//			return ResponseEntity.status(HttpStatus.CONFLICT).build();
//
//		}
//	}
}
