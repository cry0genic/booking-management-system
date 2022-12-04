package Venue;

import java.util.ArrayList;

public class LTC implements Venue {
    public ArrayList<Integer> roomNumbers = new ArrayList<>();
    public int roomStrength;
    public int code;
    public LTC(){
       init();
    }
    public void init(){
        roomNumbers = new ArrayList<>();
        int[] valid = {5102,5103,5105,5106};
        for(int i : valid){
            roomNumbers.add(i);
        }
        code = 0;
        this.roomStrength = 125;
    }
}
