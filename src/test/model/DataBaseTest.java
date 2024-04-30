package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DataBaseTest {
    Recipe recipe1;
    Recipe recipe2;


    @BeforeEach
    void setup() {
        recipe1 = new Recipe("mashed potatoes");
        recipe2 = new Recipe("Poached egg");
    }

    @Test
    void testConstructor() {
        DataBase.erase();
        DataBase d = new DataBase();
        assertEquals(d.getSize(), 0);
    }

    @Test
    void addRecipeTest() {
        assertEquals(DataBase.returnRecipes().size(), DataBase.getSize());

        int size = DataBase.getSize();

        DataBase.addRecipe(recipe1);
        assertEquals(DataBase.returnRecipes().size(), 1 + size);
        assertEquals(DataBase.returnRecipes().get(size), recipe1);

        DataBase.addRecipe(recipe2);
        assertEquals(DataBase.returnRecipes().size(), 2 + size);
        assertEquals(DataBase.returnRecipes().get(size), recipe1);
        assertEquals(DataBase.returnRecipes().get(size + 1), recipe2);
    }

    @Test
    void removeRecipeTest() {
        assertEquals(DataBase.returnRecipes().size(), DataBase.getSize());

        DataBase.addRecipe(recipe1);
        DataBase.addRecipe(recipe2);
        int size = DataBase.getSize();

        DataBase.removeRecipe(recipe2);
        assertEquals(DataBase.returnRecipes().size(), size - 1);
        assertFalse(DataBase.returnRecipes().contains(recipe2));

        DataBase.removeRecipe(recipe1);
        assertEquals(DataBase.returnRecipes().size(),  size - 2);
        assertFalse(DataBase.returnRecipes().contains(recipe1));

        DataBase.addRecipe(recipe1);
        DataBase.addRecipe(recipe2);

        DataBase.removeRecipe(recipe1);
        assertEquals(DataBase.returnRecipes().size(), size - 1);
        assertFalse(DataBase.returnRecipes().contains(recipe1));

        DataBase.removeRecipe(recipe2);
        assertEquals(DataBase.returnRecipes().size(),  size - 2);
        assertFalse(DataBase.returnRecipes().contains(recipe2));
    }

    @Test
    void testErase() {
        DataBase.addRecipe(recipe1);
        DataBase.addRecipe(recipe2);
        assertEquals(DataBase.getSize(), 2);

        DataBase.erase();
        assertEquals(DataBase.getSize(), 0);

    }
}
