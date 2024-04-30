package persistence;

import model.*;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Represents a reader that reads account from JSON data and stored into files.

public class JsonReaderTest extends JsonTest{

    String realSource;
    String fakeSource;

    Account account;

    String created;
    String saved;
    String database;
    String emptyCreated;
    String emptySaved;
    String emptyDatabase;

    @BeforeEach
    void setup() {
        realSource = "./data/testGeneralRecipes.json";
        fakeSource = "./data/NoSuchFile.json";

        created = "./data/testReaderGeneralCreatedRecipes.json";
        saved = "./data/testReaderGeneralSavedRecipes.json";
        database= "./data/testReaderGeneralDatabase.json";

        emptyCreated = "./data/testReaderEmptyCreatedRecipes.json";
        emptySaved = "./data/testReaderEmptySavedRecipes.json";
        emptyDatabase = "./data/testReaderEmptyDatabase.json";

        account = new Account("Username");
    }

    @Test
    void testReaderNonExistentFileCreated() {
        JsonReader reader = new JsonReader(fakeSource, realSource, realSource);
        try {
            reader.readRecipes(account, "created");
            fail("IOException expected");
        } catch (IOException e) {
            //
        }
    }

    @Test
    void testReaderNonExistentFileSaved() {
        JsonReader reader = new JsonReader(realSource, fakeSource, realSource);
        try {
            reader.readRecipes(account, "saved");
            fail("IOException expected");
        } catch (IOException e) {
            //
        }
    }

    @Test
    void testReaderNonExistentFileDatabase() {
        JsonReader reader = new JsonReader(realSource, realSource, fakeSource);
        try {
            reader.readRecipes(account, "database");
            fail("IOException expected");
        } catch (IOException e) {
        }
    }


    @Test
    void testReaderEmptyCreated() {
        JsonReader reader = new JsonReader(emptyCreated, realSource, realSource);
        try {
            reader.readRecipes(account, "created");
            assertEquals("Username", account.getUserName());
            assertEquals(0, account.getCreatedRecipes().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderEmptySaved() {
        JsonReader reader = new JsonReader(realSource, emptySaved, realSource);
        try {
            reader.readRecipes(account, "saved");
            assertEquals("Username", account.getUserName());
            assertEquals(0, account.getSavedRecipes().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderEmptyDatabase() {
        account.erase();
        JsonReader reader = new JsonReader(realSource, realSource, emptyDatabase);
        try {
            reader.readRecipes(account, "database");
            assertEquals("Username", account.getUserName());
            assertEquals(0, DataBase.returnRecipes().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralAccountCreated() {
        JsonReader reader = new JsonReader(created, realSource, realSource);
        try {
            reader.readRecipes(account, "created");
            assertEquals("Username", account.getUserName());

            ArrayList<Recipe> recipes = account.getCreatedRecipes();

            assertEquals(2, account.getCreatedRecipes().size());
            checkRecipe("pickle juice",
                    new ArrayList<>(Arrays.asList("pickles", "pepper")),
                    new ArrayList<>(Arrays.asList("step1", "step2", "step3")),
                    recipes.get(0));

            checkRecipe("recipe2",
                    new ArrayList<>(Arrays.asList("food1", "food2", "food3")),
                    new ArrayList<>(Arrays.asList("step1", "step2")),
                    recipes.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralAccountSaved() {
        JsonReader reader = new JsonReader(realSource, saved, realSource);
        try {
            reader.readRecipes(account, "saved");
            assertEquals("Username", account.getUserName());

            ArrayList<Recipe> recipes = account.getSavedRecipes();

            assertEquals(2, account.getSavedRecipes().size());
            checkRecipe("pickle juice",
                    new ArrayList<>(Arrays.asList("pickles", "pepper")),
                    new ArrayList<>(Arrays.asList("step1", "step2", "step3")),
                    recipes.get(0));

            checkRecipe("recipe2",
                    new ArrayList<>(Arrays.asList("food1", "food2", "food3")),
                    new ArrayList<>(Arrays.asList("step1", "step2")),
                    recipes.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralAccountDatabase() {
        JsonReader reader = new JsonReader(realSource, realSource, database);
        try {
            reader.readRecipes(account, "database");
            assertEquals("Username", account.getUserName());

            ArrayList<Recipe> recipes = DataBase.returnRecipes();

            assertEquals(2, recipes.size());
            checkRecipe("pickle juice",
                    new ArrayList<>(Arrays.asList("pickles", "pepper")),
                    new ArrayList<>(Arrays.asList("step1", "step2", "step3")),
                    recipes.get(0));

            checkRecipe("recipe2",
                    new ArrayList<>(Arrays.asList("food1", "food2", "food3")),
                    new ArrayList<>(Arrays.asList("step1", "step2")),
                    recipes.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }









}
