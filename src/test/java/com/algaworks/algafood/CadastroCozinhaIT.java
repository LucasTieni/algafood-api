package com.algaworks.algafood;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import org.apache.catalina.authenticator.SpnegoAuthenticator.AcceptAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.util.DatabaseCleaner;
import com.algaworks.algafood.util.ResourceUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
class CadastroCozinhaIT {
	
	private static final int COZINHA_ID_EXISTENTE = 100;
			
	private Cozinha cozinhaAmericana;
	private int numberOfRegistredKitchens;
	private String jsonCorrectChineseKitchen;
	
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private DatabaseCleaner databaseCleaner; 

	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@BeforeEach
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/cozinhas";
		
		databaseCleaner.clearTables();
		jsonCorrectChineseKitchen = ResourceUtils.getContentFromResource(
				"/json/right/cozinha-chinesa.json");
		
		prepareData();
	}
	
	@Test
	public void shouldReturnStatus200_WhenConsultKitchen() {
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void shouldContain2Kitchens() {
		
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.body("", hasSize(numberOfRegistredKitchens));
	}
	
	public void shouldReturnStatus201_WhenRegisterKitchen() {
		given()
			.body(jsonCorrectChineseKitchen)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
		}
	
	public void shouldReturnRightStatusResponse_WhenConsultNonxistingKitchen(){
		given()
			.pathParam("cozinhaId",COZINHA_ID_EXISTENTE)
			.accept(ContentType.JSON)
		.when()
			.get("/cozinhaId")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	public void shouldReturn404Status_WhenConsultExistingKitchen(){
		given()
			.pathParam("cozinhaId",cozinhaAmericana.getId())
			.accept(ContentType.JSON)
		.when()
			.get("/cozinhaId")
		.then()
			.statusCode(HttpStatus.OK.value())
		.body("nome", equalTo(cozinhaAmericana.getNome()));
	}
	
	private void prepareData() {
		Cozinha cozinha1 = new Cozinha();
		cozinha1.setNome("Tailandesa");
		cozinhaRepository.save(cozinha1);

		Cozinha cozinha2 = new Cozinha();
		cozinha2.setNome("Americana");
		cozinhaRepository.save(cozinha2);

		numberOfRegistredKitchens = (int) cozinhaRepository.count();
	}
	
}
