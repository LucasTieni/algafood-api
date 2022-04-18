package com.algaworks.algafood.infrastructure.service.storage;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;

import com.algaworks.algafood.core.storage.StorageProperties;
import com.algaworks.algafood.domain.service.FotoStorageService;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;

public class S3FotoStorageService  implements FotoStorageService{
	
	@Autowired
	private AmazonS3 amazonS3;
	
	@Autowired
	private StorageProperties storageProperties;
	
	@Override
	public RecoveredPhoto recover(String fileName) {
		String pathFile = getFilePath(fileName);
		
		URL url = amazonS3.getUrl(storageProperties.getS3().getBucket(), pathFile);
		
		RecoveredPhoto recoveredPhoto = RecoveredPhoto.builder()
				.url(url.toString()).build();
		
		return recoveredPhoto;
	}

	@Override
	public void storage(NewPhoto newPhoto) {
		try {
			InputStream inputStream = newPhoto.getInputStream();
			
			byte[] newPhotoBytes = IOUtils.toByteArray(inputStream);
			
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(newPhotoBytes);
			
			String filePath = getFilePath(newPhoto.getNomeArquivo());
			
			var objectMetadata = new ObjectMetadata();
			objectMetadata.setContentType(newPhoto.getContentType());
			objectMetadata.setContentLength(newPhotoBytes.length);
			
			var putObjectRequest = new PutObjectRequest(
					storageProperties.getS3().getBucket(),
					filePath,
					byteArrayInputStream,
					objectMetadata)
					.withCannedAcl(CannedAccessControlList.PublicRead);
			
				amazonS3.putObject(putObjectRequest);
		} catch (Exception e) {
			throw new StorageException("Não foi possível enviar arquivo para Amazon S3", e);
		}
	}

	private String getFilePath(String nomeArquivo) {
		
		return String.format("%s/%s",storageProperties.getS3().getDiretorioFotos(), nomeArquivo);
	}

	@Override
	public void remove(String fileName) {
		try {
			String filePath = getFilePath(fileName);
			
			var deleteObjectRequest = new DeleteObjectRequest(
					storageProperties.getS3().getBucket(), filePath);
			
			amazonS3.deleteObject(deleteObjectRequest);
		} catch (Exception e) {
			throw new StorageException("Não foi possível excluir arquivo na Amazon S3.", e);
		}		
		
	}

	
}
