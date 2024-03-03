package ui;

import model.*;

import java.sql.Array;
import java.util.*;

public class RecipeSearchEngine {
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


    // EFFECTS: creates an instance of RecipeSearchEngine with specified username,
    //          fields searchCounters, currentSavedRecipes, currentCreatedRecipes, nameRecipes
    //          will be instantiated to empty ArrayLists, all the fields ending with 'Mode' will
    //          be instantiated as false.
    public RecipeSearchEngine(String username) {
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
    }


    // EFFECTS: guides the user by providing instructions in using the recipe search engine,
    //          responds depending on the user's input by calling respective methods.
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public void run() {
        String spacer = "     ";
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.println("Enter your request (type one of the following): \n"
                    + spacer + "search recipe" + "\n"
                    + spacer + "next recipe" + "\n"
                    + spacer + "create recipe" + "\n"
                    + spacer + "delete recipe" + "\n"
                    + spacer + "save current recipe" + "\n"
                    + spacer + "see all created recipes" + "\n"
                    + spacer + "see all saved recipes" + "\n"
                    + spacer + "see all recipes" + "\n"
                    + spacer + "previous recipe " + "\n"
                    + spacer + "quit");

            String response = input.nextLine();
            if (response.equals("search recipe")) {
                searchRecipeHandler(input);

            } else if (response.equals("create recipe")) {
                createRecipeHandler(input);

            } else if (response.equals("save current recipe")) {
                saveCurrentRecipe();

            } else if (response.equals("see all created recipes")) {
                seeAllCreatedRecipes();

            } else if (response.equals("see all saved recipes")) {
                seeAllSavedRecipes();

            } else if (response.equals("see all recipes")) {
                System.out.println("Select of one the options: (at once) or (one by one)");
                String answer = input.nextLine();
                if (answer.trim().equals("at once")) {
                    seeAllRecipesAtOnce();
                } else if (answer.trim().equals("one by one")) {
                    seeAllRecipes();
                } else {
                    System.out.println("The input was not one of the options!");
                }

            } else if (response.equals("next recipe")) {
                nextRecipe();

            } else if (response.equals("previous recipe")) {
                previousRecipe();

            } else if (response.equals("delete recipe")) {
                deleteRecipe();

            }  else if (response.equals("quit")) {
                System.out.println("You have successfully quit the system!");
                break;
            } else {
                System.out.println("The system does not recognize this command.");
            }
        }
    }

    // EFFECTS: takes in a Scanner and handles the input ingredients through more detailed
    //          console instructions and guidance. Classifies the user input into three categories,
    //          calls the searchRecipeByNameHandler method if 'name' is input, and call the searchRecipe
    //          method when the user input is 'one by one'
    public void searchRecipeHandler(Scanner input) {
        System.out.println("Search by name or ingredients (type either 'name' or 'ingredients')");
        String answer = input.nextLine();

        if (answer.equals("name")) {
            System.out.println("Enter the name of the recipe: ");
            String name = input.nextLine();
            searchRecipeByNameHandler(name);

        } else if (answer.equals("ingredients")) {
            System.out.println("Enter the ingredients you "
                    + "have (each ingredient separated by ','): ");
            String ingredients = input.nextLine();
            searchRecipe(ingredients);
        }
    }

    // EFFECTS: fulfills the user request by calling the searchRecipeByName function
    public void searchRecipeByNameHandler(String name) {
        reset();
        searchMode = true;

        account.searchRecipeByName(name, nameRecipes, DataBase.returnRecipes());

        if (nameRecipes.isEmpty()) {
            System.out.println("Sorry, there are no matching recipes in the database!");
        } else {
            nameRecipes.get(0).getRecipe();
        }
    }

    // EFFECTS: handles the user input when the input
    //          equals to 'create recipe', prompts the user to enter information of name, ingredients, and
    //          steps. Organizes the console output by adding indentations and list numbers.
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public void createRecipeHandler(Scanner input) {
        ArrayList<String> steps = new ArrayList<>();
        int stepCounter = 1;
        String stepConnector = "";

        System.out.println("Enter the name of your recipe: ");
        String nameRecipe = input.nextLine();
        System.out.println("Enter the ingredients required for this recipe (separated by ','): ");
        String ingredients = input.nextLine();

        String step = "";
        while (true) {

            if (stepCounter == 1) {
                stepConnector = "st";
            } else if (stepCounter == 2) {
                stepConnector = "nd";
            } else {
                stepConnector = "th";
            }
            System.out.println("Enter the " + stepCounter + stepConnector
                    + " step of your recipe (type 'finish' if all the steps are entered): ");
            step = input.nextLine();
            if (step.equals("finish")) {
                break;
            }
            steps.add(step);
            stepCounter++;
        }
        createRecipe(nameRecipe, ingredients, steps);
        System.out.println("You have successfully added the recipe!");
    }

    // MODIFIES: this
    // EFFECTS: returns all the recipes in the database sorted by the number of ingredients
    //          matched, likes and saves. Converts the input string to arraylist
    private void searchRecipe(String ingredients) {

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
        filteredRecipes.get(0).getRecipe();
    }

    // EFFECTS: calls the matchIngredientCounter function from the account with the user inputIngredients
    private ArrayList<Recipe> matchIngredientCounter(ArrayList<String> inputIngredients) {
        return account.matchIngredientCounter(inputIngredients, searchCounters, DataBase.returnRecipes());
    }

    // EFFECTS: returns the next recommended recipe depending on the current mode the user is viewing,
    //          this function will be activated when the user types 'next recipe'
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public void nextRecipe() {
        if (saveMode) {
            if (currentSavedRecipes.size() == recipeIndex + 1) {
                System.out.println("You are at the end of your list!");
            } else {
                recipeIndex++;
                currentSavedRecipes.get(recipeIndex).getRecipe();
            }
        } else if (createMode) {
            if (currentCreatedRecipes.size() == recipeIndex + 1) {
                System.out.println("You are at the end of your list!");
            } else {
                recipeIndex++;
                currentCreatedRecipes.get(recipeIndex).getRecipe();
            }
        } else if (recommendMode) {
            if (searchCounters.size() == recipeIndex + 1) {
                System.out.println("You are at the end of your list!");
            } else {
                recipeIndex++;
                searchCounters.get(recipeIndex).getAssocaitedRecipe().getRecipe();
            }
        } else if (viewMode) {
            if (DataBase.returnRecipes().size() == recipeIndex + 1) {
                System.out.println("You are at the end of Database list!");
            } else {
                recipeIndex++;
                DataBase.returnRecipes().get(recipeIndex).getRecipe();
            }
        } else if (searchMode) {
            if (nameRecipes.size() == recipeIndex + 1) {
                System.out.println("You are at the end of search list!");
            } else {
                recipeIndex++;
                nameRecipes.get(recipeIndex).getRecipe();
            }
        } else {
            System.out.println("You are currently not viewing any recipes! Please type"
                    + "one of \n(see all saved recipes, see all created recipes, see all recipes, or "
                    + "search recipe) before continuing");
        }
    }

    // EFFECTS: returns the previous recommended recipe depending on the user's current mode,
    //          this will be activated if the user types 'previous recipe'
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public void previousRecipe() {
        if (recommendMode) {
            if (recipeIndex > 0) {
                recipeIndex--;
                searchCounters.get(recipeIndex).getAssocaitedRecipe().getRecipe();
            } else {
                System.out.println("You are viewing the first recipe in the recommended list.");
            }
        } else if (createMode) {
            if (recipeIndex > 0) {
                recipeIndex--;
                currentCreatedRecipes.get(recipeIndex).getRecipe();
            } else {
                System.out.println("You are viewing the first recipe in the created list.");
            }
        } else if (saveMode) {
            if (recipeIndex > 0) {
                recipeIndex--;
                currentSavedRecipes.get(recipeIndex).getRecipe();
            } else {
                System.out.println("You are viewing the first recipe in the saved list.");
            }
        } else if (viewMode) {
            if (recipeIndex > 0) {
                recipeIndex--;
                DataBase.returnRecipes().get(recipeIndex).getRecipe();
            } else {
                System.out.println("You are viewing the first recipe in the Database.");
            }
        } else if (searchMode) {
            if (recipeIndex > 0) {
                recipeIndex--;
                nameRecipes.get(recipeIndex).getRecipe();
            } else {
                System.out.println("You are viewing the first recipe in your search list");
            }
        } else {
            System.out.println("You are not currently viewing any recipes! Please type"
                    + "one of \n(see all saved recipes, see all created recipes, see all recipes, or "
                    + "search recipe) before continuing");
        }
    }

    // EFFECTS: the system will create a recipe for the account
    private void createRecipe(String name, String ingredients, ArrayList<String> steps) {

        account.createRecipe(name, ingredients, steps);
    }

    // EFFECTS: deletes the recipe from the current created or saved list of recipes
    public void deleteRecipe() {
        if (createMode) {
            if (currentCreatedRecipes.isEmpty()) {
                System.out.println("You have not created any recipes yet!");
            } else {
                account.deleteRecipe(currentCreatedRecipes.get(recipeIndex));
                System.out.println("The current created recipe has been deleted!");
            }
        } else if (saveMode) {
            if (currentSavedRecipes.isEmpty()) {
                System.out.println("You have no saved recipes!");

            } else {
                account.removeSavedRecipe(currentSavedRecipes.get(recipeIndex));
                System.out.println("The current saved recipe has been deleted from the save list!");
            }
        } else if (viewMode) {
            System.out.println("You cannot delete recipes from the Database!");
        } else if (recommendMode || searchMode) {
            System.out.println("you cannot delete recipes that are recommended to you!");
        }
    }


    // EFFECTS: save the current recipe that the user is viewing to the currentSavedRecipes
    public void saveCurrentRecipe() {
        if (searchCounters.isEmpty() && nameRecipes.isEmpty()) {
            System.out.println("No recipes have been selected!");
        } else if (recommendMode) {
            account.addSavedRecipe(searchCounters.get(recipeIndex).getAssocaitedRecipe());
            System.out.println("The current recipe has been saved to the save list!");
        } else {
            account.addSavedRecipe(nameRecipes.get(recipeIndex));
            System.out.println("The current recipe has been saved to the save list!");
        }
    }

    // MODIFIES: this
    // EFFECTS: access all the recipes created by the user one by one
    public void seeAllCreatedRecipes() {
        reset();
        createMode = true;
        currentCreatedRecipes = account.getCreatedRecipes();

        if (currentCreatedRecipes.isEmpty()) {
            System.out.println("You have yet created any recipes!");
        } else {

            currentCreatedRecipes.get(recipeIndex).getRecipe();
        }
    }

    // MODIFIES: this
    // EFFECTS: access all the recipes in the user's saved list one by one
    public void seeAllSavedRecipes() {
        reset();
        saveMode = true;
        currentSavedRecipes = account.getSavedRecipes();

        if (currentSavedRecipes.isEmpty()) {
            System.out.println("You have no saved recipes!");
        } else {
            currentSavedRecipes.get(recipeIndex).getRecipe();
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

    // EFFECTS: access the recipes in the database at once, all recipes will be printed
    public void seeAllRecipesAtOnce() {
        reset();
        for (Recipe recipe : DataBase.returnRecipes()) {
            recipe.getRecipe();
        }
    }

    // EFFECTS: access all the recipes in the database one by one.
    public void seeAllRecipes() {
        reset();
        viewMode = true;
        DataBase.returnRecipes().get(recipeIndex).getRecipe();
    }
}