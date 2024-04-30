package persistence;

import model.Account;
import org.json.JSONObject;

import java.io.*;
import java.nio.file.FileSystemNotFoundException;

// Represents a writer that writes JSON representation of account to files for created recipes,
// saved recipes, and database recipes.

public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destinationCreatedRecipes;
    private String destinationSavedRecipes;
    private String destinationDataBase;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destinationCreatedRecipes, String destinationSavedRecipes, String destinationDataBase) {
        this.destinationCreatedRecipes = destinationCreatedRecipes;
        this.destinationSavedRecipes = destinationSavedRecipes;
        this.destinationDataBase = destinationDataBase;

    }

    // REQUIRES: writeMode is one of "created", "saved", or "database"
    // MODIFIES: account
    // EFFECTS: opens writer; throws FileNotFoundException if destination file
    //          cannot be found
    public void open(String writeMode) throws FileNotFoundException  {
        String destination = "";

        if (writeMode.equals("created")) {
            destination = destinationCreatedRecipes;
        } else if (writeMode.equals("saved")) {
            destination = destinationSavedRecipes;
        } else {
            destination = destinationDataBase;
        }

        writer = new PrintWriter(new File(destination));

    }

    // MODIFIES: account
    // EFFECTS: write JSON representation of account to file
    public void write(Account account, String writeMode) {
        account.changeWritingMode(writeMode);

        JSONObject json = account.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }

}
