package Model;
import Main.BookingSystem;
import java.time.LocalDateTime;
import java.util.Comparator;


public class Application {

    private boolean approved;
    private boolean open;
    private String reason;
    private String bookingID;
    private String pseudoID;
    private LocalDateTime today;
    private String TimeSlot;
    private int BookingDay;
    private int BookingMonth;
    private int venueCode;
    private int roomNumber;
    private int groupSize;


    public Application(int day,int month, String TimeSlot,int venueCode,int roomNumber,int groupSize,String reason){
        this.open = true;
        this.venueCode = venueCode;
        this.TimeSlot = TimeSlot;
        this.BookingDay = day;
        this.BookingMonth = month;
        this.reason = reason;
        this.roomNumber = roomNumber;
        this.groupSize = groupSize;
        this.pseudoID = day+"."+month+"."+"."+venueCode+"."+roomNumber;
        this.bookingID = day+"."+month+"."+TimeSlot+"."+venueCode+"."+roomNumber;
        this.today = LocalDateTime.now();
        BookingSystem.getAllBookings().add(this);
    }
    public String toString(){
        String date = BookingDay+"/"+BookingMonth;
        String timing = "";
        for(int i=0;i<TimeSlot.length();i++){
            char c = TimeSlot.charAt(i);
            switch(c){
                case 'A' : timing+="8am - 9am ";break;
                case 'B' : timing+="9am - 10am ";break;
                case 'C' : timing+="10am - 11am ";break;
                case 'D' : timing+="11am - 12pm ";break;
                case 'E' : timing+="12pm - 1pm ";break;
                case 'F' : timing+="1pm - 2pm ";break;
                case 'G' : timing+="2pm - 3pm ";break;
                case 'H' : timing+="3pm - 4pm ";break;
                case 'I' : timing+="4pm - 5pm ";break;
                case 'J' : timing+="5pm - 6pm ";break;
            }
            if(i<TimeSlot.length()-1) timing+=",";
        }
        String v = "";
        if(venueCode==0) v = "LTC";
        else if(venueCode==1) v= "NAB Auditorium";
        else v= "BrainStorming Room";
        String t = (open) ? "Application Open" : "Application Closed";
        String a;
        if(open){
            a = "In Queue";
        }else{
            a = (approved) ? "Approved" : "Disapproved";
        }
        return "Date - "+date+"|"+" Venue - "+v+"|"+"RoomNumber - "+roomNumber+"|"+" Timing - "+timing+" "+"| "+reason+"| "+a+" | "+t;
    }
    public String getPseudoID(){return pseudoID;}
    public LocalDateTime getToday(){
        return today;
    }
    public String getBookingID(){
        return bookingID;
    }
    public String getTimeSlot(){
        return TimeSlot;
    }
    public int getBookingDay(){ return BookingDay;};
    public int getBookingMonth() {
        return BookingMonth;
    }
    public int getVenueCode() {
        return venueCode;
    }
    public boolean isApproved() {
        return approved;
    }
    public void setApproved(){
        approved = true;
    }
    public boolean getOpen(){
        return open;
    }
    public void setOpen(){
        open = false;
    }
    public boolean isequal(Application a) {
       return a.getBookingID().equals(this.bookingID);
    }
}
