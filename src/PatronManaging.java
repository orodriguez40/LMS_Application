// PatronManager Class
// This class manages all operations related to patrons in the library system
import java.util.*;

public class PatronManaging {
    // Attribute to store all patrons in a list
    private final List<Patron> patrons = new ArrayList<>();

    // Method to add a patron manually
    public void addPatronManual(Scanner scanner) {
        // Loop to allow adding multiple patrons
        do {

            System.out.println("Please enter the following patron information:\n");

            // Get patron details through user input
            int id = manualIdInput(scanner); // Get unique ID for the patron
            String name = StringInput(scanner, "Full Name: "); // Get patron's name
            String address = StringInput(scanner, "Address:\nExample (123 Street Rd. Orlando, FL 12345)\n"); // Get patron's address
            double amountOwed = amountInput(scanner); // Get the amount owed by the patron

            // Add the new patron to the list
            patrons.add(new Patron(id, name, address, amountOwed));
            System.out.println("\nPatron successfully added!\n");

            // Ask if the user wants to add another patron
        } while (userConfirmation(scanner, "Would you like to add another patron? y or n\n"));
    }

    // Method to add patrons by file upload
    public void addPatronFile(Scanner scanner) {
        // Prompt user for the file path
        System.out.print("Enter the file path for the patron list:\n");
        System.out.print("Example (C:\\Users\\<YourUsername>\\Desktop\\<YourFileName>.txt)\n ");
        String filePath = scanner.nextLine().trim();

        // Create FileHandler instance to process the file
        FileHandling fileHandling = new FileHandling();
        fileHandling.addPatronsFromFile(filePath, patrons); // Add patrons from the file
    }

    // Method to remove a patron
    public void removePatron(Scanner scanner) {
        // Loop to allow user to remove multiple patrons
        while (true) {
            int id = patronIdDelete(scanner); // Get the ID of the patron to remove
            Patron patron = findPatronById(id); // Find the patron by ID

            // Check if the patron exists
            if (patron != null) {
                // Ask for confirmation before deletion
                if (userConfirmation(scanner, "Are you sure you want to delete this patron? (y/n): ")) {
                    patrons.remove(patron); // Remove the patron from the list
                    System.out.println("\nPatron removed successfully.");
                } else {
                    System.out.println("Patron deletion canceled."); // Inform user if deletion is canceled
                }
                return; // Exit the method after handling the removal
            } else {
                System.out.println("Patron not found in the system."); // Inform user if patron is not found

                // Ask if the user wants to try again
                if (!userConfirmation(scanner, "Would you like to try again? (y/n): ")) {
                    return; // Exit the method if user chooses not to try again
                }
            }
        }
    }


    // Method to view all patrons
    public void viewAllPatrons() {
        // Check if there are any patrons in the list
        if (patrons.isEmpty()) {
            System.out.println("No patrons found."); // Inform user if no patrons are present
        } else {
            patrons.forEach(System.out::println); // Print all patrons
        }
    }

    // Method to get a patron ID from user input
    private int manualIdInput(Scanner scanner) {
        int id; // Local variable to hold the patron ID

        // Loop until a valid 7-digit ID is entered
        while (true) {
            System.out.print("Enter ID: ");
            String input = scanner.nextLine().trim();
            if (input.matches("\\d{7}")) { // Check if the input is a 7-digit number
                id = Integer.parseInt(input);

                // Check if the ID is unique
                int finalId = id;
                if (patrons.stream().noneMatch(patron -> patron.getId() == finalId)) {
                    return finalId; // Return the valid ID
                } else {
                    System.out.println("ID is already in the system. Please enter a different number.");
                }
            } else {
                System.out.println("Invalid ID. It must be a 7-digit number. Please try again.");
            }
        }
    }

    // Method to get a string input from the user
    private String StringInput(Scanner scanner, String prompt) {
        String input; // Local variable to hold the user input
        // Loop to ensure valid input is received
        do {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
        } while (input.isEmpty()); // Repeat until a non-empty string is entered
        return input; // Return the valid input
    }

    // Method to get the amount owed from the user
    private double amountInput(Scanner scanner) {
        double amountOwed; // Local variable to hold the amount owed
        // Loop until a valid amount is entered
        while (true) {
            System.out.print("Amount Owed (0 - 250): ");
            if (scanner.hasNextDouble()) {
                amountOwed = scanner.nextDouble();
                scanner.nextLine(); // Clears the line
                if (amountOwed >= 0 && amountOwed <= 250) {
                    return amountOwed; // Return the valid amount
                } else {
                    System.out.print("Invalid amount. Number must be between 0 and 250: ");
                }
            } else {
                System.out.print("Invalid input. Please enter a valid number: ");
                scanner.nextLine(); // Clears invalid input
            }
        }
    }

    // Method to get confirmation from the user
    private boolean userConfirmation(Scanner scanner, String message) {
        // Loop until we get a valid response from the user
        while (true) {
            // Prompt the user with the provided message
            System.out.print(message);

            // Read the user's input and normalize it
            String choice = scanner.nextLine().trim().toLowerCase();

            // Check if the user confirmed
            if (choice.equals("y") || choice.equals("yes")) {
                return true; // User confirmed, return true
            }
            // Check if the user declined
            else if (choice.equals("n") || choice.equals("no")) {
                return false; // User declined, return false
            }
            // Handle invalid input
            else {
                // Inform the user about the invalid input
                System.out.println("Oops! That's not a valid response. Please enter 'y' for yes or 'n' for no.");
            }
        }
    }
    // Method to get a valid patron ID to delete
    private int patronIdDelete(Scanner scanner) {
        while (true) {
            System.out.print("Enter patron ID to remove (7 digits, or type 'b' to return): ");
            String input = scanner.nextLine().trim().toLowerCase();

            // Check if the user wants to return to the main menu
            if (input.equals("b")) {
                System.out.println("Returning to the main menu");
                return -1; // Signal to return
            }

            try {
                int id = Integer.parseInt(input);
                if (id >= 1 && id <= 9999999) {
                    return id; // Return the valid ID
                } else {
                    System.out.println("Invalid ID. It must be exactly 7 digits.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid 7-digit number.");
            }
        }
    }

    // Method to find a patron by ID
    private Patron findPatronById(int id) {
        return patrons.stream().filter(patron -> patron.getId() == id).findFirst().orElse(null); // Return the patron if found
    }
}
