package htwberlin.webtech;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
class WebtechApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void testCreateRecipe() {
		Recipe newRecipe = new Recipe("Test Recipe", "Test Description", 30);
		ResponseEntity<Recipe> response = restTemplate.postForEntity("/recipe", newRecipe, Recipe.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Test Recipe", response.getBody().getName());

		// Additional test for checking if the created recipe has an ID
		assertNotNull(response.getBody().getId());
	}

	@Test
	void testUpdateRecipe() {
		Recipe existingRecipe = restTemplate.getForObject("/recipe/1", Recipe.class);
		existingRecipe.setDescription("Updated Description");
		ResponseEntity<Recipe> response = restTemplate.exchange("/recipe/1", HttpMethod.PUT, new HttpEntity<>(existingRecipe), Recipe.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Updated Description", response.getBody().getDescription());
	}

	@Test
	void testDeleteRecipe() {
		// Assuming there is at least one recipe in the database
		ResponseEntity<String> response = restTemplate.exchange("/recipe/1", HttpMethod.DELETE, null, String.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Recipe deleted successfully", response.getBody());
	}

	@Test
	void testRemoveIngredient() {
		// Assuming there is at least one recipe and one ingredient in the database
		ResponseEntity<String> response = restTemplate.exchange("/recipe/1/ingredient/1", HttpMethod.DELETE, null, String.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Ingredient removed successfully", response.getBody());
	}

	@Test
	void testGetAllRecipes() {
		ResponseEntity<Recipe[]> response = restTemplate.getForEntity("/recipes", Recipe[].class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(1, response.getBody().length); // Assuming there is at least one recipe in the database

		// Additional test for checking if the returned list is not empty
		assertNotNull(response.getBody());
		assertEquals(1, response.getBody().length);
	}

	@Test
	void testSearchRecipes() {
		// Assuming there is at least one recipe in the database
		ResponseEntity<Recipe[]> response = restTemplate.getForEntity("/recipes/search?keyword=Test", Recipe[].class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
	}

	@Test
	void testSaveRecipe() {
		ResponseEntity<Recipe> response = restTemplate.postForEntity("/save-recipe?name=Test&description=Test&preparationTime=30", null, Recipe.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());

		// Additional test for checking if the saved recipe has an ID
		assertNotNull(response.getBody().getId());
	}
}
