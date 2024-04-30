package model;

/*
This class represents a temporary container that is created when the user asks for a
recommendation of recipes. This class contains an associated recipes and the number of times
the associated recipe has ingredients that match the user input.
 */

public class MatchCounter {

    private Recipe associatedRecipe;  // the recipe that the MatchCounter is associated with
    private int counter;              // the number of occurrences the associated recipe appear in a search

    // EFFECTS: creates an instance of MatchCounter with a specified recipe and counter set to 0
    public MatchCounter(Recipe recipe) {
        associatedRecipe = recipe;
        this.counter = 0;
    }

    // EFFECTS: creates an instance of MatchCounter with specified recipe and counter,
    //          this is used for mainly testing purposes.
    public MatchCounter(Recipe recipe, int counter) {
        associatedRecipe = recipe;
        this.counter = counter;
    }

    // MODIFIES: this
    // EFFECTS: increases the counter by 1
    public void matchIncrease() {
        this.counter++;
    }

    // EFFECTS: returns the counter number
    public int getCounter() {
        return counter;
    }

    // EFFECTS: return the associated recipe
    public Recipe getAssocaitedRecipe() {
        return associatedRecipe;
    }

}
