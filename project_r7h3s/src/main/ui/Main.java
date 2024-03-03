package ui;

import model.DataBase;
import model.Account;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        RecipeSearchEngine engine = new RecipeSearchEngine("Billy");
        engine.run();
    }
}
