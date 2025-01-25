// Otoniel Rodriguez-Perez
// CEN-3024C-24204
// 01/26/2024

// Librarian Class (Main Application):
// This is where the application will run.
// The user will open the JAR file through the CLI.
// They will have options to add a patron manually or by file,
// remove a patron based on their ID, view all patrons, or close the application.

// Imported Library
import java.util.Scanner;

class Librarian {

    // Attributes are instances of the PatronManaging and UserHandling classes
    private static final PatronManaging patronManaging = new PatronManaging();
    private static final UserHandling userHandling = new UserHandling();

    // Main Method
    public static void main(String[] args) {
        // Scanner is used to accept all user input.
        Scanner scanner = new Scanner(System.in);
        int userChoice;

        // Welcome message for the user.
        System.out.println("\nWelcome to the Library Management System (LMS)!");

        // Main menu will iterate until the user chooses to close the application.
        do {
            // Display the main menu options
            viewMenu();
            // Calls method to verify user input.
            userChoice = userHandling.usersChoice(scanner);

            // Switch statement to handle user choices.
            switch (userChoice) {
                case 1:
                    patronManaging.addPatronManual(scanner); // Adds a patron manually
                    break;
                case 2:
                    patronManaging.addPatronFile(scanner); // Add patrons by file upload
                    break;
                case 3:
                    patronManaging.removePatron(scanner); // Remove a patron
                    break;
                case 4:
                    patronManaging.viewAllPatrons(); // View all patrons
                    break;
                case 5:
                    System.out.println("Thank you for using the LMS application. Goodbye!"); // Message when user chooses to close applicaiton.
                    break;
                default:
                    System.out.println("Invalid option. Please enter a number from 1 to 5."); // Checks for invalid user input.
                    break;
            }
        } while (userChoice != 5); // Continue looping until the user chooses to exit

        scanner.close(); // Close the scanner instance.
    }

    // Method is called display the main menu options
    public static void viewMenu() {
        System.out.println("\nMain Menu");
        System.out.println("Please select from the following options:\n");
        System.out.println("Enter 1 to add a new patron manually");
        System.out.println("Enter 2 to add patrons by file upload");
        System.out.println("Enter 3 to remove a patron");
        System.out.println("Enter 4 to view all patrons");
        System.out.println("Enter 5 to exit the application");
    }

}