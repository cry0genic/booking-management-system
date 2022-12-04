package Main;

import Model.*;
import Utility.SortingAlgo;
import Venue.*;
import java.util.*;

public class BookingSystem {
    public static final Object Lock = new Object();
    public static HashMap<User,String> userMap;
    private static ArrayList<Application> allBookings;
    public static LTC ltc = new LTC();
    public static NABAudi audi = new NABAudi();
    public static BrainStormingRoom brainStormingRoom = new BrainStormingRoom();
    private static ArrayList<Application> validBookings;
    public static final SortingAlgo  s = new SortingAlgo();

    private void init(){
        userMap = new HashMap<>();
        userMap.put(new Applicant("user1"),"1234");
        userMap.put(new Applicant("user2"),"1234");
        userMap.put(new Applicant("user3"),"1234");
        userMap.put(new Admin("admin"),"1234");
        allBookings = new ArrayList<>();
        validBookings = new ArrayList<>();
        exec();
    }
    BookingSystem(){
        init();
    }
    public static ArrayList<Application> getValidBookings(){
        return validBookings;
    }
    public static ArrayList<Application> getAllBookings(){
        return allBookings;
    }

    private void exec(){
        System.out.println("Welcome to the Booking System");
        Scanner s = new Scanner(System.in);
        int close = 0;
        synchronized (Lock){
            while(close!=-1){
                System.out.println("0 - Login ");
                System.out.println("1 - Quit");
                int q = s.nextInt();
                switch(q){
                    case 0 : {
                        new Login();
                        try{
                            Lock.wait();
                        }catch(Exception e){
                            System.out.println("A MultiThreading error has occured");
                        }
                    }break;
                    case 1 : close = -1; break;
                    default : {
                        System.out.println("Invalid Query Entered - Please Re Enter the Query");
                    }

                }
            }


        }
    }

    public static void main(String[] args) {
        new BookingSystem();

    }
}
