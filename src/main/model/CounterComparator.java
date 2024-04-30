package model;

import java.util.Comparator;

/*
This represents a comparator function, it defines how the recommended recipes
are sorted. In this case, it is sorted based on the number of occurrence of each recipe
as MatchCounters.
 */

public class CounterComparator implements Comparator<MatchCounter> {

    // EFFECTS: this function defines the method of comparison between MatchCounters. This method
    //          sorts the list of MatchCounters based on the number of occurrences from largest to
    //          smallest.
    @Override
    public int compare(MatchCounter counter1, MatchCounter counter2) {
        return counter2.getCounter() - counter1.getCounter();
    }

}
