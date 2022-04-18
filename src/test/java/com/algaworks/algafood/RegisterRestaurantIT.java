package com.algaworks.algafood;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.math.BigDecimal;

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
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.util.DatabaseCleaner;
import com.algaworks.algafood.util.ResourceUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class RegisterRestaurantIT {

	@LocalServerPort
	private int port;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
    private static final String VIOLACAO_DE_REGRA_DE_NEGOCIO_PROBLEM_TYPE = "Violação de regra de negócio";

    private static final String DADOS_INVALIDOS_PROBLEM_TITLE = "Dados inválidos";
	
	private static final int NONEXISTENT_ID_RESTAURANT = 100;
	
	private String jsonRightRestaurant;
	private String jsonRestaurantWithoutKitchen;
	private String jsonRestauranteWithNonexistentKitchen;
	private String jsonRestaurantWithoutFreightRate;
	
	private Restaurante burguerTopRestaurant;
	
	@BeforeEach
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/restaurantes";
		
		jsonRightRestaurant = ResourceUtils.getContentFromResource(
				"/json/right/new-york-barbecue-restaurant.json");
		
		jsonRestaurantWithoutKitchen = ResourceUtils.getContentFromResource(
				"/json/right/new-york-barbecue-restaurante-without-kitchen.json");
		
		jsonRestauranteWithNonexistentKitchen = ResourceUtils.getContentFromResource(
				"/json/right/new-york-barbecue-restaurant-with-nonexistent-kitchen.json");
		
		jsonRestaurantWithoutFreightRate = ResourceUtils.getContentFromResource(
				"/json/right/new-york-barbecue-restaurant-without-freight-rate.json");
		
		databaseCleaner.clearTables();
		prepareData();
	}
	
	@Test
	public void shouldReturnStatus200_WhenConsultRestaurants() {
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	public void shouldReturnStatus201_WhenRegisterRestaurant() {
		given()
			.body(jsonRightRestaurant)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
		}
	
	public void shouldReturn200Status_WhenConsultExistingRestaurant(){
		given()
			.pathParam("cozinhaId",burguerTopRestaurant.getId())
			.accept(ContentType.JSON)
		.when()
			.get("/cozinhaId")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("nome", equalTo(burguerTopRestaurant.getNome()));
	}
	
	public void shouldReturn404Status_WhenConsultNonexistingRestaurant(){
		given()
			.pathParam("cozinhaId",NONEXISTENT_ID_RESTAURANT)
			.accept(ContentType.JSON)
		.when()
			.get("/cozinhaId")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	public void shouldReturn404Status_whenRegisterRestaurantWithNonexistentKitchen() {
		given()
			.body(jsonRestauranteWithNonexistentKitchen)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.body("title", equalTo(VIOLACAO_DE_REGRA_DE_NEGOCIO_PROBLEM_TYPE));
	}
	
	public void shouldReturn404Status_whenRegisterRestaurantWithoutKitchen() {
		given()
			.body(jsonRestaurantWithoutKitchen)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.body("title", equalTo(DADOS_INVALIDOS_PROBLEM_TITLE));
	}
	
	public void shouldReturn404Status_whenRegisterRestaurantWithoutFreigthRate() {
		given()
			.body(jsonRestaurantWithoutFreightRate)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.body("title", equalTo(DADOS_INVALIDOS_PROBLEM_TITLE));
	}
	
	private void prepareData() {
		
        Cozinha brazilainFood = new Cozinha();
        brazilainFood.setNome("Brasileira");
        cozinhaRepository.save(brazilainFood);

        Cozinha americanFood = new Cozinha();
        americanFood.setNome("Americana");
        cozinhaRepository.save(americanFood);
        
        burguerTopRestaurant = new Restaurante();
        burguerTopRestaurant.setNome("Burguer Top");
        burguerTopRestaurant.setTaxaFrete(new BigDecimal(10));
        burguerTopRestaurant.setCozinha(americanFood);
        restauranteRepository.save(burguerTopRestaurant);
        
        Restaurante MineiraFoodRestaurant = new Restaurante();
        MineiraFoodRestaurant.setNome("Comida Mineira");
        MineiraFoodRestaurant.setTaxaFrete(new BigDecimal(10));
        MineiraFoodRestaurant.setCozinha(brazilainFood);
        restauranteRepository.save(MineiraFoodRestaurant);
        
	}
	
}
