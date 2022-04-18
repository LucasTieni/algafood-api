package com.algaworks.algafood.domain.service;

import java.io.InputStream;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

public interface FotoStorageService {
	
	RecoveredPhoto recover(String fileName);
	
	void storage(NewPhoto newPhoto);
	
	void remove(String fileName);
	
	default void replace(String existingFileName, NewPhoto newPhoto) {
		this.storage(newPhoto);
		
		if (existingFileName != null) {
			this.remove(existingFileName);
		}
	};
	
	default String generateFileName(String originalName) {
		return UUID.randomUUID().toString() + "_" + originalName;
	}
	
	@Builder
	@Getter
	class NewPhoto{
		private String nomeArquivo;
		private InputStream inputStream;
		private String contentType;
	}
	
	@Builder
	@Getter
	class RecoveredPhoto{
		private InputStream inputStream;
		private String url;
		
		public boolean hasUrl() {
			return url != null;
		}
		
		public boolean hasInputStream() {
			return inputStream != null;
		}
		
	}
}
