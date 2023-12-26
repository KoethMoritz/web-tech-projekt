package htwberlin.webtech;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        for (Recipe recipe : iterator) recipes.add(recipe);
        return recipes;
    }

    public Recipe saveRecipe(String name, String description) {
        Recipe newRecipe = new Recipe(name, description);
        return save(newRecipe);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

}
