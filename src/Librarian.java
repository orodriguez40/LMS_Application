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
        Scanner scanner = new Scanner(System.in);
        int userChoice;

        System.out.println("\nWelcome to the Library Management System (LMS)!\n");

        // Main menu loop
        do {
            displayMenu();
            userChoice = getUserChoice(scanner);

            switch (userChoice) {
                case 1 -> addPatronManual(scanner);
                case 2 -> addPatronFile(scanner);
                case 3 -> removePatron(scanner);
                case 4 -> viewAllPatrons();
                case 5 -> System.out.println("Thank you for using the LMS application. Goodbye!");
                default -> System.out.println("Invalid option. Please enter a number from 1 to 6.");
            }
        } while (userChoice != 6);

        scanner.close();
    }

    // Display the main menu
    private static void displayMenu() {

    }

    // Get user choice within a valid range
    private static int getUserChoice(Scanner scanner) {

    }

    // Method to add a patron manually
    private static void addPatronManual(Scanner scanner) {

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


