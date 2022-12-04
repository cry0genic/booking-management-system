package Venue;

import java.util.ArrayList;

public class NABAudi implements Venue {
    public ArrayList<Integer> roomNumbers = new ArrayList<>();
    public int roomStrength;
    private int code;
    public NABAudi(){
       init();
    }

    public void init(){
        roomNumbers = new ArrayList<>();
        roomNumbers.add(1234);
        roomStrength = 250;
        code = 1;
    }
}
