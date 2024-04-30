package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class RecipeTest {
    Recipe recipe;
    Recipe recipeFormal;
    Recipe recipeFormal2;
    Recipe recipe2;
    Recipe recipe3;
    Recipe recipe4;


    @BeforeEach
    void setup() {
        recipe = new Recipe("Mashed Potatoes");
        recipeFormal = new Recipe("Mac and Cheese",
                new ArrayList<>(Arrays.asList("macaroni", "butter", "flour", "cheese", "seasonings",
                        "bread Crumbs")),
                new ArrayList<>(Arrays.asList("Boil the noodles, drain, and transfer to a prepared baking dish.",
                        "Make the cheese sauce, pour the sauce over the noodles, and stir.",
                        "Make the topping, spread it over macaroni and cheese, and sprinkle with paprika.",
                        "Bake the mac and cheese until the topping is golden brown.")));
        recipeFormal2 = new Recipe("Mashed Potatoes",
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
        recipe2 = new Recipe("Mac and Cheese",
                new ArrayList<>(Arrays.asList("macaroni", "butter", "flour", "cheese", "seasonings",
                        "bread Crumbs")),
                new ArrayList<>());
        recipe3 = new Recipe("Mac and Cheese",
                new ArrayList<>(),
                new ArrayList<>());
        recipe4 = new Recipe("",
                new ArrayList<>(Arrays.asList("macaroni", "butter", "flour", "cheese", "seasonings",
                        "bread Crumbs")),
                new ArrayList<>(Arrays.asList("Boil the noodles, drain, and transfer to a prepared baking dish.",
                        "Make the cheese sauce, pour the sauce over the noodles, and stir.",
                        "Make the topping, spread it over macaroni and cheese, and sprinkle with paprika.",
                        "Bake the mac and cheese until the topping is golden brown.")));
    }

    @Test
    void constructorTest() {
        assertEquals(recipe.getName(), "Mashed Potatoes");
        assertEquals(recipe.getNumSaves(), 0);
        assertTrue(recipe.getIngredients().isEmpty());
        assertTrue(recipe.getSteps().isEmpty());
    }

    @Test
    void addStepTest() {
        assertEquals(recipe.getSteps().size(), 0);

        recipe.addStep("Boil the potatoes");
        assertEquals(recipe.getSteps().size(), 1);
        assertEquals(recipe.getSteps().get(0), "Boil the potatoes");

        recipe.addStep("Mash the potatoes");
        assertEquals(recipe.getSteps().size(), 2);
        assertEquals(recipe.getSteps().get(1), "Mash the potatoes");
    }

    @Test
    void addIngredient() {
        assertEquals(recipe.getIngredients().size(), 0);

        recipe.addIngredient("potatoes");
        assertEquals(recipe.getIngredients().size(), 1);
        assertEquals(recipe.getIngredients().get(0), "potatoes");

        recipe.addIngredient("butter");
        assertEquals(recipe.getIngredients().size(), 2);
        assertEquals(recipe.getIngredients().get(1), "butter");
    }

    @Test
    void stepConsoleOrganizerTest() {
        String text1 = "step1";
        assertEquals(text1, recipe.stepConsoleOrganizer(text1));

        String text2 = "Gradually incorporate the sifted dry ingredients into the wet mixture, "
                + "ensuring a smooth and homogeneous batter forms. "
                + "Use a gentle folding motion to combine the ingredients, "
                + "being careful not to overmix, as this can result in a dense texture. ";
//        assertEquals(recipe.stepConsoleOrganizer(text2),
//                text2.substring(0,80) + "\n   " +
//                text2.substring(80, 160) + "\n   " +
//                text2.substring(160, 240) + "\n   " +
//                text2.substring(240));
//
//        String text3 = text2.substring(0,81);
//        assertEquals(recipe.stepConsoleOrganizer(text3),
//                text3.substring(0,80) + "\n   "
//                        + text3.substring(80));
        assertEquals(recipe.stepConsoleOrganizer(text2),
                "Gradually incorporate the sifted dry ingredients into the we\n" +
                        "   t mixture, ensuring a smooth and homogeneous batter forms. U\n" +
                        "   se a gentle folding motion to combine the ingredients, being\n" +
                        "    careful not to overmix, as this can result in a dense texture.");

        String text3 = text2.substring(0,61);
        assertEquals(recipe.stepConsoleOrganizer(text3),
                text3.substring(0,60) + "\n   "
                        + text3.substring(60));

    }

    @Test
    void addSavesTest() {
        assertEquals(recipe.getNumSaves(), 0);

        recipe.addSaves();
        assertEquals(recipe.getNumSaves(), 1);
        recipe.addSaves();
        assertEquals(recipe.getNumSaves(), 2);
    }

    @Test
    void removeSaveTest() {
        recipe.addSaves();
        assertEquals(recipe.getNumSaves(), 1);
        recipe.removeSave();
        assertEquals(recipe.getNumSaves(), 0);

        recipe.addSaves();
        recipe.addSaves();
        recipe.removeSave();
        assertEquals(recipe.getNumSaves(), 1);
    }

    @Test
    void getRecipeTest() {
        assertEquals(recipeFormal.getRecipe(),
                "------------------------------------------------" +
                        "---------------------------------------" + "\n\n" + "Recipe Name: Mac and Cheese" +
                        "\n\n" + "Ingredients: ----------- " + "\n\n" +
                        "1. macaroni\n" +
                        "2. butter\n" +
                        "3. flour\n" +
                        "4. cheese\n" +
                        "5. seasonings\n" +
                        "6. bread Crumbs\n" + "\n" + "Procedures: ------------" + "\n\n" +
                        "1. Boil the noodles, drain, and transfer to a prepared baking dish.\n" +
                        "2. Make the cheese sauce, pour the sauce over the noodles, and stir.\n" +
                        "3. Make the topping, spread it over macaroni and cheese, and sprinkle with paprika.\n" +
                        "   \n" +
                        "4. Bake the mac and cheese until the topping is golden brown." +
                        "\n\n" +
                        "-------------------------------------------------" +
                        "--------------------------------------" + "\n\n");
        assertEquals(recipeFormal.getNumSaves(), 0);
        recipeFormal.addSaves();
        assertEquals(recipeFormal.getNumSaves(), 1);
        recipeFormal.removeSave();
        assertEquals(recipeFormal.getNumSaves(), 0);
        assertEquals(recipeFormal.getName(), "Mac and Cheese");

        assertEquals(recipe2.getRecipe(),
                "---------------------------------------------------------------------------------------\n" +
                        "\n" +
                        "Recipe Name: Mac and Cheese\n" +
                        "\n" +
                        "Ingredients: ----------- \n" +
                        "\n" +
                        "1. macaroni\n" +
                        "2. butter\n" +
                        "3. flour\n" +
                        "4. cheese\n" +
                        "5. seasonings\n" +
                        "6. bread Crumbs\n" +
                        "\n" +
                        "Procedures: ------------\n" +
                        "\n" +
                        "\n" +
                        "---------------------------------------------------------------------------------------" +
                        "\n\n");

        assertEquals(recipe3.getRecipe(),
                "---------------------------------------------------------------------------------------\n" +
                        "\n" +
                        "Recipe Name: Mac and Cheese\n" +
                        "\n" +
                        "Ingredients: ----------- \n" +
                        "\n" +
                        "\n" +
                        "Procedures: ------------\n" +
                        "\n" +
                        "\n" +
                        "---------------------------------------------------------------------------------------\n" +
                        "\n");

        assertEquals(recipe4.getRecipe(),
                "---------------------------------------------------------------------------------------\n" +
                        "\n" +
                        "Recipe Name: \n" +
                        "\n" +
                        "Ingredients: ----------- \n" +
                        "\n" +
                        "1. macaroni\n" +
                        "2. butter\n" +
                        "3. flour\n" +
                        "4. cheese\n" +
                        "5. seasonings\n" +
                        "6. bread Crumbs\n" +
                        "\n" +
                        "Procedures: ------------\n" +
                        "\n" +
                        "1. Boil the noodles, drain, and transfer to a prepared baking dish.\n" +
                        "2. Make the cheese sauce, pour the sauce over the noodles, and stir.\n" +
                        "3. Make the topping, spread it over macaroni and cheese, and sprinkle with paprika.\n" +
                        "   \n" +
                        "4. Bake the mac and cheese until the topping is golden brown." +
                        "\n" +
                        "---------------------------------------------------------------------------------------\n" +
                        "\n");
    }

}
