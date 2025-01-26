// Otoniel Rodriguez-Perez
// CEN-3024C-24204
// 01/26/2025

// PatronManaging Class:
// This class manages all functions related to patrons.

//Imported Library
import java.util.*;

public class PatronManaging {

    // Attribute to store all patrons in an Arraylist.
    static final List<Patron> patrons = new ArrayList<>();

    // Method is called to add a patron manually.
    public void addPatronManual(Scanner scanner) {
        // Loop to allow adding multiple patrons.
        do {

            System.out.println("Please enter the following patron information:\n");

            // Get patron information through the user's input.
            int id = UserHandling.manualIdInput(scanner); // Get a unique ID for the patron.
            String name = UserHandling.StringInput(scanner, "Full Name: "); // Get patron's full name.
            String address = UserHandling.StringInput(scanner, "Complete Address (Example - 123 Street Rd. Orlando, FL 12345): "); // Get patron's address.
            double amountOwed = UserHandling.amountInput(scanner); // Get the amount owed by the patron.

            //Patron information will be displayed before confirming.
            System.out.print("\nPlease verify information is correct:\n");
            System.out.println("\nID: " + id +
                    "\nName: " + name +
                    "\nAddress: " + address +
                    "\nAmount Owed: " + amountOwed);

            //Asks user to confirm if they want to add patron.
            if (UserHandling.userConfirmation(scanner, "\nAre you sure you want to add this patron? y or n: ")) {
                // Add the new patron to the patrons Arraylist.
                patrons.add(new Patron(id, name, address, amountOwed));
                System.out.println("\nPatron successfully added!");
            } else {
                System.out.println("\nPatron not added.");
            }

            // Ask if the user wants to add another patron
        } while (UserHandling.userConfirmation(scanner, "\nWould you like to continue to enter patron information? y or n:\n"));

        System.out.println("\nReturning to the main menu.");
    }

    // Method is called to add patrons by file upload.
    public void addPatronFile(Scanner scanner) {
        // Prompt user for the file location.
        System.out.print("\nEnter the file path for the patron list:\n");
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
            int id = UserHandling.patronIdDelete(scanner); // Calls helper method the ID of the patron to remove.
            Patron patron = UserHandling.findPatronById(id); // Calls helper method to find the patron by ID.

            // Checks if the patron exists.
            if (patron != null) {

                //Patron information will be displayed before confirming.
                System.out.print("\nPatron Found:\n");
                System.out.println("\nID: " + patron.getId() +
                        "\nName: " + patron.getName() +
                        "\nAddress: " + patron.getAddress() +
                        "\nAmount Owed: " + patron.getAmountOwed());

                // Ask for confirmation before deletion.
                if (UserHandling.userConfirmation(scanner, "\nAre you sure you want to delete this patron? y or n: ")) {
                    patrons.remove(patron); // Remove the patron from the Arraylist.
                    System.out.println("\nPatron removed successfully. Returning to the main menu.");
                } else {
                    System.out.println("\nPatron deletion canceled. Returning to the main menu."); // Inform user does not confirm deletion.
                }
                return; // Exit the method after handling the deletion.
            } else {
                System.out.println("Patron not found in the system.\n"); // Inform user ID is not in the system.

                // Ask if the user wants to try again.
                if (!UserHandling.userConfirmation(scanner, "Would you like to try again? y or n\n(Entering n will take you back to the main menu): ")) {
                    return; // Exit the method if user chooses not to try.

                }
            }
        }
    }

    // Method is called to view all patrons.
    public void viewAllPatrons() {
        // Check if there are any patrons in the ArrayList.
        if (patrons.isEmpty()) {
            System.out.println("\nNo patrons found.\n"); // Inform user if no patrons are present.
        } else {
            //Sorts by ID number.
            patrons.sort(Comparator.comparing(Patron::getId));
            System.out.println("\nList of Patrons:");
            // Iterate through each patron and format the output.
            for (Patron patron : patrons) {
                System.out.println("\nID: " + patron.getId() +
                        "\nName: " + patron.getName() +
                        "\nAddress: " + patron.getAddress() +
                        "\nAmount Owed: " + patron.getAmountOwed() + "\n");
            }
        }
        System.out.println("Returning to the main menu.");
    }

}