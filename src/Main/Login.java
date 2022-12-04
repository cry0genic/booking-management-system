package Main;

import Model.*;
import Utility.*;

import java.util.*;

import static Main.BookingSystem.userMap;

public class Login{
    HashMap<User,String> map = userMap;
    Login(){
        Scanner s = new Scanner(System.in);
        System.out.println("Please enter your login credentials");
        System.out.println("Username - ");
        String username = s.next();
        System.out.println("Password - ");
        String password =  s.next();
        try{
            Set<User> userSet = map.keySet();
            int flag = 0;
            for(User u:userSet){
                if(u.getName().equals(username)&&map.get(u).equals(password)){
                    flag = 1;
                    if(u.getName().equals("admin")){
                        System.out.println("You are logged in as Admin");
                        u.setThread();
                        u.getThread().start();
                    }else{
                        System.out.println("You are logged in as a User");
                        u.setThread();
                        u.getThread().start();
                    }
                    break;
                }
            }
            if(flag==0) throw new InvalidUserNameOrPasswordException();
        }catch(InvalidUserNameOrPasswordException e){
            System.out.println("The UserName or Password entered is incorrect");
            new Login();
        }
    }
}

