package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.*;

/*
* Account represents a user account,
* it keeps track of the list of recipes this user has created,
* a list of recipes that this user has saved,
* a username, and a unique user ID.
*
* the user can create, add, save, and access information through this account
*/


public class Account implements Writable {
    // delete or rename this class!

    private String userName;
    private static int userID = 1;
    private ArrayList<Recipe> savedRecipes;
    private ArrayList<Recipe> createdRecipes;
    private ArrayList<Recipe> currentWritingMode;

    //EFFECTS: creates an instance of the account with the specified username, a system assigned ID,
    //         an empty array of created recipes and saved recipes.
    public Account(String userName) {
        this.userName = userName;               // username of the account
        this.userID++;                          // userID
        createdRecipes = new ArrayList<>();     // keeps track of the created recipes from the user in the account
        savedRecipes = new ArrayList<>();       // keeps track of the saved recipes from the user in this account
        currentWritingMode = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: creates a recipe with the user's specified input of ingredients, name, and steps,
    //          this created recipe will be automatically saved to the list of created recipes in this
    //          account and in the DataBase.
    public void createRecipe(String name, String ingredients, ArrayList<String> steps) {
        Recipe recipe = new Recipe(name);

        String currentIngredient = "";
        for (int i = 0; i < ingredients.length(); i++) {
            if (ingredients.charAt(i) == ',') {
                recipe.addIngredient(currentIngredient.trim());
                currentIngredient = "";
            } else {
                currentIngredient += ingredients.charAt(i);
            }
        }
        recipe.addIngredient(currentIngredient.trim());

        for (String step : steps) {
            recipe.addStep(step);
        }

        createdRecipes.add(recipe);
        DataBase.addRecipe(recipe);
        EventLog.getInstance().logEvent(new Event("A new recipe named '" + name + "' has been created!"));
    }

    // REQUIRES: readMode is one of "saved", "created", and "database"
    // MODIFIES: this
    // EFFECTS: creates a recipe, however in a simpler fashion.
    public void createRecipeThroughFile(String name, ArrayList<String> ingredeints, ArrayList<String> steps,
                                        String readMode) {
        Recipe recipe = new Recipe(name, ingredeints, steps);

        if (readMode.equals("created")) {
            createdRecipes.add(recipe);
        } else if (readMode.equals("saved")) {
            savedRecipes.add(recipe);
        } else {
            DataBase.addRecipe(recipe);
        }
    }

    // MODIFIES: this
    // EFFECTS: deletes the specified recipe from the list of created recipes in this account
    public void deleteRecipe(Recipe recipe) {
        createdRecipes.remove(recipe);
        DataBase.removeRecipe(recipe);
        EventLog.getInstance().logEvent(new Event("The recipe '" + recipe.getName()
                + "' has been deleted from your "
                + "created recipes and database"));
    }

//    // EFFECTS: returns the user id of the account
//    public int getUserID() {
//        return userID;
//    }

    // EFFECTS: returns the number of saveRecipes in the account
    public int getNumSavedRecipes() {
        return savedRecipes.size();
    }

    // EFFECTS: returns the username of the account
    public String getUserName() {
        return userName;
    }

    // MODIFIES: this
    // EFFECTS: adds a recipe into the list of saved recipes
    public void addSavedRecipe(Recipe recipe) {

        savedRecipes.add(recipe);
        recipe.addSaves();
        EventLog.getInstance().logEvent(new Event("The recipe '" + recipe.getName()
                + "' has been added to your saved list of recipes!"));
    }

    // REQUIRES: the recipe specified must be in the list of saved recipes
    // MODIFIES: this
    // EFFECTS: removes specified recipe from the list of saved recipes
    public void removeSavedRecipe(Recipe recipe) {
        savedRecipes.remove(recipe);
        recipe.removeSave();
        EventLog.getInstance().logEvent(new Event("The recipe '" + recipe.getName()
                + "' has been removed from your saved list of recipes!"));
    }

    // EFFECTS: returns the list of saved recipes
    public ArrayList<Recipe> getSavedRecipes() {
        return savedRecipes;
    }

    // EFFECTS: returns the list of created recipes
    public ArrayList<Recipe> getCreatedRecipes() {
        return createdRecipes;
    }


    // REQUIRES: nameRecipes.size() == 0
    // MODIFIES: nameRecipes
    // EFFECTS: searches through 'data' for the name input from the user by comparing the user input to
    //          the name of the recipes in the 'data', saves all the occurrences of the user input to
    //          namesRecipes
    public void searchRecipeByName(String name, ArrayList<Recipe> nameRecipes, ArrayList<Recipe> data) {
        ArrayList<Recipe> allRecipes = data;
        for (Recipe recipe : allRecipes) {
            if (recipe.getName().toLowerCase().contains(name.toLowerCase().trim())
                    || name.toLowerCase().trim().contains(recipe.getName().toLowerCase())) {
                nameRecipes.add(recipe);
            }
        }
        EventLog.getInstance().logEvent(new Event("You have searched a recipe by the name of " + name));
    }

    // REQUIRES: counters.size() == 0
    // MODIFIES: counters
    // EFFECTS: creates an array of MatchCounter in counters for all the recipes in the specified database
    public ArrayList<MatchCounter> createMatchCountersForRecipes(ArrayList<MatchCounter> counters,
                                                                  ArrayList<Recipe> database) {
        ArrayList<Recipe> recipes = database;
        for (Recipe recipe : recipes) {
            MatchCounter counter = new MatchCounter(recipe);
            counters.add(counter);
        }
        return counters;
    }

    // REQUIRES: searchCounter.size() == 0
    // MODIFIES: searchCounters
    // EFFECTS: searches through database and compare each recipe's ingredient with the
    //          ingredients from the user input, if the ingredients match or have similar
    //          words in the names, then the count in the MatchCounter for the recipe in the
    //          database will be increased by 1. It calls the sortMatchedRecipes function.
    public ArrayList<Recipe> matchIngredientCounter(ArrayList<String> inputIngredients,
                                                    ArrayList<MatchCounter> searchCounters,
                                                    ArrayList<Recipe> database) {
        searchCounters = createMatchCountersForRecipes(searchCounters, database);

        for (String ingredient : inputIngredients) {
            for (MatchCounter counter : searchCounters) {
                // The String.join(delimiter, arrayList) method converts an array list into a string separated by
                // the delimiter. (ex. String.join(',', {"a", "b", "c"}) => "a,b,c")
                String stringIngredients = String.join(",", counter.getAssocaitedRecipe().getIngredients());
                if (stringIngredients.toLowerCase().contains(ingredient.toLowerCase())) {
                    counter.matchIncrease();
                }
            }
        }
        return sortMatchedRecipes(searchCounters);
    }

    // MODIFIES: counters
    // EFFECTS: takes on a list of MathCounters and sort them based on the counter they have
    //          from most to least occurrences. Then convert the sorted list of MatchCounters to
    //          a list of Recipes.
    public ArrayList<Recipe> sortMatchedRecipes(ArrayList<MatchCounter> counters) {
        ArrayList<Recipe> filterRecipes = new ArrayList<Recipe>();

        Collections.sort(counters, new CounterComparator());
        for (MatchCounter counter : counters) {
            filterRecipes.add(counter.getAssocaitedRecipe());
        }
        EventLog.getInstance().logEvent(new Event("You have just searched recipes by ingredients!"));

        return filterRecipes;
    }

    // MODIFIES: this
    // EFFECTS: change the writing mode to the appropriate mode,
    //          one of "crated", "saved", and "database"
    public void changeWritingMode(String mode) {
        if (mode.equals("created")) {
            currentWritingMode = createdRecipes;
        } else if (mode.equals("saved")) {
            currentWritingMode = savedRecipes;
        } else {
            currentWritingMode = DataBase.returnRecipes();
        }
    }

    // MODIFIES: this
    // EFFECTS: calls the createRecipeThroughFile function with the given arguments,
    //          converts recipe into JSON and store into a file.
    public void addComponents(String recipeName, ArrayList<String> ingredients,
                              ArrayList<String> steps, String readMode) {
        createRecipeThroughFile(recipeName, ingredients, steps, readMode);
    }

    // EFFECTS: overrides the toJson method, converts all the recipes in the account into
    //          JSON objects.
    @Override
    public JSONObject toJson() {

        JSONObject json = new JSONObject();
        json.put("name", userName);
        json.put("recipes", recipesToJson());

        return json;
    }

    //EFFECTS: returns the recipes in this account
    public JSONArray recipesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Recipe recipe : currentWritingMode) {
            jsonArray.put(recipe.toJson());
        }

        return jsonArray;
    }

//    // EFFECTS: adds a recipe to the curentWritingMode, for test purposes;
//    public void addRecipeToCurrent(Recipe recipe) {
//        currentWritingMode.add(recipe);
//    }

    // EFFECTS: returns the list of recipes that the file is trying to read and write
    public ArrayList<Recipe> getCurrentWritingMode() {
        return currentWritingMode;
    }

    // MODIFIES: this
    // EFFECTS: erases all the for the current account instance, used for RecipeSearchEngine.
    public void erase() {
        savedRecipes.clear();
        createdRecipes.clear();
        currentWritingMode.clear();
        DataBase.erase();
    }
}
