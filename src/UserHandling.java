// UserInputHandler Class
// This class handles user input for menu selection
import java.util.InputMismatchException;
import java.util.Scanner;

public class UserHandling {
    // Method checks for user's choice in the main menu
    public int usersChoice(Scanner scanner) {
        while (true) {
            try {
                // Get user input and validate it
                int choice = scanner.nextInt();
                scanner.nextLine(); // Clear the buffer
                if (choice >= 1 && choice <= 5) {
                    return choice; // Return valid choice
                }
                System.out.println("Invalid input. Please select a number between 1 and 5."); // Error message for invalid input
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number."); // Error message for non-numeric input
                scanner.nextLine(); // Clear the invalid input
            }
        }
    }


}
