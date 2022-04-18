package com.algaworks.algafood.infrastructure.service.storage;

import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;

import com.algaworks.algafood.core.storage.StorageProperties;
import com.algaworks.algafood.domain.service.FotoStorageService;

public class LocalFotoStorageService implements FotoStorageService {
	
	@Autowired
	private StorageProperties storageProperties; 
	
	@Override
	public void storage(NewPhoto newPhoto) {
		try {
			Path pathFile = getFilePath(newPhoto.getNomeArquivo()); 
			
			FileCopyUtils.copy(newPhoto.getInputStream(), 
					Files.newOutputStream(pathFile));
		} catch (Exception e) {
			throw new StorageException(e.getMessage(),e);//"Não foi possível armazenar arquivo.", e);
		}
	}
	
	@Override
	public void remove(String fileName) {
		try {
			Path filePath = getFilePath(fileName);
			Files.deleteIfExists(filePath);
			
		} catch (Exception e) {
			throw new StorageException("Não foi possível excluir arquivo.", e);
		}
	}
	
	private Path getFilePath(String nomeArquivo) {
		return storageProperties.getLocal().getDiretorioFotos()
				.resolve(Path.of(nomeArquivo));
	}

//	@Override
//	public void replace(String existingFileName, NewPhoto newPhoto) {
//		this.storage(newPhoto);
//		
//		if (existingFileName != null) {
//			this.remove(existingFileName);
//		}
//	}

	@Override
	public RecoveredPhoto recover(String fileName) {
		try {
			Path pathFile = getFilePath(fileName);
			
			RecoveredPhoto recoveredPhoto = RecoveredPhoto.builder()
					.inputStream(Files.newInputStream(pathFile))
					.build();
			
			return recoveredPhoto;
		} catch (Exception e) {
			throw new StorageException("Não foi possível recuperars arquivo.", e);
		}
	}
	
}
