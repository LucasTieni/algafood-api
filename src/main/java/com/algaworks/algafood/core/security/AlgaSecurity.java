package com.algaworks.algafood.core.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.repository.UsuarioRepository;

@Component
public class AlgaSecurity {
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	};
	
	public Long getUsuarioId() {
		Jwt jwt = (Jwt) getAuthentication().getPrincipal();
		Long a = jwt.getClaim("usuario_id");
		
		return jwt.getClaim("usuario_id");
	}
	
	public boolean gerenciaRestaurante(Long restauranteId) {
	    if (restauranteId == null) {
	        return false;
	    }
	    return restauranteRepository.existsResponsavel(restauranteId, getUsuarioId());
	}
	
	public boolean gerenciaRestaurantePedido(String codigoPedido) {
		if (codigoPedido == null) {
	        return false;
	    }
	    Optional<Pedido> pedido = pedidoRepository.findByCodigo(codigoPedido);
	    
	    if (!pedido.isPresent()) {
	    	return false;
	    }
	    Usuario usario = usuarioRepository.getById(getUsuarioId());
	    return pedido.get().getRestaurante().getResponsaveis().contains(usario);
	    
	}
	
	
	public boolean usuarioAutenticadoIgual(Long usuarioId) {
		return getUsuarioId() != null && usuarioId != null
				&& getUsuarioId().equals(usuarioId);
	}
	
	
	public boolean hasAuthority(String authorityName) {
//		System.out.println(authorityName + " " + getAuthentication().getAuthorities().stream()
//				.anyMatch(authority -> authority.getAuthority().equals(authorityName)));
		
		return getAuthentication().getAuthorities().stream()
				.anyMatch(authority -> authority.getAuthority().equals(authorityName));
	}
	
	public boolean podeGerenciarPedidos(String codigoPedido) {
		return hasAuthority("SCOPE_WRITE") && (hasAuthority("GERENCIAR_PEDIDOS")
				|| gerenciaRestaurantePedido(codigoPedido));
	}
	
}
