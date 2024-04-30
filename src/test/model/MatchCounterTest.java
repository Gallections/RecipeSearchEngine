package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;



public class MatchCounterTest {

    MatchCounter mc;
    Recipe recipe;

    @BeforeEach
    void setup() {
        recipe = new Recipe("Mashed Potatoes");
        mc = new MatchCounter(recipe);
    }

    @Test
    void constructorTest() {
        assertEquals(0, mc.getCounter());
        assertEquals(recipe, mc.getAssocaitedRecipe());
    }

    @Test
    void matchIncreaseTest() {
        assertEquals(0, mc.getCounter());

        mc.matchIncrease();
        assertEquals(1, mc.getCounter());

        mc.matchIncrease();
        mc.matchIncrease();
        assertEquals(3, mc.getCounter());
    }

}
