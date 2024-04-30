package ui;

import model.*;
import persistence.*;

import javax.swing.*;
import java.io.IOException;
import java.sql.Array;
import java.util.*;

/*
 * This  is the GUI engine for recipe search engine,
 * a user can asks requests about the engine for recommendations of
 * recipes based on input ingredients or name, can save a recipe, create recipe, and view recipe
 * through the user account.
 */

public class GuiEngine {
    private Account account;                         // account of the user
    private int recipeIndex;                         // the current index that the user is accessing in a
    // list of recipes
    private ArrayList<MatchCounter> searchCounters;  // keeps track of the recommended recipes in terms of MatchCounters
    // searched by ingredients
    private ArrayList<Recipe> currentSavedRecipes;   // keeps track of the list of saved recipes
    private ArrayList<Recipe> currentCreatedRecipes; // keeps track of the created list of recipes
    private ArrayList<Recipe> nameRecipes;           // keeps track of the list of recipes searched by names
    private boolean saveMode;                        // user viewing the saved list
    private boolean createMode;                      // user viewing the created list
    private boolean recommendMode;                   // user viewing the recommended list by searching ingredients
    private boolean searchMode;                      // user viewing the list by searching names
    private boolean viewMode;                        // user viewing the database recipe list


    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_CREATED_RECIPE = "./data/createdRecipes.json";
    private static final String JSON_SAVED_RECIPE = "./data/savedRecipes.json";
    private static final String JSON_DATABASE_RECIPE = "./data/database.json";

    private boolean progressSaved;

    // EFFECTS: creates an instance of RecipeSearchEngine with specified username,
    //          fields searchCounters, currentSavedRecipes, currentCreatedRecipes, nameRecipes
    //          will be instantiated to empty ArrayLists, all the fields ending with 'Mode' will
    //          be instantiated as false.
    public GuiEngine(String username) {
        account = new Account(username);
        searchCounters = new ArrayList<>();
        recipeIndex = 0;
        currentSavedRecipes = new ArrayList<>();
        currentCreatedRecipes = new ArrayList<>();
        nameRecipes = new ArrayList<>();
        saveMode = false;
        createMode = false;
        recommendMode = false;
        viewMode = false;
        searchMode = false;
        progressSaved = true;

        jsonWriter = new JsonWriter(JSON_CREATED_RECIPE, JSON_SAVED_RECIPE, JSON_DATABASE_RECIPE);
        jsonReader = new JsonReader(JSON_CREATED_RECIPE, JSON_SAVED_RECIPE, JSON_DATABASE_RECIPE);
    }

    // EFFECTS: fulfills the user request by calling the searchRecipeByName function
    public void searchRecipeByNameHandler(String name, JTextPane pane) {
        reset();
        searchMode = true;

        account.searchRecipeByName(name, nameRecipes, DataBase.returnRecipes());

        if (nameRecipes.isEmpty()) {
            pane.setText("Sorry, there are no matching recipes in the database!");
        } else {
            pane.setText(nameRecipes.get(0).getRecipe());
        }
    }

    // MODIFIES: this
    // EFFECTS: returns all the recipes in the database sorted by the number of ingredients
    //          matched, likes and saves. Converts the input string to arraylist
    public void searchRecipe(String ingredients, JTextPane pane) {

        reset();
        recommendMode = true;

        ArrayList<String> inputIngredients = new ArrayList<>();

        String currentIngredient = "";
        for (int i = 0; i < ingredients.length(); i++) {
            if (ingredients.charAt(i) == ',') {
                inputIngredients.add(currentIngredient.toLowerCase().trim());
                currentIngredient = "";
            } else {
                currentIngredient += ingredients.charAt(i);
            }
        }

        inputIngredients.add(currentIngredient);

        ArrayList<Recipe> filteredRecipes =  matchIngredientCounter(inputIngredients);
        pane.setText(filteredRecipes.get(0).getRecipe());
    }

    // EFFECTS: calls the matchIngredientCounter function from the account with the user inputIngredients
    private ArrayList<Recipe> matchIngredientCounter(ArrayList<String> inputIngredients) {
        return account.matchIngredientCounter(inputIngredients, searchCounters, DataBase.returnRecipes());
    }

    // EFFECTS: returns the next recommended recipe depending on the current mode the user is viewing,
    //          this function will be activated when the user types 'next recipe'
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public void nextRecipe(JTextPane pane) {
        if (saveMode) {
            if (currentSavedRecipes.size() == recipeIndex + 1) {
                pane.setText("You are at the end of your list!");
            } else {
                recipeIndex++;
                pane.setText(currentSavedRecipes.get(recipeIndex).getRecipe());
            }
        } else if (createMode) {
            if (currentCreatedRecipes.size() == recipeIndex + 1) {
                pane.setText("You are at the end of your list!");
            } else {
                recipeIndex++;
                pane.setText(currentCreatedRecipes.get(recipeIndex).getRecipe());
            }
        } else if (recommendMode) {
            if (searchCounters.size() == recipeIndex + 1) {
                pane.setText("You are at the end of your list!");
            } else {
                recipeIndex++;
                pane.setText(searchCounters.get(recipeIndex).getAssocaitedRecipe().getRecipe());
            }
        } else if (viewMode) {
            if (DataBase.returnRecipes().size() == recipeIndex + 1) {
                pane.setText("You are at the end of Database list!");
            } else {
                recipeIndex++;
                pane.setText(DataBase.returnRecipes().get(recipeIndex).getRecipe());
            }
        } else if (searchMode) {
            if (nameRecipes.size() == recipeIndex + 1) {
                pane.setText("You are at the end of search list!");
            } else {
                recipeIndex++;
                pane.setText(nameRecipes.get(recipeIndex).getRecipe());
            }
        } else {
            pane.setText("You are currently not viewing any recipes! Please select"
                    + "one of buttons to view lists of recipes first!");
        }
    }

    // EFFECTS: returns the previous recommended recipe depending on the user's current mode,
    //          this will be activated if the user types 'previous recipe'
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public void previousRecipe(JTextPane pane) {
        if (recommendMode) {
            if (recipeIndex > 0) {
                recipeIndex--;
                pane.setText(searchCounters.get(recipeIndex).getAssocaitedRecipe().getRecipe());
            } else {
                pane.setText("You are viewing the first recipe in the recommended list.");
            }
        } else if (createMode) {
            if (recipeIndex > 0) {
                recipeIndex--;
                pane.setText(currentCreatedRecipes.get(recipeIndex).getRecipe());
            } else {
                pane.setText("You are viewing the first recipe in the created list.");
            }
        } else if (saveMode) {
            if (recipeIndex > 0) {
                recipeIndex--;
                pane.setText(currentSavedRecipes.get(recipeIndex).getRecipe());
            } else {
                pane.setText("You are viewing the first recipe in the saved list.");
            }
        } else if (viewMode) {
            if (recipeIndex > 0) {
                recipeIndex--;
                pane.setText(DataBase.returnRecipes().get(recipeIndex).getRecipe());
            } else {
                pane.setText("You are viewing the first recipe in the Database.");
            }
        } else if (searchMode) {
            if (recipeIndex > 0) {
                recipeIndex--;
                pane.setText(nameRecipes.get(recipeIndex).getRecipe());
            } else {
                pane.setText("You are viewing the first recipe in your search list");
            }
        } else {
            pane.setText("You are currently not viewing any recipes! Please select"
                    + "one of buttons to view lists of recipes first!");
        }
    }

    public String getRecipeName() {
        if (recommendMode) {
            return searchCounters.get(recipeIndex).getAssocaitedRecipe().getName();
        } else if (createMode) {
            return currentCreatedRecipes.get(recipeIndex).getName();
        } else if (saveMode) {
            return currentSavedRecipes.get(recipeIndex).getName();
        } else if (viewMode) {
            return DataBase.returnRecipes().get(recipeIndex).getName();
        } else if (searchMode) {
            return nameRecipes.get(recipeIndex).getName();
        } else {
            return "None";
        }
    }

    // EFFECTS: the system will create a recipe for the account
    public void createRecipe(String name, String ingredients, ArrayList<String> steps) {
        progressSaved = false;
        account.createRecipe(name, ingredients, steps);
    }

    // EFFECTS: deletes the recipe from the current created or saved list of recipes
    public void deleteRecipe(JTextPane pane) {
        progressSaved = false;
        if (createMode) {
            if (currentCreatedRecipes.isEmpty()) {
                pane.setText("You have not created any recipes yet!");
            } else {
                account.deleteRecipe(currentCreatedRecipes.get(recipeIndex));
                pane.setText("The current created recipe has been deleted!");
            }
        } else if (saveMode) {
            if (currentSavedRecipes.isEmpty()) {
                pane.setText("You have no saved recipes!");

            } else {
                account.removeSavedRecipe(currentSavedRecipes.get(recipeIndex));
                pane.setText("The current saved recipe has been deleted from the save list!");
            }
        } else if (viewMode) {
            pane.setText("You cannot delete recipes from the Database!");
        } else if (recommendMode || searchMode) {
            pane.setText("you cannot delete recipes that are recommended to you!");
        }
    }

    // EFFECTS: save the current recipe that the user is viewing to the currentSavedRecipes
    public void saveCurrentRecipe(JTextPane pane) {
        progressSaved = false;
        if (viewMode) {
            account.addSavedRecipe(DataBase.returnRecipes().get(recipeIndex));
            pane.setText("The current recipe has been saved to the saved list!");
        } else if (searchCounters.isEmpty() && nameRecipes.isEmpty()) {
            pane.setText("No recipes have been selected!");
        } else if (recommendMode) {
            account.addSavedRecipe(searchCounters.get(recipeIndex).getAssocaitedRecipe());
            pane.setText("The current recipe has been saved to the save list!");
        } else {
            account.addSavedRecipe(nameRecipes.get(recipeIndex));
            pane.setText("The current recipe has been saved to the save list!");
        }
    }

    // MODIFIES: this
    // EFFECTS: access all the recipes created by the user one by one
    public void seeAllCreatedRecipes(JTextPane pane) {
        reset();
        createMode = true;
        currentCreatedRecipes = account.getCreatedRecipes();

        if (currentCreatedRecipes.isEmpty()) {
            pane.setText("You have yet created any recipes!");
        } else {

            pane.setText(currentCreatedRecipes.get(recipeIndex).getRecipe());
        }
    }

    // MODIFIES: this
    // EFFECTS: access all the recipes in the user's saved list one by one
    public void seeAllSavedRecipes(JTextPane pane) {
        reset();
        saveMode = true;
        currentSavedRecipes = account.getSavedRecipes();

        if (currentSavedRecipes.isEmpty()) {
            pane.setText("You have no saved recipes!");
        } else {
            pane.setText(currentSavedRecipes.get(recipeIndex).getRecipe());
        }
    }

    // MODIFIES: this
    // EFFECTS: resets all the fields containing the keyword 'Mode'
    // and clears fields of nameRecipes and searchCounters.
    private void reset() {
        saveMode = false;
        createMode = false;
        recommendMode = false;
        viewMode = false;
        searchMode = false;
        searchCounters.clear();
        nameRecipes.clear();
        recipeIndex = 0;
    }

    // EFFECTS: access all the recipes in the database one by one.
    public void seeAllRecipes(JTextPane pane) {
        reset();
        viewMode = true;
        pane.setText(DataBase.returnRecipes().get(recipeIndex).getRecipe());
    }

    // EFFECTS: save all the progress in the account
    public void saveProgress() {
        progressSaved = true;
        try {
            jsonWriter.open("created");
            jsonWriter.write(account, "created");
            jsonWriter.close();

            jsonWriter.open("saved");
            jsonWriter.write(account, "saved");
            jsonWriter.close();

            jsonWriter.open("database");
            jsonWriter.write(account, "database");
            jsonWriter.close();

//            System.out.println("All user progress have been saved!");

        } catch (IOException e) {
//            System.out.println("Unable to write to the files!");
        }
    }

    // MODIFIES: this
    // EFFECTS: loads account from all saved files.
    public void loadProgress() {
        account.erase();
        try {
            jsonReader.readRecipes(account,"created");
//            System.out.println("Loaded all created recipes from " + account.getUserName() + " from "
//                    + JSON_CREATED_RECIPE);
            jsonReader.readRecipes(account,"saved");
//            System.out.println("Loaded all created recipes from " + account.getUserName() + " from "
//                    + JSON_SAVED_RECIPE);
            jsonReader.readRecipes(account,"database");
//            System.out.println("Loaded all created recipes from " + account.getUserName() + " from "
//                    + JSON_DATABASE_RECIPE);
        } catch (IOException e) {
//            System.out.println("Unable to read from the files!");
        }
    }


}