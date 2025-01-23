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

    //Attribute to list to store all patrons.
    private static final List<Patron> patrons = new ArrayList<>();

    // Main Method
    public static void main(String[] args) {
        //Scanner is used to accept all user input.
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
    public static void viewMenu() {
        System.out.println("Main Menu");
        System.out.println("Please select from the following options:\n");
        System.out.println("Enter 1 to add a new patron manually");
        System.out.println("Enter 2 to add patrons by file upload");
        System.out.println("Enter 3 to remove a patron");
        System.out.println("Enter 4 to View all patrons");
        System.out.println("Enter 5 to exit the application");
    }

    //Method checks for user's choice in the main menu.
    public static int getUserChoice(Scanner scanner) {
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
    public static void addPatronManual(Scanner scanner) {
        do {
            System.out.println("Please enter the following patron information:\n");

            //Local attributes will store called method values by
            //checking if input is correct first.
            int id = getPatronIdManual(scanner);
            String name = getStringInput(scanner, "Name: ");
            String address = getStringInput(scanner, "Address: ");
            double amountOwed = getAmount(scanner);

            //When correct values are entered, the patron will be added.
            patrons.add(new Patron(id, name, address, amountOwed));
            System.out.println("Patron successfully added!");

            //method will loop until user selects to return to the main menu.
            //getConfirmation() method will confirm users choice.
        } while (getConfirmation(scanner, "Would you like to add another patron? Enter y or n"));
    }

    //Method will ask user to input ID to be added manually.
    public static int getPatronIdManual(Scanner scanner) {
        //Local attribute used to check specified conditions are met.
        int id;

        //Loop will iterate until user inputs 7 digits.
        while (true) {
            System.out.print("Enter ID: ");
            String input = scanner.nextLine().trim();
            if (input.matches("\\d{7}")) {
                id = Integer.parseInt(input);

                //Attribute and condition used to check if id not duplicated in the application.
                int finalId = id;
                if (patrons.stream().noneMatch(patron -> patron.getId() == finalId)) {
                    return finalId;
                } else {
                    System.out.println("ID is already in the system. Please enter a different number.");
                }
            } else {
                System.out.println("Invalid ID. It must be a 7-digit number. Please try again.");
            }
        }
    }

    //Method will ask for the user to input a String.
    public static String getStringInput(Scanner scanner, String prompt) {
        //Local attribute to check store a String value.
        String input;
        //Loop use to iterate and gather a String.
        do {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
        } while (input.isEmpty());
        return input;
    }

    //Method will ask user for the amount owed.
    private static double getAmount(Scanner scanner) {
        //Local attribute to check store a double value.
        double amountOwed;
        //Loop will iterate until correct value is entered.
        while (true) {
            //Checks if amount is correct and returns it.
            System.out.print("Amount Owed (0 - 250): ");
            if (scanner.hasNextDouble()) {
                amountOwed = scanner.nextDouble();
                scanner.nextLine(); //Clears the line.
                if (amountOwed >= 0 && amountOwed <= 250) {
                    return amountOwed;
                } else {
                    System.out.print("Invalid amount. Number must be between 0 and 250: ");
                }
            } else {
                System.out.print("Invalid input. Please enter a valid number: ");
                scanner.nextLine(); //Clears invalid input.
            }
        }
    }


    //Method user to get yes or no confirmation.
    private static boolean getConfirmation(Scanner scanner, String message) {
        System.out.print(message);
        String choice = scanner.nextLine().trim().toLowerCase();
        return choice.equals("y") || choice.equals("yes");
    }

    //Method asks the user to upload file
    private static void addPatronFile(Scanner scanner) {
        //User will be asked to enter directory for the .txt file upload.
        System.out.print("Enter the file path for the patron list: ");
        String filePath = scanner.nextLine().trim();

        //BufferedReader will try to read the file.
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            //Attributes used to check for duplicate ids and file format.
            List<String> duplicateIDs = new ArrayList<>();
            List<String> invalidEntries = new ArrayList<>();
            List<String> invalidIDs = new ArrayList<>();
            List<String> outOfRangeAmountOwed = new ArrayList<>();
            Set<Integer> existingIDs = patrons.stream().map(patron -> Integer.valueOf(patron.getId())).collect(Collectors.toSet());
            Map<Integer, Integer> fileIDCounts = new HashMap<>();
            List<Patron> validPatrons = new ArrayList<>();
            boolean hasErrors = false;

            //Attribute to parse file.
            String line;
            //Loop to iterate to parse and read all values within file.
            while ((line = reader.readLine()) != null) {
                //While reading the file, it will parse patron attribute values by hyphen.
                String[] details = line.split("-");
                if (details.length != 4) {
                    invalidEntries.add(line);
                    hasErrors = true;
                    continue;
                }

                //Checks if IDs are within specified range.
                try {
                    //Local attributes used to check values.
                    int id = Integer.parseInt(details[0].trim());
                    fileIDCounts.put(id, fileIDCounts.getOrDefault(id, 0) + 1);

                    //Condition statement to check if id within file is in the specified range.
                    if (id < 1 || id > 9999999) {
                        invalidIDs.add(line);
                        hasErrors = true;
                        continue;
                    }
                    //Condition statement to check there are duplicate values within file.
                    if (existingIDs.contains(id)) {
                        duplicateIDs.add(line);
                        hasErrors = true;
                        continue;
                    }
                    //Local attributes to store parsed values.
                    String name = details[1].trim();
                    String address = details[2].trim();

                    //Checks if amount owed is out of specified range.
                    double amountOwed = Double.parseDouble(details[3].trim());
                    if (amountOwed < 0 || amountOwed > 250) {
                        outOfRangeAmountOwed.add(line);
                        hasErrors = true;
                        continue;
                    }

                    //Patron is added once all information is validated.
                    validPatrons.add(new Patron(id, name, address, amountOwed));
                } catch (NumberFormatException e) {
                    invalidEntries.add(line);
                    hasErrors = true;
                }
            }

            //Loop checks if id is already in the application.
            for (Map.Entry<Integer, Integer> entry : fileIDCounts.entrySet()) {
                if (entry.getValue() > 1) {
                    int duplicateID = entry.getKey();
                    duplicateIDs.addAll(
                            validPatrons.stream().filter(p -> p.getId() == duplicateID).map(Patron::toString).collect(Collectors.toList())
                    );
                    validPatrons.removeIf(p -> p.getId() == duplicateID);
                }
            }

            //Once it finished iterating through the file, it will add all patrons who satisfy all conditions.
            patrons.addAll(validPatrons);

            //Message displayed if there are IDs within the file or in the application.
            if (!duplicateIDs.isEmpty()) {
                System.out.println("\nThe following entries have duplicate IDs and were skipped:");
                duplicateIDs.forEach(System.out::println);
            }

            //Message displayed if any ID is incorrectly formatted.
            if (!invalidIDs.isEmpty()) {
                System.out.println("\nThe following entries have invalid IDs (must be exactly 7 digits) and were skipped:");
                invalidIDs.forEach(System.out::println);
            }

            //Message is displayed if amount owed is invalid.
            if (!outOfRangeAmountOwed.isEmpty()) {
                System.out.println("\nThe following entries have invalid amounts owed (must be between $0 and $250) and were skipped:");
                outOfRangeAmountOwed.forEach(System.out::println);
            }

            //Message will display if any of the conditions mentioned above equals to true.
            if (hasErrors) {
                System.out.println("\nFile processing completed with errors.");
            } else {
                // Message will display if file uploaded successfully with no errors.
                System.out.println("\nFile processing completed with no errors.");
            }
        } catch (IOException e) {
            System.out.println("Error reading the file. Please ensure the file path is correct.");
        }
    }


    //Method is to delete a patron
    private static void removePatron(Scanner scanner) {
        while (true) {
            int id = getPatronIdDelete(scanner);
            Patron patron = findPatronById(id);

            if (patron != null) {
                patrons.remove(patron);
                System.out.println("Patron removed successfully.");
                return;
            } else {
                System.out.println("Patron not found in the system.");
                if (!getConfirmation(scanner, "Would you like to try again? (y/n): ")) {
                    return;
                }
            }
        }
    }

    //Method to get a valid patron Id to delete.
    private static int getPatronIdDelete(Scanner scanner) {
        while (true) {
            System.out.print("Enter patron ID to remove (7 digits, or type 'b' to return): ");
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("b")) {
                System.out.println("Returning to the main menu");
                return -1; // Signal to return
            }

            try {
                int id = Integer.parseInt(input);
                if (id >= 1 && id <= 9999999) {
                    return id;
                } else {
                    System.out.println("Invalid ID. It must be exactly 7 digits.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid 7-digit number.");
            }
        }
    }


    //Method to find a patron by ID
    private static Patron findPatronById(int id) {
        return patrons.stream().filter(patron -> patron.getId() == id).findFirst().orElse(null);
    }

    //Method to view all patrons
    private static void viewAllPatrons() {
        if (patrons.isEmpty()) {
            System.out.println("The application does not have any patrons.");
        } else {
            patrons.forEach(System.out::println);
        }
    }
}