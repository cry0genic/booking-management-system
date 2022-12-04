package Model;
import Main.BookingSystem;
import java.util.*;
public class Admin implements Runnable,User{
    private String name;
    private Thread t;
    static  ArrayList<Application> LTCBookings = new ArrayList<>();
    static  ArrayList<Application> AudiBookings = new ArrayList<>();
    static ArrayList<Application> BrainstormingRoomBookings = new ArrayList<>();

    public Admin(String name){
        this.name = name;
    }
    private void init(ArrayList<Application> allBookings){
        for(Application a : allBookings){
            if(a.getVenueCode()==0) LTCBookings.add(a);
            else if(a.getVenueCode()==1) AudiBookings.add(a);
            else BrainstormingRoomBookings.add(a);
        }
    }

//    private void sort(ArrayList<Application> applications){
//        Comparator<Application> c = new Comparator<Application>() {
//            @Override
//            public int compare(Application o1, Application o2) {
//                return o1.getToday().compareTo(o2.getToday());
//            }
//        };
//        applications.sort(c);
//    }
    private void approve(Application a){
        a.setApproved();
    }

    public void viewLTCBookings(){
        for(Application a: LTCBookings){
            if(BookingSystem.getAllBookings().contains(a))System.out.println(a);
        }
    }
    public void viewNABAudiBookings(){
        for(Application a: AudiBookings){
            if(BookingSystem.getAllBookings().contains(a))System.out.println(a);
        }
    }

    public void viewBRBookings(){
        for(Application a: BrainstormingRoomBookings){
            if(BookingSystem.getAllBookings().contains(a))System.out.println(a);
        }
    }
    public void clearLTCBookings(){
        Scanner s = new Scanner(System.in);
        System.out.println("0 - Approve the first booking");
        System.out.println("1 - Disapprove the first booking");
        int code = s.nextInt();
        LTCBookings.sort(BookingSystem.s.getComparator());
        if(!LTCBookings.isEmpty()){
            if(code==0){
                System.out.println("Application approved");
                approve(LTCBookings.get(0));
                LTCBookings.get(0).setOpen();
                BookingSystem.getValidBookings().add(LTCBookings.get(0));
                Application toRemove = LTCBookings.get(0);
                BookingSystem.getAllBookings().remove(toRemove);
                for(Application a : LTCBookings){
                    if(a.isequal(toRemove)){
                        a.setOpen();
                        BookingSystem.getAllBookings().remove(a);
                    }
                }
            }else{
                System.out.println("Application rejected");
                LTCBookings.get(0).setOpen();
                LTCBookings.remove(0);
            }

        }
    }
    public void clearAudiBookings(){
        Scanner s = new Scanner(System.in);
        System.out.println("0 - Approve the first booking");
        System.out.println("1 - Disapprove the first booking");
        int code = s.nextInt();
        AudiBookings.sort(BookingSystem.s.getComparator());
        if(!AudiBookings.isEmpty()){
            if(code==0){
                System.out.println("Application approved");
                approve(AudiBookings.get(0));
                AudiBookings.get(0).setOpen();
                BookingSystem.getValidBookings().add(AudiBookings.get(0));
                Application toRemove = AudiBookings.get(0);
                BookingSystem.getAllBookings().remove(toRemove);
                for(Application a : AudiBookings){
                    if(a.isequal(toRemove)){
                        a.setOpen();
                        BookingSystem.getAllBookings().remove(a);
                    }
                }
            }else{
                System.out.println("Application rejected");
                AudiBookings.get(0).setOpen();
                AudiBookings.remove(0);
            }
        }
    }
    public void clearBRBookings(){
        Scanner s = new Scanner(System.in);
        System.out.println("0 - Approve the first booking");
        System.out.println("1 - Disapprove the first booking");
        int code = s.nextInt();
        BrainstormingRoomBookings.sort(BookingSystem.s.getComparator());
        if(!BrainstormingRoomBookings.isEmpty()){
            if(code==0){
                System.out.println("Application approved");
                approve(BrainstormingRoomBookings.get(0));
                BrainstormingRoomBookings.get(0).setOpen();
                BookingSystem.getValidBookings().add(BrainstormingRoomBookings.get(0));
                Application toRemove = BrainstormingRoomBookings.get(0);
                BookingSystem.getAllBookings().remove(toRemove);
                for(Application a : BrainstormingRoomBookings){
                    if(a.isequal(toRemove)){
                        a.setOpen();
                        BookingSystem.getAllBookings().remove(a);
                    }
                }

            }else{
                System.out.println("Application rejected");
                BrainstormingRoomBookings.get(0).setOpen();
                BrainstormingRoomBookings.remove(0);
            }
        }
    }

    public static void cancelBooking(Application application){
        if(LTCBookings.contains(application)) LTCBookings.remove(application);
        else if(BrainstormingRoomBookings.contains(application)) BrainstormingRoomBookings.remove(application);
        else if(AudiBookings.contains(application)) AudiBookings.remove(application);
    }
    public String getName(){
        return name;
    }
    public Thread getThread(){ return t;}
    public void setThread() {
        this.t = new Thread(this);
    }

    @Override
    public void run() {
        System.out.println("List of Queries -");
        System.out.println("1 - View LTC Bookings");
        System.out.println("2 - View NAB Auditorium Bookings");
        System.out.println("3 - View Brain Storming Room Bookings");
        System.out.println("4 - Clear LTC Bookings");
        System.out.println("5 - Clear NAB Auditorium Bookings");
        System.out.println("6 - Clear Brain Storming Room Bookings");
        System.out.println("7 - logout");
        Scanner s = new Scanner(System.in);
        init(BookingSystem.getAllBookings());
        int logout = 0;
        synchronized (BookingSystem.Lock){
            while(logout!=-1){
                System.out.println("Enter a new Query");
                int q = s.nextInt();
                switch(q){
                    case 1 : viewLTCBookings();break;
                    case 2 : viewNABAudiBookings();break;
                    case 3 : viewBRBookings();break;
                    case 4 : clearLTCBookings();break;
                    case 5 : clearAudiBookings();break;
                    case 6 : clearBRBookings();break;
                    case 7 :{
                        logout = -1;
                        BookingSystem.Lock.notifyAll();
                        break;
                    }
                    default : {
                        System.out.println("Invalid Query - Please Re Enter the query");
                        break;
                    }
                }
            }

        }

    }
}
