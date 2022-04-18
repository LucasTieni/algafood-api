package com.algaworks.algafood.core.validation;

import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.web.multipart.MultipartFile;

public class FileContentTypeValidator implements ConstraintValidator<FileContentType, MultipartFile>{
	
	private List<String> allowedTypes;
	
	@Override
	public void initialize(FileContentType constraintAnnotation) {
		this.allowedTypes = Arrays.asList(constraintAnnotation.allowed());
	}
	
	@Override
	public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
		return value == null || 
				this.allowedTypes.contains(value.getContentType());
	}
	
	
}
