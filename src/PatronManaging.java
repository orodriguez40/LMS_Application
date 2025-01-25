// Otoniel Rodriguez-Perez
// CEN-3024C-24204
// 01/26/2025

// PatronManaging Class:
// This class manages all functions related to patrons.

//Imported Library
import java.util.*;

public class PatronManaging {
    // Attribute to store all patrons in an Arraylist.
    private final List<Patron> patrons = new ArrayList<>();

    // Method is called to add a patron manually.
    public void addPatronManual(Scanner scanner) {
        // Loop to allow adding multiple patrons.
        do {

            System.out.println("Please enter the following patron information:\n");

            // Get patron information through the user's input.
            int id = manualIdInput(scanner); // Get a unique ID for the patron.
            String name = StringInput(scanner, "Full Name: "); // Get patron's full name.
            String address = StringInput(scanner, "Complete Address (Example - 123 Street Rd. Orlando, FL 12345): "); // Get patron's address.
            double amountOwed = amountInput(scanner); // Get the amount owed by the patron.

            // Add the new patron to the patrons Arraylist.
            patrons.add(new Patron(id, name, address, amountOwed));
            System.out.println("\nPatron successfully added!\n");

            // Ask if the user wants to add another patron
        } while (userConfirmation(scanner, "Would you like to add another patron? y or n\n"));
    }

    // Method is called to add patrons by file upload.
    public void addPatronFile(Scanner scanner) {
        // Prompt user for the file location.
        System.out.print("Enter the file path for the patron list:\n");
        System.out.print("Example (C:\\Users\\<YourUsername>\\Desktop\\<YourFileName>.txt)\n ");
        String filePath = scanner.nextLine().trim();

        // Create FileHandling class instance to process the file.
        FileHandling fileUpload = new FileHandling();
        fileUpload.addPatronsByFile(filePath, patrons); // Add patrons from the file
    }

    // Method is called to remove a patron.
    public void removePatron(Scanner scanner) {
        // Loop to allow user to remove multiple patrons.
        while (true) {
            int id = patronIdDelete(scanner); // Calls helper method the ID of the patron to remove.
            Patron patron = findPatronById(id); // Calls helper method to find the patron by ID.

            // Checks if the patron exists.
            if (patron != null) {
                // Ask for confirmation before deletion.
                if (userConfirmation(scanner, "Are you sure you want to delete this patron? y or n: ")) {
                    patrons.remove(patron); // Remove the patron from the Arraylist.
                    System.out.println("\nPatron removed successfully.");
                } else {
                    System.out.println("\nPatron deletion canceled."); // Inform user does not confirm deletion.
                }
                return; // Exit the method after handling the deletion.
            } else {
                System.out.println("Patron not found in the system."); // Inform user ID is not in the system.

                // Ask if the user wants to try again.
                if (!userConfirmation(scanner, "Would you like to try again? y or n: ")) {
                    return; // Exit the method if user chooses not to try again
                }
            }
        }
    }

    // Method is called to view all patrons.
    public void viewAllPatrons() {
        // Check if there are any patrons in the ArrayList.
        if (patrons.isEmpty()) {
            System.out.println("No patrons found."); // Inform user if no patrons are present.
        } else {
            //Sorts by ID number.
            patrons.sort(Comparator.comparing(Patron::getId));
            // Iterate through each patron and format the output.
            for (Patron patron : patrons) {
                System.out.println("\nID: " + patron.getId() +
                        "\nName: " + patron.getName() +
                        "\nAddress: " + patron.getAddress() +
                        "\nAmount Owed: " + patron.getAmountOwed());
            }
        }
    }

    // Method to get a patron ID from user input.
    // Method to get a patron ID from user input.
    private int manualIdInput(Scanner scanner) {
        int id; // Local variable to hold the patron ID.

        // Loops until a valid 7-digit ID is entered.
        while (true) {
            System.out.print("Enter ID: ");
            String input = scanner.nextLine().trim();
            if (input.matches("\\d{7}") && Integer.parseInt(input) >= 1000000) { // Check if the input is a 7-digit number and >= 1000000
                id = Integer.parseInt(input);

                // Checks if the ID is unique.
                int finalId = id;
                if (patrons.stream().noneMatch(patron -> patron.getId() == finalId)) {
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
    private String StringInput(Scanner scanner, String prompt) {
        String input; // Local variable to hold the user input.
        // Loops to ensure valid input is received.
        do {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
        } while (input.isEmpty()); // Repeats until a string is entered.
        return input; // Returns the valid input.
    }

    // Method is called to get the amount owed from the user.
    private double amountInput(Scanner scanner) {
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

    // Method is called to get confirmation from the user.
    private boolean userConfirmation(Scanner scanner, String message) {
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
                System.out.println("That's not a valid response. Please enter y for yes or n for no.");
            }
        }
    }
    // Method is called to get a valid patron ID to delete.
    private int patronIdDelete(Scanner scanner) {
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

    // Method is called to find a patron by ID.
    private Patron findPatronById(int id) {
        return patrons.stream().filter(patron -> patron.getId() == id).findFirst().orElse(null); // Return the patron if found.
    }
}
