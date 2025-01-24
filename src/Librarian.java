// Librarian Class (Main Application)
// This is where the application will run.
// The user will open the JAR file through the CLI.
// They will have options to add a patron manually or by file,
// remove a patron based on their ID, view all patrons, or close the application.

// Imported Libraries

// Create instances of PatronManager and UserInputHandler
private static final PatronManaging patronManaging = new PatronManaging();
private static final UserHandling userHandling = new UserHandling();

// Main Method
public static void main(String[] args) {
    // Scanner is used to accept all user input.
    Scanner scanner = new Scanner(System.in);
    int userChoice;

    // Welcome message for the user
    System.out.println("\nWelcome to the Library Management System (LMS)!\n");

    // Main menu will iterate until the user chooses to close the application.
    do {
        viewMenu(); // Display the main menu options
        // Calls method to verify user input.
        userChoice = userHandling.usersChoice(scanner);

        // Switch statement to handle user choices
        switch (userChoice) {
            case 1:
                patronManaging.addPatronManual(scanner); // Option to add a patron manually
                break;
            case 2:
                patronManaging.addPatronFile(scanner); // Option to add patrons by file upload
                break;
            case 3:
                patronManaging.removePatron(scanner); // Option to remove a patron
                break;
            case 4:
                patronManaging.viewAllPatrons(); // Option to view all patrons
                break;
            case 5:
                System.out.println("Thank you for using the LMS application. Goodbye!"); // Exit message
                break;
            default:
                System.out.println("Invalid option. Please enter a number from 1 to 5."); // Error message for invalid input
                break;
        }
    } while (userChoice != 5); // Continue looping until the user chooses to exit

    scanner.close(); // Close the scanner resource
}

// Method to display the main menu options
public static void viewMenu() {
    System.out.println("\nMain Menu");
    System.out.println("Please select from the following options:\n");
    System.out.println("Enter 1 to add a new patron manually");
    System.out.println("Enter 2 to add patrons by file upload");
    System.out.println("Enter 3 to remove a patron");
    System.out.println("Enter 4 to view all patrons");
    System.out.println("Enter 5 to exit the application");
}
