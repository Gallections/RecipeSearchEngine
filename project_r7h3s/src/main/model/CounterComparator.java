package model;

import java.util.Comparator;

public class CounterComparator implements Comparator<MatchCounter> {

    @Override
    public int compare(MatchCounter counter1, MatchCounter counter2) {
        return counter2.getCounter() - counter1.getCounter();
    }
}
