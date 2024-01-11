package htwberlin.webtech;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecipeService {

    @Autowired
    RecipeRepository repo;

    public Recipe save(Recipe recipe) {
        return repo.save(recipe);
    }

    public Recipe get(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException());
    }

    public List<Recipe> getAll() {
        Iterable<Recipe> iterator = repo.findAll();
        List<Recipe> recipes = new ArrayList<>();
        for (Recipe recipe : iterator) {
            recipes.add(recipe);
        }

        recipes = recipes.stream()
                .sorted(Comparator.comparing(Recipe::getName))
                .collect(Collectors.toList());

        return recipes;
    }

    public Recipe saveRecipe(String name, String description, int preparationTime) {
        Recipe newRecipe = new Recipe(name, description, preparationTime);
        return save(newRecipe);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public Recipe update(Long id, Recipe updatedRecipe) {
        Recipe existingRecipe = get(id);

        if (existingRecipe != null) {
            existingRecipe.setName(updatedRecipe.getName());
            existingRecipe.setDescription(updatedRecipe.getDescription());
            existingRecipe.setPreparationTime(updatedRecipe.getPreparationTime());
            existingRecipe.setIngredients(updatedRecipe.getIngredients());

            return save(existingRecipe);
        } else {
            throw new RuntimeException("Recipe not found");
        }
    }

    public Recipe removeIngredient(Long recipeId, Long ingredientId) {
        Recipe existingRecipe = get(recipeId);

        if (existingRecipe != null) {
            List<Ingredient> ingredients = existingRecipe.getIngredients();
            ingredients.removeIf(ingredient -> ingredient.getIngredientId().equals(ingredientId));
            existingRecipe.setIngredients(ingredients);

            return save(existingRecipe);
        } else {
            throw new RuntimeException("Recipe not found");
        }
    }

    public List<Recipe> searchRecipes(String keyword) {
        if (keyword != null && !keyword.isEmpty()) {
            return repo.findAllByNameContainingIgnoreCase(keyword);
        } else {
            return getAll();
        }
    }


}