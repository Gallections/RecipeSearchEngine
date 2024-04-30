package model;

import org.json.JSONArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;


class AccountTest {
    // delete or rename this class!
    Account account;
    Recipe recipe1;
    Recipe recipe2;
    Recipe recipe3;
    Recipe recipe4;
    Recipe recipe5;

    @BeforeEach
    void setup() {
        account = new Account("John");
        recipe1 = new Recipe(" super mac and cheese");
        recipe2 = new Recipe("super mashed potatoes");
        recipe3 = new Recipe("Mac and Cheese",
                new ArrayList<>(Arrays.asList("macaroni", "butter", "flour", "cheese", "seasonings",
                        "bread Crumbs")),
                new ArrayList<>(Arrays.asList("Boil the noodles, drain, and transfer to a prepared baking dish.",
                        "Make the cheese sauce, pour the sauce over the noodles, and stir.",
                        "Make the topping, spread it over macaroni and cheese, and sprinkle with paprika.",
                        "Bake the mac and cheese until the topping is golden brown.")));
        recipe4 = new Recipe("Fried Rice",
                new ArrayList<>(Arrays.asList("baby carrots", "frozen green peas", "vegetable oil",
                        "garlic", "eggs", "white rice", "soy sauce", "sesame oil")),
                new ArrayList<>(Arrays.asList("Assemble ingredients.",
                        "Place carrots in a small saucepan and cover with water. "
                                + "Bring to a low boil and cook for 3 to 5 minutes. Stir in peas, "
                                + "then immediately drain in a colander.",
                        "Heat a wok over high heat. Pour in vegetable oil, then stir in carrots, peas, "
                                + "and garlic; cook for about 30 seconds. Add eggs; stir quickly to "
                                + "scramble eggs with vegetables.",
                        "Stir in cooked rice. Add soy sauce and toss rice to coat. "
                                + "Drizzle with sesame oil and toss again."
                )));
        recipe5 = new Recipe("Mashed Potatoes",
                new ArrayList<>(Arrays.asList("baking potatoes",
                        "garlic", "milk", "butter", "ground black pepper", "salt")),
                new ArrayList<>(Arrays.asList("Bring a large pot of salted water to a boil. "
                                + "Add peeled potatoes and peeled garlic, lower heat to medium, "
                                + "and simmer until potatoes are tender, 15 to 20 minutes.",
                        "When the potatoes are almost finished, "
                                + "heat milk and 3 table spoon of butter in a small saucepan "
                                + "over low heat until butter is "
                                + "melted.",
                        "Drain potatoes and return to the pot. Slowly add warm milk mixture, "
                                + "blending it in with a potato masher or electric mixer until potatoes "
                                + "are smooth and creamy. Season with salt and pepper.")));
    }

    @Test
    void testConstructor() {
//        assertEquals(account.getUserID(), 17);
        assertEquals(account.getUserName(), "John");
        assertTrue(account.getCreatedRecipes().isEmpty());
        assertTrue(account.getSavedRecipes().isEmpty());
    }

    @Test
    void createRecipeTest() {
        assertTrue(account.getCreatedRecipes().isEmpty());
        int size = DataBase.getSize();

        String ingredients = "macaroni, milk, butter, seasonings, flour";
        ArrayList<String> steps = new ArrayList<>(Arrays.asList(
                "Boil the noodles, drain, and transfer to a prepared baking dish.",
                "Make the cheese sauce, pour the sauce over the noodles, and stir.",
                "Make the topping, spread it over macaroni and cheese, and sprinkle with paprika.",
                "Bake the mac and cheese until the topping is golden brown."
        ));

        account.createRecipe("super mac and cheese", ingredients, steps);

        assertFalse(account.getCreatedRecipes().isEmpty());
        assertEquals(account.getCreatedRecipes().size(), 1);
        assertEquals(account.getCreatedRecipes().get(0).getName(), "super mac and cheese");
        assertEquals(account.getCreatedRecipes().get(0).getIngredients(),
                new ArrayList<>(Arrays.asList("macaroni", "milk", "butter", "seasonings", "flour"))
                );
        assertEquals(account.getCreatedRecipes().get(0).getSteps(), steps);

        assertEquals(DataBase.getSize(), 1 + size);
        assertEquals(DataBase.returnRecipes().get(size).getName(), "super mac and cheese");
        assertEquals(DataBase.returnRecipes().get(size).getIngredients(),
                new ArrayList<>(Arrays.asList("macaroni", "milk", "butter", "seasonings", "flour"))
            );
        assertEquals(DataBase.returnRecipes().get(size).getSteps(), steps);
    }

    @Test
    void deleteRecipeTest() {
        assertTrue(account.getCreatedRecipes().isEmpty());
        int size = DataBase.getSize();

        String ingredients = "macaroni, milk, butter, seasonings, flour";
        ArrayList<String> steps = new ArrayList<>(Arrays.asList(
                "Boil the noodles, drain, and transfer to a prepared baking dish.",
                "Make the cheese sauce, pour the sauce over the noodles, and stir.",
                "Make the topping, spread it over macaroni and cheese, and sprinkle with paprika.",
                "Bake the mac and cheese until the topping is golden brown."
        ));

        account.createRecipe("super mac and cheese", ingredients, steps);
        assertFalse(account.getCreatedRecipes().isEmpty());

        account.deleteRecipe(account.getCreatedRecipes().get(0));
        assertTrue(account.getCreatedRecipes().isEmpty());
    }

    @Test
    void addSavedRecipeTest() {
        assertTrue(account.getSavedRecipes().isEmpty());

        account.addSavedRecipe(recipe1);
        assertEquals(account.getNumSavedRecipes(), 1);
        assertEquals(account.getSavedRecipes().get(0), recipe1);
        assertEquals(recipe1.getNumSaves(), 1);

        account.addSavedRecipe(recipe2);
        assertEquals(account.getNumSavedRecipes(), 2);
        assertEquals(account.getSavedRecipes().get(1), recipe2);
        assertEquals(recipe2.getNumSaves(), 1);
    }

    @Test
    void removeSavedRecipeTest() {
        assertTrue(account.getSavedRecipes().isEmpty());

        account.addSavedRecipe(recipe1);
        account.addSavedRecipe(recipe2);
        assertEquals(account.getNumSavedRecipes(), 2);
        assertEquals(recipe1.getNumSaves(), 1);
        assertEquals(recipe2.getNumSaves(), 1);

        account.removeSavedRecipe(recipe1);
        assertEquals(account.getNumSavedRecipes(), 1);
        assertEquals(account.getSavedRecipes().get(0), recipe2);
        assertEquals(recipe1.getNumSaves(), 0);

        account.removeSavedRecipe(recipe2);
        assertEquals(account.getNumSavedRecipes(), 0);
        assertEquals(recipe2.getNumSaves(), 0);

        account.addSavedRecipe(recipe1);
        account.addSavedRecipe(recipe2);

        account.removeSavedRecipe(recipe2);
        assertEquals(account.getNumSavedRecipes(), 1);
        assertEquals(account.getSavedRecipes().get(0), recipe1);
        assertEquals(recipe1.getNumSaves(), 1);
    }

    @Test
    void searchRecipeByNameTest() {
        ArrayList<Recipe> recipes = new ArrayList<>(Arrays.asList(recipe1, recipe2, recipe3, recipe4));
        ArrayList<Recipe> trackingRecipes = new ArrayList<>();

        // Situation 1: When there are two recipes that contain similar keywords
        account.searchRecipeByName("super mac and cheese", trackingRecipes, recipes);
        assertEquals(trackingRecipes.size(), 2);
        assertTrue(trackingRecipes.contains(recipe1));
        assertTrue(trackingRecipes.contains(recipe3));
        assertEquals(trackingRecipes.get(0), recipe1);
        assertEquals(trackingRecipes.get(1), recipe3);

        // Situation 2: There is one unique recipe containing the keyword
        trackingRecipes.clear();
        account.searchRecipeByName("rice", trackingRecipes, recipes);
        assertEquals(trackingRecipes.size(), 1);
        assertTrue(trackingRecipes.contains(recipe4));

        // Situation 3: There is a unique recipe containing exactly the same keyword
        trackingRecipes.clear();
        account.searchRecipeByName("super mashed potatoes", trackingRecipes, recipes);
        assertEquals(trackingRecipes.size(), 1);
        assertTrue(trackingRecipes.contains(recipe2));
    }

    @Test
    void createMatchCountersForRecipesTest() {
        ArrayList<MatchCounter> counters = new ArrayList<MatchCounter>();
        ArrayList<Recipe> database = new ArrayList<>(Arrays.asList(recipe3, recipe4, recipe5));
        account.createMatchCountersForRecipes(counters, database);

        assertEquals(counters.size(), 3);
        assertEquals(counters.get(0).getAssocaitedRecipe(), recipe3);
        assertEquals(counters.get(0).getCounter(), 0);
        assertEquals(counters.get(1).getAssocaitedRecipe(), recipe4);
        assertEquals(counters.get(1).getCounter(), 0);
        assertEquals(counters.get(2).getAssocaitedRecipe(), recipe5);
        assertEquals(counters.get(2).getCounter(), 0);
    }

    @Test
    void matchIngredientCounterTest() {
        ArrayList<String> inputIngredients1 = new ArrayList<>(Arrays.asList(
           "milk", "cheese", "butter", "rice"
        ));

        ArrayList<String> inputIngredients2 = new ArrayList<>(Arrays.asList(
                "milk"
        ));

        ArrayList<String> inputIngredients3 = new ArrayList<>(Arrays.asList(
                "Milk", "chEese", "soy ", "rice"
        ));

        ArrayList<String> inputIngredients4 = new ArrayList<>(Arrays.asList(
                ""
        ));

        ArrayList<MatchCounter> counters = new ArrayList<MatchCounter>();
        ArrayList<Recipe> database = new ArrayList<>(Arrays.asList(recipe3, recipe4, recipe5));

        ArrayList<Recipe> sortedRecipes1 = account.matchIngredientCounter(inputIngredients1, counters, database);
        assertEquals(counters.get(0).getAssocaitedRecipe(), recipe3);
        assertEquals(counters.get(0).getCounter(), 2);
        assertEquals(counters.get(1).getAssocaitedRecipe(), recipe5);
        assertEquals(counters.get(1).getCounter(), 2);
        assertEquals(counters.get(2).getAssocaitedRecipe(), recipe4);
        assertEquals(counters.get(2).getCounter(), 1);
        assertEquals(sortedRecipes1, account.sortMatchedRecipes(counters));


        counters.clear();
        ArrayList<Recipe> sortedRecipes2 = account.matchIngredientCounter(inputIngredients2, counters, database);
        assertEquals(counters.get(0).getAssocaitedRecipe(), recipe5);
        assertEquals(counters.get(0).getCounter(), 1);
        assertEquals(counters.get(1).getAssocaitedRecipe(), recipe3);
        assertEquals(counters.get(1).getCounter(), 0);
        assertEquals(counters.get(2).getAssocaitedRecipe(), recipe4);
        assertEquals(counters.get(2).getCounter(), 0);
        assertEquals(sortedRecipes2, account.sortMatchedRecipes(counters));

        counters.clear();
        ArrayList<Recipe> sortedRecipes3 = account.matchIngredientCounter(inputIngredients3, counters, database);
        assertEquals(counters.get(0).getAssocaitedRecipe(), recipe4);
        assertEquals(counters.get(0).getCounter(), 2);
        assertEquals(counters.get(1).getAssocaitedRecipe(), recipe3);
        assertEquals(counters.get(1).getCounter(), 1);
        assertEquals(counters.get(2).getAssocaitedRecipe(), recipe5);
        assertEquals(counters.get(2).getCounter(), 1);
        assertEquals(sortedRecipes3, account.sortMatchedRecipes(counters));

        counters.clear();
        ArrayList<Recipe> sortedRecipes4 = account.matchIngredientCounter(inputIngredients4, counters, database);
        assertEquals(counters.get(0).getAssocaitedRecipe(), recipe3);
        assertEquals(counters.get(0).getCounter(), 1);
        assertEquals(counters.get(1).getAssocaitedRecipe(), recipe4);
        assertEquals(counters.get(1).getCounter(), 1);
        assertEquals(counters.get(2).getAssocaitedRecipe(), recipe5);
        assertEquals(counters.get(2).getCounter(), 1);
        assertEquals(sortedRecipes4, account.sortMatchedRecipes(counters));
    }

    @Test
    void sortMatchedRecipesTest() {
        ArrayList<MatchCounter> counters1 = new ArrayList<MatchCounter>(Arrays.asList(
                new MatchCounter(recipe3, 3),
                new MatchCounter(recipe4, 2),
                new MatchCounter(recipe5, 1)
                ));

        ArrayList<MatchCounter> counters2 = new ArrayList<MatchCounter>(Arrays.asList(
                new MatchCounter(recipe3, 1),
                new MatchCounter(recipe4, 2),
                new MatchCounter(recipe5, 5)
        ));

        ArrayList<MatchCounter> counters3 = new ArrayList<MatchCounter>(Arrays.asList(
                new MatchCounter(recipe3, 3),
                new MatchCounter(recipe4, 9),
                new MatchCounter(recipe5, 1)
        ));

        ArrayList<MatchCounter> counters4 = new ArrayList<MatchCounter>(Arrays.asList(
                new MatchCounter(recipe3, 9),
                new MatchCounter(recipe4, 9),
                new MatchCounter(recipe5, 1)
        ));

        ArrayList<MatchCounter> counters5 = new ArrayList<MatchCounter>(Arrays.asList(
                new MatchCounter(recipe3, 9),
                new MatchCounter(recipe4, 9),
                new MatchCounter(recipe5, 9)
        ));

        ArrayList<Recipe> recipes1 = account.sortMatchedRecipes(counters1);
        ArrayList<Recipe> recipes2 = account.sortMatchedRecipes(counters2);
        ArrayList<Recipe> recipes3 = account.sortMatchedRecipes(counters3);
        ArrayList<Recipe> recipes4 = account.sortMatchedRecipes(counters4);
        ArrayList<Recipe> recipes5 = account.sortMatchedRecipes(counters5);

        assertEquals(recipes1.get(0), recipe3);
        assertEquals(recipes1.get(1), recipe4);
        assertEquals(recipes1.get(2), recipe5);

        assertEquals(recipes2.get(0), recipe5);
        assertEquals(recipes2.get(1), recipe4);
        assertEquals(recipes2.get(2), recipe3);

        assertEquals(recipes3.get(0), recipe4);
        assertEquals(recipes3.get(1), recipe3);
        assertEquals(recipes3.get(2), recipe5);

        assertEquals(recipes4.get(0), recipe3);
        assertEquals(recipes4.get(1), recipe4);
        assertEquals(recipes4.get(2), recipe5);

        assertEquals(recipes5.get(0), recipe3);
        assertEquals(recipes5.get(1), recipe4);
        assertEquals(recipes5.get(2), recipe5);
    }

    @Test
    void testCreateRecipeThroughFileCreated() {
        ArrayList<String> steps = new ArrayList<>(Arrays.asList("step1", "step2", "step3"));
        ArrayList<String> ingredients =  new ArrayList<>(Arrays.asList("food1", "food2", "food3"));

        String readModeCreated = "created";
        account.createRecipeThroughFile("name", ingredients, steps, readModeCreated);
        assertEquals(account.getCreatedRecipes().get(0).getIngredients(), ingredients);
        assertEquals(account.getCreatedRecipes().get(0).getSteps(), steps);
        assertEquals(account.getCreatedRecipes().get(0).getName(), "name");
        assertEquals(account.getCreatedRecipes().size(), 1);

    }

    @Test
    void testCreateRecipeThroughFileSaved() {
        ArrayList<String> steps = new ArrayList<>(Arrays.asList("step1", "step2", "step3"));
        ArrayList<String> ingredients =  new ArrayList<>(Arrays.asList("food1", "food2", "food3"));

        String readModeSaved = "saved";
        account.createRecipeThroughFile("name", ingredients, steps, readModeSaved);
        assertEquals(account.getSavedRecipes().get(0).getIngredients(), ingredients);
        assertEquals(account.getSavedRecipes().get(0).getSteps(), steps);
        assertEquals(account.getSavedRecipes().get(0).getName(), "name");
        assertEquals(account.getSavedRecipes().size(), 1);
    }

    @Test
    void testCreateRecipeThroughFileDataBase() {
        account.erase();
        ArrayList<String> steps = new ArrayList<>(Arrays.asList("step1", "step2", "step3"));
        ArrayList<String> ingredients =  new ArrayList<>(Arrays.asList("food1", "food2", "food3"));

        String readModeDataBase = "database";
        account.createRecipeThroughFile("name", ingredients, steps, readModeDataBase);
        assertEquals(DataBase.returnRecipes().get(0).getIngredients(), ingredients);
        assertEquals(DataBase.returnRecipes().get(0).getSteps(), steps);
        assertEquals(DataBase.returnRecipes().get(0).getName(), "name");
        assertEquals(DataBase.returnRecipes().size(), 1);

    }

    @Test
    void testErase() {
        account.createRecipe("one", "butter, chicken",
                new ArrayList<>(Arrays.asList("step1", "step2")));
        account.addSavedRecipe(recipe2);
        account.addSavedRecipe(recipe1);
        DataBase.addRecipe(recipe5);
        assertFalse(account.getSavedRecipes().isEmpty());
        assertFalse(account.getCreatedRecipes().isEmpty());
        assertFalse(DataBase.returnRecipes().isEmpty());

        account.erase();
        assertTrue(account.getCreatedRecipes().isEmpty());
        assertTrue(account.getSavedRecipes().isEmpty());
        assertTrue(DataBase.returnRecipes().isEmpty());

    }

    @Test
    void testChangeWritingMode() {
        account.createRecipe("one", "butter, chicken",
                new ArrayList<>(Arrays.asList("step1", "step2")));
        account.addSavedRecipe(recipe2);
        account.addSavedRecipe(recipe1);
        DataBase.addRecipe(recipe5);

        account.changeWritingMode("created");
        assertEquals(account.getCurrentWritingMode(), account.getCreatedRecipes());

        account.changeWritingMode("saved");
        assertEquals(account.getCurrentWritingMode(), account.getSavedRecipes());

        account.changeWritingMode("database");
        assertEquals(account.getCurrentWritingMode(), DataBase.returnRecipes());
    }

    @Test
    void testAddComponent() {
        ArrayList<String> steps = new ArrayList<>(Arrays.asList("step1", "step2", "step3"));
        ArrayList<String> ingredients =  new ArrayList<>(Arrays.asList("food1", "food2", "food3"));

        String readModeCreated = "created";
        account.addComponents("name", ingredients, steps, readModeCreated);
        assertEquals(account.getCreatedRecipes().get(0).getIngredients(), ingredients);
        assertEquals(account.getCreatedRecipes().get(0).getSteps(), steps);
        assertEquals(account.getCreatedRecipes().get(0).getName(), "name");
        assertEquals(account.getCreatedRecipes().size(), 1);
    }

    @Test
    void testGetCurrentWritingMode() {

    }

}