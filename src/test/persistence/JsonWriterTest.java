package persistence;

import model.*;
import org.json.JSONObject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest extends JsonTest{

    String realSource;
    String fakeSource;

    Account account;

    String created;
    String saved;
    String database;
    String emptyCreated;
    String emptySaved;
    String emptyDatabase;

    Recipe recipe1;
    Recipe recipe2;

    @BeforeEach
    void setup() {
        realSource = "./data/testGeneralRecipes.json";
        fakeSource = "./data/N\0oSuchFile.json";

        created = "./data/testWriterGeneralCreatedRecipes.json";
        saved = "./data/testWriterGeneralSavedRecipes.json";
        database= "./data/testWriterGeneralDatabase.json";

        emptyCreated = "./data/testWriterEmptyCreatedRecipes.json";
        emptySaved = "./data/testWriterEmptySavedRecipes.json";
        emptyDatabase = "./data/testWriterEmptyDatabase.json";

        account = new Account("Username");

        recipe1 = new Recipe("pickle juice",
                new ArrayList<>(Arrays.asList("pickles", "pepper")),
                new ArrayList<>(Arrays.asList("step1", "step2", "step3")));
        recipe2 = new Recipe("recipe2",
                new ArrayList<>(Arrays.asList("food1", "food2", "food3")),
                new ArrayList<>(Arrays.asList("step1", "step2")));
    }

    @Test
    void testWriterInvalidFileCreated() {
        JsonWriter writer = new JsonWriter(fakeSource, realSource, realSource);
        try {
            writer.open("created");
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterInvalidFileSaved() {
        JsonWriter writer = new JsonWriter(realSource, fakeSource, realSource);
        try {
            writer.open("saved");
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterInvalidFileDatabase() {
        JsonWriter writer = new JsonWriter(realSource, realSource, fakeSource);
        try {
            writer.open("database");
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyAccountCreated() {
        try {
            JsonWriter writer = new JsonWriter(emptyCreated, realSource, realSource);
            writer.open("created");
            writer.write(account, "created");
            writer.close();

            JsonReader reader = new JsonReader(emptyCreated, realSource, realSource);
            reader.readRecipes(account, "created");
            assertEquals("Username", account.getUserName());
            assertEquals(0, account.getCreatedRecipes().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterEmptyAccountSaved() {
        try {
            JsonWriter writer = new JsonWriter(realSource, emptySaved, realSource);
            writer.open("saved");
            writer.write(account, "saved");
            writer.close();

            JsonReader reader = new JsonReader(realSource, emptySaved, realSource);
            reader.readRecipes(account, "saved");
            assertEquals("Username", account.getUserName());
            assertEquals(0, account.getSavedRecipes().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterEmptyAccountDatabase() {
        account.erase();
        try {
            JsonWriter writer = new JsonWriter(realSource, realSource, emptyDatabase);
            writer.open("database");
            writer.write(account, "database");
            writer.close();

            JsonReader reader = new JsonReader(realSource, realSource, emptyDatabase);
            reader.readRecipes(account, "database");
            assertEquals("Username", account.getUserName());
            assertEquals(0, DataBase.returnRecipes().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralAccountCreated() {
        try {
            account.createRecipe("pickle juice",
                    "pickles, pepper",
                    new ArrayList<>(Arrays.asList("step1", "step2", "step3"))
            );

            account.createRecipe("recipe2",
                    "food1, food2, food3",
                    new ArrayList<>(Arrays.asList("step1", "step2")));

            JsonWriter writer = new JsonWriter(created, realSource, realSource);
            writer.open("created");
            writer.write(account, "created");
            writer.close();

            account.erase();
            JsonReader reader = new JsonReader(created, realSource, realSource);
            reader.readRecipes(account, "created");
            assertEquals("Username", account.getUserName());

            ArrayList<Recipe> recipes = account.getCreatedRecipes();

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
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralAccountSaved() {
        account.addSavedRecipe(recipe1);
        account.addSavedRecipe(recipe2);

        try {
            JsonWriter writer = new JsonWriter(realSource, saved, realSource);
            writer.open("saved");
            writer.write(account, "saved");
            writer.close();

            account.erase();
            JsonReader reader = new JsonReader(realSource, saved, realSource);
            reader.readRecipes(account, "saved");
            assertEquals("Username", account.getUserName());

            ArrayList<Recipe> recipes = account.getSavedRecipes();

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
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralAccountDatabase() {
        DataBase.addRecipe(recipe1);
        DataBase.addRecipe(recipe2);
        try {

            JsonWriter writer = new JsonWriter(realSource, realSource, database);
            writer.open("database");
            writer.write(account, "database");
            writer.close();

            account.erase();
            JsonReader reader = new JsonReader(realSource, realSource, database);
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
            fail("Exception should not have been thrown");
        }
    }



}
