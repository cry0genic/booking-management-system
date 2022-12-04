package Model;

import Main.BookingSystem;
import Utility.*;
import java.util.*;


public class Applicant implements User{

    private String name;
    private ArrayList<Application> appliedFor;
    private Thread t;

    public Applicant(String name){
        this.name = name;
        appliedFor = new ArrayList<>();
    }

    private synchronized String getDate(){
        Scanner s = new Scanner(System.in);
        System.out.println("Please enter the date of Booking in dd/mm/yy format");
        String date = s.next();
        StringTokenizer st = new StringTokenizer(date,"/");
        try{
            int day = Integer.parseInt(st.nextToken());
            int month = Integer.parseInt(st.nextToken());
            if(day>31||month>12){
                throw new DateFormatException();
            }
        }catch(DateFormatException e){
            System.out.println("Please Re-Enter the correct date");
            date = getDate();
        }
        return date;
    }
    private synchronized String getTimeSlot(int day,int month,int venueCode,int roomNumber){
        System.out.println("Please Enter the Time Slot");
        Scanner s = new Scanner(System.in);
        String TimeSlot = s.next();
        String pseudoID = day+"."+month+"."+"."+venueCode+"."+roomNumber;
        try{
            for(Application a: BookingSystem.getValidBookings()){
                if(a.getPseudoID().equals(pseudoID)){
                    String check = a.getTimeSlot();
                    for(int i=0;i<TimeSlot.length();i++){
                        for(int j=0;j<check.length();j++){
                            if(TimeSlot.charAt(i)==check.charAt(j)){
                                throw new TimeClashException();
                            }
                        }
                    }
                }
            }
        }catch(TimeClashException e){
            System.out.println("Time Slot Unavailable - Please Re enter the TimeSlot");
            TimeSlot = getTimeSlot(day, month, venueCode, roomNumber);
        }
        return TimeSlot;
    }
    private synchronized int getVenueCode(){
        Scanner s = new Scanner(System.in);
        System.out.println("LTC - 0, NABAudi - 1, BrainStormingRoom - 2");
        System.out.println("Please enter the venue code");
        int code  = s.nextInt();
        return code;
    }
    private synchronized int getGroupSize(int venueCode){
        Scanner s = new Scanner(System.in);
        System.out.println("Please enter the group size");
        int groupSize = s.nextInt();
        try{
            int roomStrength;
            switch(venueCode){
                case 0 : roomStrength = BookingSystem.ltc.roomStrength;break;
                case 1 : roomStrength = BookingSystem.audi.roomStrength;break;
                case 2 : roomStrength  = BookingSystem.brainStormingRoom.roomStrength;break;
                default : roomStrength = 0;break;
            }
            if(groupSize>roomStrength) {
                throw new RoomStrengthExceededException();
            }
        }catch(RoomStrengthExceededException e){
            System.out.println("The group is exceeding the room Size");
            groupSize = getGroupSize(venueCode);
        }
        return groupSize;
    }
    private synchronized String getReason(){
        Scanner s = new Scanner(System.in);
        System.out.println("Please enter a valid reason for booking");
        String str = s.next();
        return str;
    }
    private synchronized int getRoomNumber(int venueCode){
        Scanner s = new Scanner(System.in);
        System.out.println("Please enter the room number");
        int roomNumber = s.nextInt();
        try{
            ArrayList<Integer> validRooms = new ArrayList<>();
            switch(venueCode){
                case 0 : {
                    validRooms = BookingSystem.ltc.roomNumbers;
                    break;
                }
                case 1 : {
                    validRooms = BookingSystem.audi.roomNumbers;
                    break;
                }
                case 2 : {
                    validRooms  = BookingSystem.brainStormingRoom.roomNumbers;
                    break;
                }
                default :{
                    validRooms = new ArrayList<>();
                    break;
                }
            }

            if(!validRooms.contains(roomNumber)){
                throw new InvalidRoomException();
            }
        }catch(InvalidRoomException e){
            System.out.println("The room to be booked does not exist - Please Re - enter the roomNumber ");
            roomNumber = getRoomNumber(venueCode);
        }
        return roomNumber;
    }
    private Application checkForID(String applicationID){
        for(Application a: appliedFor){
            if(a.getBookingID().equals(applicationID)) return a;
        }
        return null;
    }



    public void createBooking(){
        Scanner s = new Scanner(System.in);
        String date = getDate();
        StringTokenizer st = new StringTokenizer(date,"/");
        int day = Integer.parseInt(st.nextToken());
        int month = Integer.parseInt(st.nextToken());
        int code = getVenueCode();
        String reason = getReason();
        int groupSize = getGroupSize(code);
        int roomNumber = getRoomNumber(code);
        String TimeSlot = getTimeSlot(day,month,code,roomNumber);
        Application a =  new Application(day,month,TimeSlot,code,roomNumber,groupSize,reason);
        System.out.println("Application Created Successfully");
        System.out.println("Application ID - "+a.getBookingID());
        appliedFor.add(a);
    }
    public void cancelBooking(){
        System.out.println("Please enter the Booking ID -");
        Scanner s = new Scanner(System.in);
        String applicationID = s.next();
        try{
            if(checkForID(applicationID)==null){
                throw new WrongApplicationIDException();
            }
        }catch(WrongApplicationIDException e){
            System.out.println("Wrong ID entered, Please Reenter the ID");
            cancelBooking();
        }

       for(Application a: appliedFor){
           if(a.getBookingID().equals(applicationID)){
               System.out.println("Searching for the application");
               BookingSystem.getAllBookings().remove(a);
               BookingSystem.getValidBookings().remove(a);
               Admin.cancelBooking(a);
               appliedFor.remove(a);
               break;
           }
       }
        System.out.println("Your application for booking has been removed");
    }
    public void checkBookingStatus() {
        System.out.println("Please enter the Booking ID -");
        Scanner s = new Scanner(System.in);
        String applicationID = s.next();
        Application application = checkForID(applicationID);
        try {
            if (application == null) {
                throw new WrongApplicationIDException();
            } else {
                if (application.isApproved()) {
                    System.out.println("Your application is approved");
                } else {
                    System.out.println("Your application is not approved");
                }
            }
        } catch (WrongApplicationIDException e) {
            System.out.println("Wrong ID entered, Please Reenter the ID");
            checkBookingStatus();
        }
    }
    public void displayBooking(){
        System.out.println("Please enter the Booking ID -");
        Scanner s = new Scanner(System.in);
        String applicationID = s.next();
        Application application = checkForID(applicationID);
        try{
            if(application==null){
                throw new WrongApplicationIDException();
            }
            System.out.println(application);
        }catch(WrongApplicationIDException e){
            System.out.println("Wrong ID entered, Please Reenter the ID");
            displayBooking();
        }
    }
    public void displayAllBookings(){
        System.out.println("Displaying all Bookings");
        for(Application a : appliedFor){
            System.out.println(a);
        }
    }
    public String getName(){
        return name;
    }
    public Thread getThread(){ return t; }
    public void setThread() {
        this.t = new Thread(this);
    }
    @Override
    public void run() {
        System.out.println("List of queries");
        System.out.println("1 - create a Booking");
        System.out.println("2 - check a booking status");
        System.out.println("3 - cancel your booking");
        System.out.println("4 - display a booking");
        System.out.println("5 - display all bookings");
        System.out.println("6 - logout");
        int logout = 0;
        Scanner s = new Scanner(System.in);
        synchronized (BookingSystem.Lock){
            while(logout!=-1){
                System.out.println("Enter a new query -");
                int q = s.nextInt();
                switch(q){
                    case 1 : {
                        createBooking();
                        break;
                    }
                    case 2 : {
                        checkBookingStatus();
                        break;
                    }
                    case 3 : {
                        cancelBooking();
                        break;
                    }
                    case 4 :{
                        displayBooking();
                        break;
                    }
                    case 5 :{
                        displayAllBookings();
                        break;
                    }
                    case 6 : {
                        logout = -1;
                        BookingSystem.Lock.notifyAll();
                        break;
                    }
                    default:{
                        System.out.println("Invalid Query - Please Enter valid query");
                    }
                }
            }
        }

    }


}