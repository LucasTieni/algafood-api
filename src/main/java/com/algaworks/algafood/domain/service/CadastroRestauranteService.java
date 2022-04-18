package com.algaworks.algafood.domain.service;


import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	
	@Autowired 
	private CadastroCidadeService cadastroCidade;
	
	@Autowired
	private CadastroFormaPagamentoService cadastroFormaPagamento;
	
	@Autowired
	private CadastroUsuarioService cadastroUsuario;
	
	@Transactional
	public Restaurante salvar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();
		Long cidadeId = restaurante.getEndereco().getCidade().getId();				
		
		Cozinha cozinha = cadastroCozinha.buscarOuFalhar(cozinhaId);
		Cidade cidade = cadastroCidade.buscarOuFalhar(cidadeId);
		
		restaurante.setCozinha(cozinha);
		restaurante.getEndereco().setCidade(cidade);
		
		return restauranteRepository.save(restaurante);
	}
	
	@Transactional
	public void excluir(Long id) {
		try {
			restauranteRepository.deleteById(id);
			restauranteRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new RestauranteNaoEncontradoException(id);

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Restaurante de código %d não pode ser removida, pois está em uso.", id));
		}
	}
	
	@Transactional
	public void activate(Long restauranteId) {
		Restaurante restauranteAtual = buscarOuFalhar(restauranteId);
		restauranteAtual.activate();
	}
	
	@Transactional
	public void deactivate(Long restauranteId) {
		Restaurante restauranteAtual = buscarOuFalhar(restauranteId);
		restauranteAtual.deactivate();
	}
	
	@Transactional
	public void connectPaymentMode(Long restauranteId, Long formaPagamentoId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		FormaPagamento formaPagamento = cadastroFormaPagamento.buscarOuFalhar(formaPagamentoId);
		
		restaurante.addPaymentMode(formaPagamento);
	}
	
	@Transactional
	public void disconnectPaymentMode(Long restauranteId, Long formaPagamentoId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		FormaPagamento formaPagamento = cadastroFormaPagamento.buscarOuFalhar(formaPagamentoId);
		
		restaurante.removePaymentMode(formaPagamento);
	}
	
	@Transactional
	public void open(Long restauranteId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		restaurante.open();
	}
	
	@Transactional
	public void close(Long restauranteId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		restaurante.close();
	}
	
	@Transactional
	public void addUser(Long restauranteId, Long userId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		Usuario usuario = cadastroUsuario.buscarOuFalhar(userId);
		restaurante.addUser(usuario);
	}
	
	@Transactional
	public void removeUser(Long restauranteId, Long userId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		Usuario usuario = cadastroUsuario.buscarOuFalhar(userId);
		restaurante.removeUser(usuario);
	}
	
	@Transactional
	public void activate(List<Long> restauranteIds) {
		restauranteIds.forEach(this::activate);
	}
	
	@Transactional
	public void deactivate(List<Long> restauranteIds) {
		restauranteIds.forEach(this::deactivate);
	}
	
	public Restaurante buscarOuFalhar(Long restauranteId) {
	
		return restauranteRepository.findById(restauranteId)
				.orElseThrow(() -> new RestauranteNaoEncontradoException(restauranteId));
	}
}



