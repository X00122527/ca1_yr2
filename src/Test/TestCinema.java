/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import dbService.JPAService;
import dbService.MovieOperations;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Movies;
import model.Users;

/**
 *
 * @author T450
 * 
 * X00122527 - TYMOTEUSZ WISNIEWSKI
 * X00128890 - JASON PLUMMER
 * 
 * there are two types of users, member and admin, admin example is email : t@gmail.com password: abcde
 */
public class TestCinema {

    /**
     * @param args the command line arguments
     */
    static JPAService jpac = new JPAService();
    final static double CHILDRENDISCOUNT = 0.70;
    static String email;
    static Users u;

    public static void main(String[] args) {
        int adults, children, choice1;
        String name, password, confirmPassword;
        boolean passed = true;

        displayMenu();
    }

    public static void displayMenu() {
        Scanner input = new Scanner(System.in);
        boolean passed = false;
        System.out.println("\n\n\t- Welcome to Online Ticket Booking -\t");

        System.out.println("1. Log in as an existing user.");

        System.out.println("2. Create an account.");

        System.out.println("3. Exit.");

        int verification = input.nextInt();
        input.nextLine();
        switch (verification) {
            case 1:
                System.out.println("Your email: ");
                email = input.nextLine();

                System.out.println("Your password: ");
                String password = input.nextLine();
                boolean found = jpac.findUser(email, password);
                System.out.println("found status : " + found);
                if (found == false) {
                    System.out.println("Incorrect details, please try again.");
                    displayMenu();
                    passed = false;
                } else {
                    System.out.println("Hi " + email);
                    passed = true;
                    displayMenu2();
                    break;
                }

            case 2:
                System.out.println("Enter your name:");
                String name = input.nextLine();
                System.out.println("Enter your email address: ");
                email = input.nextLine();
                System.out.println("Enter your password: ");
                password = input.nextLine();
                System.out.println("Confirm your password: ");
                String confirmPassword = input.nextLine();
                if (!password.equals(confirmPassword)) {
                    System.out.println("Your password does not match!");
                    displayMenu();
                } else {
                    jpac.createUser(name, email, password);
                    System.out.println("You have been registered.");
                    displayMenu();

                }
                passed = true;
                break;

            case 3:
                System.exit(1);
        }

    }

    public static void displayMenu2() {
        Scanner input = new Scanner(System.in);
        int choice1;
        System.out.println("\n1. Check what's on.");
        System.out.println("2. Book your tickets.");
        System.out.println("3. Admin tools"); // add if statement
        System.out.println("4. Your order");
        

        //}
        int choice = input.nextInt();
        input.nextLine();
        switch (choice) {
            case 1:
                displayMenu3();
                break;
            case 2:
                displayMenu4();
                break;

            case 3:
                if (jpac.findUserType(email).equals("admin")) {
                    displayAdminMenu();
                } else {
                    System.out.println("You don't have rights to display admin tools.");
                    displayMenu2();
                }

                break;
            case 4:
            //displayOrderMenu();
                System.out.println("Your order: ");
                jpac.printOrder(email);
                displayMenu2();
        }

    }

    public static void displayMenu3() {
        Scanner input = new Scanner(System.in);
        System.out.println("1. Display all available movies.");
        System.out.println("2. Search by genre.");
        System.out.println("3. Go back.");
        int choice1 = input.nextInt();
        input.nextLine();

        switch (choice1) {

            case 1:
                System.out.println("Currently available movies at our cinema:");
                jpac.printAllMovies();
                displayMenu3();
                break;
            case 2:
                System.out.println("Enter genre. (Drama/Animated/Comedy...)");
                String genre = input.nextLine().toLowerCase();
                System.out.println("List of " + genre + " movies:");
                jpac.printMoviesByGenre(genre);
                displayMenu3();
                break;
            case 3:
                displayMenu2();
                break;
        }
    }

    public static void displayMenu4() {
        Scanner input = new Scanner(System.in);
        int adults, children;
        double total = 0;
        System.out.println("Enter the name of movie you want to book your ticket for:");
        String title = input.nextLine().toLowerCase();
        String title2 = title.substring(0, 1).toUpperCase() + title.substring(1);
        System.out.println("title: " + title2);
        jpac.printTimes(title2);
        System.out.println("Select suitable time for you:");
        int option = input.nextInt();
        System.out.println("Enter the number of tickets you want to purchase for " + title2);
        int qty = input.nextInt();
        double ticketPrice = jpac.getTicketPrice(title2);
        System.out.println("ticket price; " + ticketPrice);
        if (qty > 1) {
            System.out.println("Number of adults(18+): ");
            adults = input.nextInt();
            System.out.println("Number of children(<18): ");
            children = input.nextInt();
            total = (adults * ticketPrice) + (children * ticketPrice) * CHILDRENDISCOUNT;
            System.out.println("Total amount to pay: " + total);
        }
        jpac.purchaseTickets(title2, qty, option, email);
        //MovieOperations mo = new MovieOperations();
       // mo.addUserBookings(jpac.findUserID(email), jpac.findMovieID(title2), option, qty); //
        // calculate here price and make changes to database

        displayMenu2();
    }

    public static void displayAdminMenu() {
        Scanner input = new Scanner(System.in);

        System.out.println("1. Add movie.");
        System.out.println("2. Remove movie.");
        System.out.println("3. Add times to movie.");
        System.out.println("4. Set new price.");
        System.out.println("5. Go back.");
        int option = input.nextInt();
        input.nextLine();
        switch (option) {
            case 1:
                System.out.println("Enter the title of movie you want to add: ");
                String title = input.nextLine();
                System.out.println("Enter the genre of movie:");
                String genre = input.nextLine();
                System.out.println("Enter number of tickets available for that movie:");
                int ticnum = input.nextInt();
                input.nextLine();
                System.out.println("Enter price of each ticket for that movie:");
                String price = input.nextLine(); // should be double but no time to change everything
                jpac.addMovie(title, genre, price);
                displayAdminMenu();
                break;
            case 2:
                System.out.println("Enter the title of movie you want to remove:");
                String titleToRemove = input.nextLine();
                jpac.removeMovie(titleToRemove);
                displayAdminMenu();
                break;
            case 3:
                System.out.println("Enter the title of movie you want to add new time in cinema:");
                title = input.nextLine();
                System.out.println("Enter a date:");
                String date = input.nextLine();
                System.out.println("Enter the time:");
                String time = input.nextLine();
                System.out.println("Enter number of tickets available for that movie: ");
                int qty = input.nextInt();
                jpac.addTime(title, date, time, qty); // it should be checking if that time already exitst in DB but no TIME
                displayAdminMenu();
                break;

            case 4:

                break;

            case 5:
                displayMenu2();
                break;
        }
    }
    
    public static void displayOrderMenu(){
        
    }

}
