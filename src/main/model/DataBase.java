package model;

import java.util.*;

/*
This is the main database, it stores all the recipes that different users created.
 */

public class DataBase {

    private static ArrayList<Recipe> recipes = new ArrayList<>();

//    private static ArrayList<Recipe> recipes = new ArrayList<>(Arrays.asList(
//            // Mashed Potatoes recipe
//            new Recipe("Mashed Potatoes",
//                    new ArrayList<>(Arrays.asList("baking potatoes",
//                            "garlic", "milk", "butter", "ground black pepper", "salt")),
//                    new ArrayList<>(Arrays.asList("Bring a large pot of salted water to a boil. "
//                            + "Add peeled potatoes and peeled garlic, lower heat to medium, "
//                            + "and simmer until potatoes are tender, 15 to 20 minutes.",
//                            "When the potatoes are almost finished, "
//                            + "heat milk and 3 table spoon of butter in a small saucepan "
//                            + "over low heat until butter is "
//                            + "melted.",
//                            "Drain potatoes and return to the pot. Slowly add warm milk mixture, "
//                            + "blending it in with a potato masher or electric mixer until potatoes "
//                            + "are smooth and creamy. Season with salt and pepper."))),
//
//            new Recipe("Mac and Cheese",
//                    new ArrayList<>(Arrays.asList("macaroni", "butter", "flour", "cheese", "seasonings",
//                            "bread Crumbs")),
//                    new ArrayList<>(Arrays.asList("Boil the noodles, drain, and transfer to a prepared baking dish.",
//                            "Make the cheese sauce, pour the sauce over the noodles, and stir.",
//                            "Make the topping, spread it over macaroni and cheese, and sprinkle with paprika.",
//                            "Bake the mac and cheese until the topping is golden brown."))),
//
//            new Recipe("Fried Rice",
//                    new ArrayList<>(Arrays.asList("baby carrots", "frozen green peas", "vegetable oil",
//                            "garlic", "eggs", "white rice", "soy sauce", "sesame oil")),
//                    new ArrayList<>(Arrays.asList("Assemble ingredients.",
//                            "Place carrots in a small saucepan and cover with water. "
//                            + "Bring to a low boil and cook for 3 to 5 minutes. Stir in peas, "
//                            + "then immediately drain in a colander.",
//                            "Heat a wok over high heat. Pour in vegetable oil, then stir in carrots, peas, "
//                            + "and garlic; cook for about 30 seconds. Add eggs; stir quickly to "
//                            + "scramble eggs with vegetables.",
//                            "Stir in cooked rice. Add soy sauce and toss rice to coat. "
//                            + "Drizzle with sesame oil and toss again."
//                    )))
//    ));

    // EFFECTS: returns all the recipes in the Database
    public static ArrayList<Recipe> returnRecipes() {
        return recipes;
    }

    // REQUIRES: the recipe specified must not be unique from the other elements in the list
    // MODIFIES: this
    // EFFECTS: adds a new specified recipe to the Database
    public static void addRecipe(Recipe recipe) {
        recipes.add(recipe);
    }

    // REQUIRES: the specified recipe must exist in the database
    // MODIFIES: this
    // EFFECTS: removes the specified recipe from the Database
    public static void removeRecipe(Recipe recipe) {

        recipes.remove(recipe);
    }

    // EFFECTS: returns the size of recipes in the Database
    public static int getSize() {
        return recipes.size();
    }

    // EFFECTS: erases all the information stored in the current dataset list
    public static void erase() {
        recipes.clear();
    }

}
