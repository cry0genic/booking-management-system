package Utility;

import Model.Application;
import java.util.Comparator;

public class SortingAlgo  {

    private Comparator<Application> c;
    public SortingAlgo(){
        c = new Comparator<Application>() {
            @Override
            public int compare(Application o1, Application o2) {
                return o1.getToday().compareTo(o2.getToday());
            }
        };
    }
    public Comparator<Application> getComparator(){
        return c;
    }
}
