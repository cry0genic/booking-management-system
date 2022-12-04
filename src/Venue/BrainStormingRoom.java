package Venue;

import Utility.DateFormatException;

import java.util.ArrayList;

public class BrainStormingRoom extends DateFormatException implements Venue {

    public ArrayList<Integer> roomNumbers = new ArrayList<>();
    public int roomStrength;
    public int code;
     public void init(){
        roomNumbers = new ArrayList<>();
        int[] valid = {101,102,103,104,105,106};
        for(int i: valid){
            roomNumbers.add(i);
        }
        this.roomStrength = 15;
        code = 2;
    }

    public BrainStormingRoom(){
         init();
    }

}
