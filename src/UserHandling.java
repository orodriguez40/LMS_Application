// Otoniel Rodriguez-Perez
// CEN-3024C-24204
// 01/26/2024

// UserHandling Class:
// This class handles user input for menu selection.

//Imported Libraries
import java.util.InputMismatchException;
import java.util.Scanner;

public class UserHandling {
    // Method checks for user's choice in the main menu
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
                scanner.nextLine(); // Clear invalid input
            }
        }
    }


}
