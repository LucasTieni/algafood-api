package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.DTO.FormaPagamentoDTO;
import com.algaworks.algafood.api.controller.FormaPagamentoController;
import com.algaworks.algafood.domain.model.FormaPagamento;

@Component
public class FormaPagamentoDTOAssembler 
	extends RepresentationModelAssemblerSupport<FormaPagamento, FormaPagamentoDTO>{
	
	@Autowired  
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	public FormaPagamentoDTOAssembler() {
		super(FormaPagamentoController.class, FormaPagamentoDTO.class);
	}
	
	public FormaPagamentoDTO toModel(FormaPagamento formaPagamento) {
		FormaPagamentoDTO formaPagamentoDTO = createModelWithId(formaPagamento.getId(), formaPagamento);
		
		modelMapper.map(formaPagamento, formaPagamentoDTO);
		
		formaPagamentoDTO.add(algaLinks.linkToFormasPagamento("Forma-pagamento"));
		
		return formaPagamentoDTO;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
	}
	
	public CollectionModel<FormaPagamentoDTO> toCollectionDTO(Iterable<? extends FormaPagamento> entities){
		return super.toCollectionModel(entities)
				.add(algaLinks.linkToFormasPagamento());
	}
}

 
	