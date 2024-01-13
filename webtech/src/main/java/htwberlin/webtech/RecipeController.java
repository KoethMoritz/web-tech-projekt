package htwberlin.webtech;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RecipeController {

    @Autowired
    RecipeService service;

    @PostMapping("/recipe")
    @CrossOrigin(origins = "http://localhost:3000")
    public Recipe createRecipe(@RequestBody Recipe recipe) {
        return service.save(recipe);
    }

    @GetMapping("/recipe/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public Recipe getRecipe(@PathVariable String id) {
        Long recipeId = Long.parseLong(id);
        return service.get(recipeId);
    }

    @PostMapping("/save-recipe")
    @CrossOrigin(origins = "http://localhost:3000")
    public Recipe saveRecipe(@RequestParam String name, @RequestParam String description, @RequestParam int preparationTime) {
        return service.saveRecipe(name, description, preparationTime);
    }

    @DeleteMapping("/recipe/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> deleteRecipe(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok("Recipe deleted successfully");
    }

    @PutMapping("/recipe/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable Long id, @RequestBody Recipe updatedRecipe) {
        try {
            Recipe updated = service.update(id, updatedRecipe);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }


    @DeleteMapping("/recipe/{recipeId}/ingredient/{ingredientId}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> removeIngredient(@PathVariable Long recipeId, @PathVariable Long ingredientId) {
        try {
            Recipe updated = service.removeIngredient(recipeId, ingredientId);
            return ResponseEntity.ok("Ingredient removed successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/recipes")
    @CrossOrigin(origins = "http://localhost:3000")
    public List<Recipe> getAllRecipes() {
        return service.getAll();
    }

    @GetMapping("/recipes/search")
    @CrossOrigin(origins = "http://localhost:3000")
    public List<Recipe> searchRecipes(@RequestParam(required = false) String keyword) {
        return service.searchRecipes(keyword);
    }


}