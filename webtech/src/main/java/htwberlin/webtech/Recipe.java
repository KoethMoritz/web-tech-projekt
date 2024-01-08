package htwberlin.webtech;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(length = 100000)
    private String description;

    private int preparationTime;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "recipe_id")
    private List<Ingredient> ingredients;

    public Recipe() {}

    public Recipe(String name, String description, int preparationTime) {
        this.name = name;
        this.description = description;
        this.preparationTime = preparationTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(int preparationTime) {
        this.preparationTime = preparationTime;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Recipe)) return false;

        Recipe recipe = (Recipe) o;

        if (getId() != null ? !getId().equals(recipe.getId()) : recipe.getId() != null) return false;
        if (getName() != null ? !getName().equals(recipe.getName()) : recipe.getName() != null) return false;
        if (getDescription() != null ? !getDescription().equals(recipe.getDescription()) : recipe.getDescription() == null)
            return false;
        return getPreparationTime() == recipe.getPreparationTime();
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = result + (getName() != null ? getName().hashCode() : 0);
        result = result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = result + getPreparationTime();
        return result;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", preparationTime=" + preparationTime +
                '}';
    }
}