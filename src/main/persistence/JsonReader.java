package persistence;

import model.Account;
import model.Recipe;
import model.DataBase;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads account from JSON data stored in files for created recipes,
// saved recipes, and database recipes.



public class JsonReader {
    private String sourceCreatedRecipes;
    private String sourceSavedRecipes;
    private String sourceDatabase;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String sourceCreatedRecipes, String sourceSavedRecipes, String sourceDatabase) {
        this.sourceDatabase = sourceDatabase;
        this.sourceCreatedRecipes = sourceCreatedRecipes;
        this.sourceSavedRecipes = sourceSavedRecipes;

    }

    // REQUIRES: readMode is one of "created", "saved", and "database"
    // EFFECTS: reads the recipes from file and return the information to console
    //          when necessary.
    public void readRecipes(Account account, String readMode) throws IOException {
        String jsonData = "";

        if (readMode.equals("created")) {
            jsonData = readFile(sourceCreatedRecipes);
        } else if (readMode.equals("saved")) {
            jsonData = readFile(sourceSavedRecipes);
        } else {
            jsonData = readFile(sourceDatabase);
        }

        JSONObject jsonObject = new JSONObject(jsonData);
        parseAccount(account, jsonObject, readMode);

        // return parseAccount(jsonObject, readMode);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    // MODIFIES: account
    // EFFECTS: adds all the account information from the file to the account.
    public void parseAccount(Account account, JSONObject jsonObject, String readMode) {
        String name = jsonObject.getString("name");
//        Account account = new Account(name);

        addRecipes(account, jsonObject, readMode);
//        return account;
    }

    // MODIFIES: account
    // EFFECTS: reads from the file and adds the the recipes from the file to the corresponding
    //          lists in the account. (saved, created, database)
    public void addRecipes(Account account, JSONObject jsonObject, String readMode) {
        JSONArray recipeArray = jsonObject.getJSONArray("recipes");
        for (Object json : recipeArray) {
            JSONObject recipe = (JSONObject) json;
            addRecipe(account, recipe, readMode);
        }
    }

    // MODIFIES: account
    // EFFECTS: stores the recipe from the file to the current account.
    public void addRecipe(Account account, JSONObject jsonObject, String readMode) {
        String name = jsonObject.getString("name");
        JSONArray jsonIngredients = jsonObject.getJSONArray("ingredients");
        JSONArray jsonSteps = jsonObject.getJSONArray("steps");

        ArrayList<String> ingredients = jsonArrayToArrayListConverter(jsonIngredients);
        ArrayList<String> steps = jsonArrayToArrayListConverter(jsonSteps);
        account.addComponents(name, ingredients, steps, readMode);
    }

    // EFFECTS: converts the given JSON array into an ArrayList of strings and return it.
    public ArrayList<String> jsonArrayToArrayListConverter(JSONArray array) {
        ArrayList<String> stringArray = new ArrayList<>();

        for (int i = 0; i < array.length(); i++) {
            stringArray.add(array.getString(i));
        }

        return stringArray;
    }

}
