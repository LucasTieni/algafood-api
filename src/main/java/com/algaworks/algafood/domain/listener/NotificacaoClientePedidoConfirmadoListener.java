package com.algaworks.algafood.domain.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.domain.event.PedidoConfirmadoEvent;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.service.SendEmailService;
import com.algaworks.algafood.domain.service.SendEmailService.Message;

@Component
public class NotificacaoClientePedidoConfirmadoListener {

	@Autowired
	private SendEmailService envioEmail;
	
	@EventListener
	public void aoConfirmarPedido(PedidoConfirmadoEvent event) {
		
		System.out.println("aoConfirmarPedido");
		Pedido pedido = event.getPedido();
		
		var mensagem = Message.builder()
				.assunto(pedido.getRestaurante().getNome() + " - Pedido confirmado")
				.corpo("pedido-confirmado.html")
				.variavel("pedido", pedido)
				.destinatario(pedido.getCliente().getEmail())
				.build();

		envioEmail.enviar(mensagem);
	}
	
}