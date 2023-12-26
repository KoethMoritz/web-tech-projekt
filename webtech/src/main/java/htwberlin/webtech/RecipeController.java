package htwberlin.webtech;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public Recipe saveRecipe(@RequestParam String name, @RequestParam String description) {
        return service.saveRecipe(name, description);
    }

    @DeleteMapping("/recipe/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> deleteRecipe(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok("Recipe deleted successfully");
    }

}
