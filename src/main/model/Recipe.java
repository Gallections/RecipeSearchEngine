package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import javax.swing.*;
import java.awt.*;
import java.util.*;

/*

Represents a recipe,
is consisted of a recipe name, a list of ingredients, a list of procedures,
and an integer that keeps track of the total number of saves that the recipe have received.
This class also allows you to add steps, ingredients, and name.
 */
public class Recipe implements Writable {

    private ArrayList<String> steps;
    private ArrayList<String> ingredients;
    private String name;
    private int saves = 0;


    // EFFECTS: creates an instance of the recipe with empty steps and ingredients, instantiate the
    //          name input as the recipe name;
    public Recipe(String name) {
        this.name = name;                        // the name of the recipe
        steps = new ArrayList<String>();         // the list of steps
        ingredients = new ArrayList<String>();   // the list of ingredients
    }

    // EFFECTS: creates an instance of the recipe with specified name, steps and ingredients,
    //          this is mainly used for building tests.
    public Recipe(String name, ArrayList<String> ingredients, ArrayList<String> steps) {
        this.name = name;
        this.steps = steps;
        this.ingredients = ingredients;
    }

    // MODIFIES: this
    //EFFECTS: adds a new step to the steps list
    public void addStep(String step) {
        steps.add(step);
    }

    // MODIFIES: this
    // EFFECTS: add a new ingredient to the ingredients list
    public void addIngredient(String ingredient) {
        ingredients.add(ingredient);
    }

    // EFFECTS: return the list of ingredients
    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    // EFFECTS: return the list of steps in the recipe
    public ArrayList<String> getSteps() {
        return steps;
    }

    // EFFECTS: formats the recipe on the console
    public String getRecipe() {
        String consoleOutput = "";
        for (int i = 0; i < 87; i++) {
            consoleOutput += "-";
        }
        consoleOutput += "\n\nRecipe Name: " + name + "\n\n";
        consoleOutput += "Ingredients: ----------- " + "\n";
        for (int i = 0; i < ingredients.size(); i++) {
            consoleOutput += "\n" + (i + 1) + ". " + ingredients.get(i);
        }
        consoleOutput += "\n\nProcedures: ------------" + "\n";
        String stepsConsole = "";
        for (int i = 0; i < steps.size(); i++) {
            String step = steps.get(i);
            stepsConsole += "\n" + (i + 1) + ". " + stepConsoleOrganizer(step);
        }
        consoleOutput += stepsConsole + "\n\n";
        for (int i = 0; i < 87; i++) {
            consoleOutput += "-";
        }
        consoleOutput += "\n\n";
//        System.out.println(consoleOutput);
        return consoleOutput;
    }

    // MODIFIES: step
    // EFFECTS: limits the length of the characters of each step printed on the console to ensure
    //          user's best viewing experience, reconfigure the length of each step each line.
    public String stepConsoleOrganizer(String step) {
        if (step.length() >= 60) {
            return step.substring(0,60)
                    + "\n   " + stepConsoleOrganizer(step.substring(60));
        } else {
            return step;
        }
    }

    // MODIFIES: this
    // EFFECTS: adds one save to saves.
    public void addSaves() {
        this.saves++;
    }

    // MODIFIES: this
    // EFFECTS: remove a save from saves.
    public void removeSave() {
        this.saves--;
    }


    // EFFECTS: returns the name of the recipe
    public String getName() {
        return this.name;
    }

    // EFFECTS: returns the number of saves the recipe received.
    public int getNumSaves() {
        return this.saves;
    }


    // EFFECTS: Overrides the toJson function and turns the information of the recipe
    //          into JSON Objects
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();

        json.put("name", name);
        json.put("ingredients", jsonArrayConverter(ingredients));
        json.put("steps", jsonArrayConverter(steps));

        return json;
    }

    // EFFECTS: converts an ArrayList of Strings into a JSONArray.
    private JSONArray jsonArrayConverter(ArrayList<String> stringArray) {
        JSONArray array = new JSONArray();
        for (String component : stringArray) {
            array.put(component);
        }

        return array;
    }

}
