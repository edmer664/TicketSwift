import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * TicketSwift
 * A simple console application to automate the process of ticketing
 * in bus companies.
 *
 * @author John Edmerson Pizarra
 * @version 1.0
 */

public class Main {

    private static final String LOG_FILE = "log.txt";

    /**
     * Displays the main menu of the application.
     *
     * @param destinations the list of destinations
     */
    private static void displayMainMenu(ArrayList<Destination> destinations) {
        System.out.println("""
                                
                  ______ _        __          __     _____           _  ____ __\s
                 /_  __/(_)_____ / /__ ___   / /_   / ___/_      __ (_)/ __// /_
                  / /  / // ___// //_// _ \\ / __/   \\__ \\| | /| / // // /_ / __/
                 / /  / // /__ / ,<  /  __// /_    ___/ /| |/ |/ // // __// /_ \s
                /_/  /_/ \\___//_/|_| \\___/ \\__/   /____/ |__/|__//_//_/   \\__/ \s
                                                                               \s
                                
                """);
        System.out.println();
        System.out.println("List of Destinations:");

        // Iterates through the destinations and displays them.
        for (int i = 0; i < destinations.size(); i++) {
            System.out.println((i + 1) + ". " + destinations.get(i).name());
        }

    }


    /**
     * Displays the ticket data.
     *
     * @param ticketID    the ticket ID
     * @param destination the destination
     * @param cash        the cash of the customer
     * @param change      the change of the customer
     */
    private static void displayTicketData(String ticketID, Destination destination, int cash, int change) {
        System.out.println("____________________________________________________________");
        System.out.println("Ticket ID: " + ticketID);
        System.out.println("Destination: " + destination.name());
        System.out.println("Price: " + destination.fare());
        System.out.println("Cash: " + cash);
        System.out.println("Change: " + change);
        System.out.println("____________________________________________________________");
    }


    /**
     * Initializes the destinations.
     *
     * @param initialDestinations the array of initial destinations
     */
    private static void initializeDestinations(ArrayList<Destination> initialDestinations) {
        initialDestinations.add(new Destination("Mariveles", 86));
        initialDestinations.add(new Destination("Orani", 75));
        initialDestinations.add(new Destination("Bagac", 65));
        initialDestinations.add(new Destination("Cubao", 400));
        initialDestinations.add(new Destination("Pampanga", 200));
    }


    /**
     * adds a transaction to the log file.
     *
     * @param log the string to be added to the log file.
     */
    private static void addToLogFile(String log) {
        try {
            FileWriter fileWriter = new FileWriter(LOG_FILE, true);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println(log);
            printWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }


    /**
     * The main method of the application.
     * main() is the entry point of the application.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        // a boolean variable to determine if the user wants to exit the application.
        boolean isRunning = true;
        int userCash;

        Scanner scanner = new Scanner(System.in);

        // initialize the list of destinations
        ArrayList<Destination> destinations = new ArrayList<>();
        initializeDestinations(destinations);

        // Main loop of the application.
        while (isRunning) {
            displayMainMenu(destinations);

            // ask the user for input
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            // check if the user entered a valid choice
            if (!(choice > 0 && choice <= destinations.size())){
                System.out.println("Error: Destination is invalid.");
                isRunning = continuationPrompt(isRunning, scanner);
                continue;
            }

            int transactionFare = destinations.get(choice - 1).fare();
            System.out.print("\nYour destination is " + destinations.get(choice - 1).name() + " with a fare of " + transactionFare + " pesos." + "\nPlease enter your cash: ");
            userCash = scanner.nextInt();

            // calculate the change
            int change = userCash - transactionFare;
            System.out.println("Your change is " + change + " pesos.");

            // check if the user has enough cash
            if (change < 0) {
                System.out.println("Erorr: Insufficient balance.");
            } else {

                // generate Unique Ticket ID
                String ticketID = "TS" + (int) (Math.random() * 1000000);

                // generate the log
                String log = "[" + ticketID + "]-" + destinations.get(choice - 1).name() + "{fare}PHP" + transactionFare + "{cash}PHP" + userCash + "{change}PHP" + change;
                addToLogFile(log);

                // display the ticket data
                displayTicketData(ticketID, destinations.get(choice - 1), userCash, change);

            }
            isRunning = continuationPrompt(isRunning, scanner);


        }

    }

    private static boolean continuationPrompt(boolean isRunning, Scanner scanner) {
        // determine if the user will still continue
        System.out.println("Press “r” to start a new transaction or “e” to exit");
        String userChoice = scanner.next();
        if (userChoice.equals("e")) {
            isRunning = false;
        }
        return isRunning;
    }
}

/**
 * Destination
 * A class that represents a destination.
 *
 * @param name
 * @param fare
 */
record Destination(String name, int fare) {
}