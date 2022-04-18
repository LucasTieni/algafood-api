package com.algaworks.algafood.domain.service;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.PedidoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.PedidoRepository;

@Service
public class EmitePedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private CadastroRestauranteService cadastroRestaurante;
	
	@Autowired
	private CadastroCidadeService cadastroCidade;
	
	@Autowired 
	private CadastroUsuarioService cadastroUsuario;
	
	@Autowired 
	private CadastroFormaPagamentoService cadastroFormaPagamento;
	
	@Autowired 
	private CadastroProdutoService cadastroProduto;
	
	@Transactional
	public Pedido salvar(Pedido pedido) {
		validarPedido(pedido);
		validarItens(pedido);
		
		pedido.setTaxaFrete(pedido.getRestaurante().getTaxaFrete());
		pedido.calcularValorTotal();
		
		return pedidoRepository.save(pedido);
	}


	private void validarPedido(Pedido pedido) {
		Long clientId = pedido.getCliente().getId(); 
		Long cityId = pedido.getEnderecoEntrega().getCidade().getId();
		Long restaurantId = pedido.getRestaurante().getId();
		Long formaPagamentoId = pedido.getFormaPagamento().getId();
		
		Cidade city = cadastroCidade.buscarOuFalhar(cityId);
		Usuario client = cadastroUsuario.buscarOuFalhar(clientId);
		Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restaurantId);
		FormaPagamento formaPagamento = cadastroFormaPagamento.buscarOuFalhar(formaPagamentoId);
		
		pedido.getEnderecoEntrega().setCidade(city);
		pedido.setCliente(client);
		pedido.setRestaurante(restaurante);
		pedido.setFormaPagamento(formaPagamento);
		
		if (restaurante.naoAceitaFormaPagamento(formaPagamento)){
			throw new NegocioException(String.format("Forma Pagamento %s não é aceita por esse restaurante.", 
				formaPagamento.getDescricao()));
		}
	}
	
	private void validarItens(Pedido pedido) {
		pedido.getItens().forEach(item -> {
			Produto produto = cadastroProduto.buscarOuFalhar(pedido.getRestaurante().getId(), item.getProduto().getId());
			
			item.setPedido(pedido);
			item.setProduto(produto);
			item.setPrecoUnitario(produto.getPreco());
		});
	}
	
	
	public Pedido buscarOuFalhar(String codigo) {
		return pedidoRepository.findByCodigo(codigo)
				.orElseThrow(() -> new PedidoNaoEncontradoException(codigo));
	}
}



