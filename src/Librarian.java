// Librarian Class (Main Application)
// This is where the application will run.
// The user will open the JAR file through the CLI.
// They will have the options to add a patron manually or by file,
// remove a patron based on their ID,
// view all patrons, or close the application.

// Imported Libraries
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Librarian {

    // Attribute: List to store all patrons
    private static final List<Patron> patrons = new ArrayList<>();

    // Main Method
    public static void main(String[] args) {
        //Scanner user to accept all user input.
        Scanner scanner = new Scanner(System.in);
        int userChoice;

        System.out.println("\nWelcome to the Library Management System (LMS)!\n");

        // Main menu will iterate until the users chooses to close the application.
        do {
            viewMenu();
            //Calls method to verify user input.
            userChoice = getUserChoice(scanner);

            switch (userChoice) {
                case 1:
                    addPatronManual(scanner);
                    break;
                case 2:
                    addPatronFile(scanner);
                    break;
                case 3:
                    removePatron(scanner);
                    break;
                case 4:
                    viewAllPatrons();
                    break;
                case 5:
                    System.out.println("Thank you for using the LMS application. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please enter a number from 1 to 6.");
                    break;
            }
        } while (userChoice != 6);

        scanner.close();
    }

    //User will the following menu when entering application.
    private static void viewMenu() {
        System.out.println("Main Menu");
        System.out.println("Please select from the following options:\n");
        System.out.println("Enter 1 to add a new patron manually");
        System.out.println("Enter 2 to add patrons by upload");
        System.out.println("Enter 3 to remove a patron");
        System.out.println("Enter 4 to View all patrons");
        System.out.println("Enter 5 the application");
    }

    //Method checks for user's choice in the main menu.
    private static int getUserChoice(Scanner scanner) {
        while (true) {
            try {
                //Attribute will be sent to viewMenu() method if correct input is entered.
                int choice = scanner.nextInt();
                scanner.nextLine();
                if (choice >= 1 && choice <= 5)
                    return choice;

                //If incorrect values are entered, it will be displayed for the user to try again.
                System.out.println("Invalid input. Please select a number between 1 and 5.");
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
    }

    //Method to add a patron manually
    private static void addPatronManual(Scanner scanner) {
        do {
            System.out.println("Please enter the following patron information:\n");

            //Attributes will store called method values by first checking if input is correct.
            int id = getUniquePatronId(scanner);
            String name = getNonEmptyInput(scanner, "Name: ");
            String address = getNonEmptyInput(scanner, "Address: ");
            double amountOwed = getValidatedAmount(scanner);

            //When correct values are entered, the patron will be added.
            patrons.add(new Patron(id, name, address, amountOwed));
            System.out.println("Patron successfully added!");

            //method will loop until user selects to return to the main menu.
            //getConfirmation() method will confirm users choice.
        } while (getConfirmation(scanner, "Would you like to add another patron? Enter y or n"));
    }

    // Get a unique patron ID
    private static int getUniquePatronId(Scanner scanner) {

    }

    // Get non-empty input from user
    private static String getNonEmptyInput(Scanner scanner, String prompt) {

    }

    // Validate amount owed input
    private static double getValidatedAmount(Scanner scanner) {

    }

    // Get confirmation from user
    private static boolean getConfirmation(Scanner scanner, String message) {

    }

    // Method to add patrons from a file
    private static void addPatronFile(Scanner scanner) {

    }

    // Method to delete a patron
    private static void removePatron(Scanner scanner) {

    }

    // Get a valid patron ID
    private static int getValidPatronId(Scanner scanner) {

    }

    // Method to view all patrons
    private static void viewAllPatrons() {

    }


