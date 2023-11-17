package htwberlin.webtech;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class RecipeController {

    @Autowired
    RecipeService service;

    @PostMapping("/recipe")
    public Recipe createRecipe(@RequestBody Recipe recipe) {
        return service.save(recipe);
    }

    @GetMapping("/recipe/{id}")
    public Recipe getRecipe(@PathVariable String id) {
        Long recipeId = Long.parseLong(id);
        return service.get(recipeId);
    }

}
