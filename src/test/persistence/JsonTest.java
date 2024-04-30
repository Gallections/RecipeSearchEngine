package persistence;

import model.Account;
import model.Recipe;
import model.DataBase;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class JsonTest {
    protected void checkRecipe(String name, ArrayList<String> ingredients,
                               ArrayList<String> steps, Recipe recipe) {
        assertEquals(name, recipe.getName());
        for (int i = 0; i < ingredients.size(); i++) {
            assertEquals(ingredients.get(i), recipe.getIngredients().get(i));
        }

        for (int i = 0; i < steps.size(); i++) {
            assertEquals(steps.get(i), recipe.getSteps().get(i));
        }
    }


}
