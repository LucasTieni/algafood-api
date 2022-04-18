package com.algaworks.algafood.domain.service;

import java.io.InputStream;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.FotoProdutoNaoEncontradoException;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import com.algaworks.algafood.domain.service.FotoStorageService.NewPhoto;

@Service
public class CatalogoFotoProdutoService {
	
	@Autowired
	private FotoStorageService fotoStorageService;
	
	@Autowired
	private ProdutoRepository produtoRepository;

	@Transactional
	public FotoProduto salvar(FotoProduto foto, InputStream dadosArquivo) {
		Long restauranteId = foto.getRestauranteId();
		Long produtoId = foto.getProduto().getId();
		String newFileName = fotoStorageService.generateFileName(foto.getNomeArquivo());
		String existingFileName = null;
		
		Optional<FotoProduto> fotoExistente = produtoRepository.findFotoById(restauranteId, produtoId);
		
		if (fotoExistente.isPresent()) {
			existingFileName = fotoExistente.get().getNomeArquivo();
			produtoRepository.delete(fotoExistente.get());
			
		}
		
		foto.setNomeArquivo(newFileName);
		foto = produtoRepository.save(foto);
		produtoRepository.flush();
		
		NewPhoto newPhoto = NewPhoto.builder()
				.nomeArquivo(foto.getNomeArquivo())
				.inputStream(dadosArquivo)
				.contentType(foto.getContentType())
				.build();
		
		fotoStorageService.replace(existingFileName, newPhoto);
		
		return foto;
	}
	
	public void excluir(Long restauranteId, Long produtoId ) {
		FotoProduto foto = buscarOuFalhar(restauranteId, produtoId);
		
		produtoRepository.delete(foto);
		produtoRepository.flush();
		
		fotoStorageService.remove(foto.getNomeArquivo());
	}
	
	public FotoProduto buscarOuFalhar(Long restauranteId, Long produtoId) {
		return produtoRepository.findFotoById(restauranteId, produtoId)
				.orElseThrow(() -> new FotoProdutoNaoEncontradoException(restauranteId, produtoId));
	}
	
}


