package com.algaworks.algafood.core.openapi;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.fasterxml.classmate.TypeResolver;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RepresentationBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Response;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;


@Configuration
//@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig {
	
	@Bean
	  public Docket apiDocket() {
	    var typeReference = new TypeResolver(); 
		
		return new Docket(DocumentationType.OAS_30)
	        .select()
	          .apis(RequestHandlerSelectors.basePackage("com.algaworks.algafood"))
	          .paths(PathSelectors.any())
//	          .paths(PathSelectors.ant("/restaurantes/*"))
	          .build()
	        .useDefaultResponseMessages(false)
	        .globalResponses(HttpMethod.GET, globalGetResponseMessages())
	        .globalResponses(HttpMethod.PUT, globalPutResponseMessages())
	        .globalResponses(HttpMethod.POST, globalPostResponseMessages())
	        .globalResponses(HttpMethod.DELETE, globalDeleteResponseMessages())
	        .additionalModels(typeReference.resolve(Problem.class))
	        .apiInfo(apiInfo())
	        .tags(new Tag("Cidades", "Gerencia as cidades"));
	  }
	  
	private List<Response> globalGetResponseMessages(){
		  return Arrays.asList(
				  new ResponseBuilder()
				  	.code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
				  	.description("Erro interno do servidor")
				  	.representation(MediaType.APPLICATION_JSON)
				  	.apply(getProblemaModelReference())
				  	.build(),
				  new ResponseBuilder()
				  	.code(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()))
				  	.description("Recurso não possui representação que poderia ser aceita pelo consumidor")
				  	.build());
	  }
	
	private List<Response> globalPostResponseMessages(){
		  return Arrays.asList(
				  new ResponseBuilder()
				  	.code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
				  	.description("Erro interno do servidor")
				  	.representation(MediaType.APPLICATION_JSON)
				  	.apply(getProblemaModelReference())
				  	.build(),
				  new ResponseBuilder()
				  	.code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
				  	.description("Algo errado na requisição")
				  	.representation(MediaType.APPLICATION_JSON)
				  	.apply(getProblemaModelReference())
				  	.build(),
				  new ResponseBuilder()
				  	.code(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()))
				  	.description("Recurso não possui representação que poderia ser aceita pelo consumidor")
				  	.build(),
				  new ResponseBuilder()
				  	.code(String.valueOf(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()))
				  	.description("Formato de requisição inválido")
				  	.representation(MediaType.APPLICATION_JSON)
				  	.apply(getProblemaModelReference())
				  	.build());
	  }
	
		private List<Response> globalPutResponseMessages(){
			  return Arrays.asList(
					  new ResponseBuilder()
					  	.code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
					  	.description("Erro interno do servidor")
					  	.representation(MediaType.APPLICATION_JSON)
					  	.apply(getProblemaModelReference())
					  	.build(),
					  new ResponseBuilder()
					  	.code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
					  	.description("Algo errado na requisição")
					  	.representation(MediaType.APPLICATION_JSON)
					  	.apply(getProblemaModelReference())
					  	.build(),
					  new ResponseBuilder()
					  	.code(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()))
					  	.description("Recurso não possui representação que poderia ser aceita pelo consumidor")
					  	.build(),
					  new ResponseBuilder()
					  	.code(String.valueOf(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()))
					  	.description("Formato de requisição inválido")
					  	.representation(MediaType.APPLICATION_JSON)
					  	.apply(getProblemaModelReference())
					  	.build());
		  }
		
		private List<Response> globalDeleteResponseMessages(){
			  return Arrays.asList(
					  new ResponseBuilder()
					  	.code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
					  	.description("Erro interno do servidor")
					  	.build(),
					  new ResponseBuilder()
					  	.code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
					  	.description("Algo errado na requisição")
					  	.build());
		  }
		
		private Consumer<RepresentationBuilder> getProblemaModelReference() {
		    return r -> r.model(m -> m.name("Problema")
		            .referenceModel(ref -> ref.key(k -> k.qualifiedModelName(
		                    q -> q.name("Problema").namespace("com.algaworks.algafood.api.exceptionhandler")))));
		}	
	
	  public ApiInfo apiInfo() {
		  return new ApiInfoBuilder()
				  .title("AlgaFood API")
				  .description("API aberta para clientes e restaurantes")
				  .version("1")
				  .contact(new Contact("LucasTieni", "https://www.google.com", "lucastieni@gmail.com"))
				  .build();
	  }
}
