package org.example;


import org.example.dto.TravelDto;
import org.example.service.ServiceImp;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Main {
    private static Scanner entry = new Scanner(System.in);
    //this will get the language to execute the app
    //private static String language = "en";
    private static Locale currentLocale;
    private static ResourceBundle messages;

    //this method init the program when it's run
    public static void main(String[] args) {
        init();
    }
    //this method select the language and the repository we are using
    //we only have prepared english, arabic and chinese, but we could support as many languages as we are willing tot translate
    public static void init(){
       System.out.println("Wellcome, please select a language\n" +
                "1. English\n" +
                "2. Chinese(中文)\n" +
                "3. Arabic(العربية)");
        int op = entry.nextInt();
        entry.nextLine();
        do{
            switch (op) {
            case 1:
                currentLocale = new Locale("en");
                break;
            case 2:
                currentLocale = new Locale("chi");
                break;
            case 3:
                currentLocale = new Locale("ar");
                break;
            default:
                System.out.println("Option not available, check again");
                op = entry.nextInt();
                entry.nextLine();
       }
       }while (op < 1 || op > 3);
        welcome(op);

    }
    //this method will show the diferent travel options on the language demand by the user
    //we could expand the program offering to choose one option and buying it, for example
    public static void welcome(int op){
        ServiceImp serviceImp = new ServiceImp(op);
        messages = ResourceBundle.getBundle("MessagesBundle", currentLocale);
        System.out.println(messages.getString("welcome"));
        String country = null;
        //Because chinese uses complex characters (instead of letters),
        //we are giving special permissions to use one only character on the search
        //since there are cities that are written by only one character
        //the error message ask for only 1 character
        if (op == 2){
            do {
                country = entry.nextLine();
                if (country.length() < 1){
                    System.out.println(messages.getString("lengthError"));
                }
            } while (country.length() < 1);
        } else {
            do {
                country = entry.nextLine();
                if (country.length() < 3){
                    System.out.println(messages.getString("lengthError"));
                }
            } while (country.length() < 3);
        }

        List<TravelDto> travelOptions = serviceImp.getTravelsTo(country);
        if (travelOptions.isEmpty()){
            System.out.println(messages.getString("empty"));
        } else {
            System.out.println(travelOptions);
        }


    }
}