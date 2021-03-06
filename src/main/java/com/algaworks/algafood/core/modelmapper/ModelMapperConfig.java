package com.algaworks.algafood.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algaworks.algafood.api.v1.DTO.EnderecoDTO;
import com.algaworks.algafood.api.v1.DTO.input.ItemPedidoInput;
import com.algaworks.algafood.api.v2.model.input.CidadeInputV2;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Endereco;
import com.algaworks.algafood.domain.model.ItemPedido;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper(){
		var modelMapper = new ModelMapper();
		
//		modelMapper.createTypeMap(Restaurante.class, RestauranteDTO.class)
//			.addMapping(Restaurante::getTaxaFrete, RestauranteDTO::setPrecoFrete);
		
		modelMapper.createTypeMap(CidadeInputV2.class, Cidade.class)
    		.addMappings(mapper -> mapper.skip(Cidade::setId));
		
		modelMapper.createTypeMap(ItemPedidoInput.class, ItemPedido.class)
	    	.addMappings(mapper -> mapper.skip(ItemPedido::setId)); 
		
		var enderecoToEndereDTOTypeMap = modelMapper.createTypeMap(Endereco.class, EnderecoDTO.class);
				
		enderecoToEndereDTOTypeMap.<String>addMapping(
				enderecoSrc -> enderecoSrc.getCidade().getEstado().getNome(),
				(enderecoDest, value) -> enderecoDest.getCidade().setEstado(value));
		
		return modelMapper;
	}
}
