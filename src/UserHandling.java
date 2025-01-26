// Otoniel Rodriguez-Perez
// CEN-3024C-24204
// 01/26/2025

// UserHandling Class:
// This class handles all user inputs and confirmations.


//Imported Libraries
import java.util.InputMismatchException;
import java.util.Scanner;

public class UserHandling {

    // Method checks for user's choice in the main menu.
    public int usersChoice(Scanner scanner) {
        while (true) {
            try {
                // Get user input and validate it.
                int choice = scanner.nextInt();
                scanner.nextLine(); // Clears the buffer.
                if (choice >= 1 && choice <= 5) {
                    return choice; // Return valid input.
                }
                System.out.println("Please try again. Select a number between 1 and 5."); // Error message for invalid numeric input.
            } catch (InputMismatchException e) {
                System.out.println("Please try again. Input must be a number."); // Error message for non-numeric input.
                scanner.nextLine(); // Clears invalid input.
            }
        }
    }

    // Method to get a patron ID from user input.
    public static int manualIdInput(Scanner scanner) {
        int id; // Local variable to hold the patron ID.

        // Loops until a valid 7-digit ID is entered.
        while (true) {
            System.out.print("ID: ");
            String input = scanner.nextLine().trim();
            if (input.matches("\\d{7}") && Integer.parseInt(input) >= 1000000) { // Check if the input is a 7-digit number and >= 1000000
                id = Integer.parseInt(input);

                // Checks if the ID is unique.
                int finalId = id;
                if (PatronManaging.patrons.stream().noneMatch(patron -> patron.getId() == finalId)) {
                    return finalId; // Returns the valid ID.
                } else {
                    System.out.println("ID is already in use. Please enter a different number.");
                }
            } else {
                System.out.println("Invalid ID. It must be a 7-digit number and must start with a 1. Please try again.");
            }
        }
    }

    // Method is called to get a string input from the user.
    public static String StringInput(Scanner scanner, String prompt) {
        String input; // Local variable to hold the user input.
        // Loops to ensure valid input is received.
        do {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
        } while (input.isEmpty()); // Repeats until a string is entered.
        return input; // Returns the valid input.
    }

    // Method is called to get the amount owed from the user.
    public static double amountInput(Scanner scanner) {
        double amountOwed; // Local variable to hold the amount owed.
        // Loops until a valid amount is entered.
        while (true) {
            System.out.print("Amount Owed: ");
            if (scanner.hasNextDouble()) {
                amountOwed = scanner.nextDouble();
                scanner.nextLine(); // Clears the line
                if (amountOwed >= 0 && amountOwed <= 250) {
                    return amountOwed; // Return the valid amount
                } else {
                    System.out.print("Please try again. Number must be between 0 and 250.\n");
                }
            } else {
                System.out.print("Invalid Input. Please enter a valid number: ");
                scanner.nextLine(); // Clears invalid input
            }
        }
    }
    // Method is called to get a valid patron ID to delete.
    public static int patronIdDelete(Scanner scanner) {
        while (true) {
            System.out.print("Enter patron ID to remove: ");
            String input = scanner.nextLine().trim().toLowerCase();

            // Checks ID to be the specified range.
            try {
                int id = Integer.parseInt(input);
                if (id >= 1000000 && id <= 9999999) {
                    return id; // Returns the valid ID.
                } else {
                    System.out.println("Invalid ID. It must be exactly 7 digits.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid 7-digit number.");
            }
        }
    }

    // Helper method is called to find a patron by ID.
    public static Patron findPatronById(int id) {
        return PatronManaging.patrons.stream().filter(patron -> patron.getId() == id).findFirst().orElse(null); // Return the patron if found.
    }

    // Method is called to get confirmation from the user.
    public static boolean userConfirmation(Scanner scanner, String message) {
        // Loop until we get a valid response from the user
        while (true) {
            // Prompt the user with the provided message.
            System.out.print(message);

            // Read the user's input and normalizes it.
            String choice = scanner.nextLine().trim().toLowerCase();

            // Check if the user confirmed.
            if (choice.equals("y") || choice.equals("yes")) {
                return true; // User confirmed, return true.
            }
            // Check if the user declined.
            else if (choice.equals("n") || choice.equals("no")) {
                return false; // User declined, return false.
            }
            // Checks for invalid input.
            else {
                // Inform the user about the invalid input.
                System.out.println("\nThat's not a valid response. Please enter y for yes or n for no.");
            }
        }
    }
}

