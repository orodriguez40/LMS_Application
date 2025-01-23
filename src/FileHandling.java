// FileHandler Class
// This class handles file operations for adding patrons
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class FileHandling {
    // Method asks the user to upload a file and add patrons from it
    public void addPatronsFromFile(String filePath, List<Patron> patrons) {
        // BufferedReader will try to read the file
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // Lists to track errors and duplicates
            List<String> duplicateIDs = new ArrayList<>();
            List<String> invalidEntries = new ArrayList<>();
            List<String> invalidIDs = new ArrayList<>();
            List<String> outOfRangeAmountOwed = new ArrayList<>();
            // Create a set of existing IDs for quick lookup
            Set<Integer> existingIDs = patrons.stream().map(Patron::getId).collect(Collectors.toSet());
            Map<Integer, Integer> fileIDCounts = new HashMap<>();
            List<Patron> validPatrons = new ArrayList<>();
            boolean hasErrors = false; // Flag to check if any errors occurred

            // Read lines from the file
            String line;
            while ((line = reader.readLine()) != null) {
                // Split the line into details based on the hyphen
                String[] details = line.split("-");
                if (details.length != 4) {
                    invalidEntries.add(line); // Add to invalid entries if format is incorrect
                    hasErrors = true; // Set error flag
                    continue;
                }

                // Validate the ID and other details
                try {
                    int id = Integer.parseInt(details[0].trim());
                    fileIDCounts.put(id, fileIDCounts.getOrDefault(id, 0) + 1); // Count occurrences of each ID

                    // Check if ID is in the valid range
                    if (id < 1 || id > 9999999) {
                        invalidIDs.add(line); // Add to invalid IDs if out of range
                        hasErrors = true; // Set error flag
                        continue;
                    }
                    // Check for duplicates in existing patrons
                    if (existingIDs.contains(id)) {
                        duplicateIDs.add(line); // Add to duplicates if already exists
                        hasErrors = true; // Set error flag
                        continue;
                    }
                    // Store valid patron information
                    String name = details[1].trim();
                    String address = details[2].trim();

                    // Validate the amount owed
                    double amountOwed = Double.parseDouble(details[3].trim());
                    if (amountOwed < 0 || amountOwed > 250) {
                        outOfRangeAmountOwed.add(line); // Add to out of range amounts
                        hasErrors = true; // Set error flag
                        continue;
                    }

                    // Add the valid patron to the list
                    validPatrons.add(new Patron(id, name, address, amountOwed));
                } catch (NumberFormatException e) {
                    invalidEntries.add(line); // Add to invalid entries on format error
                    hasErrors = true; // Set error flag
                }
            }

            // Check for duplicates in the valid patrons list
            for (Map.Entry<Integer, Integer> entry : fileIDCounts.entrySet()) {
                if (entry.getValue() > 1) {
                    int duplicateID = entry.getKey();
                    duplicateIDs.addAll(
                            validPatrons.stream().filter(p -> p.getId() == duplicateID).map(Patron::toString).toList()
                    );
                    validPatrons.removeIf(p -> p.getId() == duplicateID); // Remove duplicates
                }
            }

            // Add all valid patrons to the main list
            patrons.addAll(validPatrons);

            // Display messages for any issues encountered
            if (!duplicateIDs.isEmpty()) {
                System.out.println("\nThe following entries have duplicate IDs and were skipped:");
                duplicateIDs.forEach(System.out::println); // Print duplicates
            }

            if (!invalidIDs.isEmpty()) {
                System.out.println("\nThe following entries have invalid IDs (must be exactly 7 digits) and were skipped:");
                invalidIDs.forEach(System.out::println); // Print invalid IDs
            }

            if (!outOfRangeAmountOwed.isEmpty()) {
                System.out.println("\nThe following entries have invalid amounts owed (must be between $0 and $250) and were skipped:");
                outOfRangeAmountOwed.forEach(System.out::println); // Print out of range amounts
            }

            // Final message based on whether any errors occurred
            if (hasErrors) {
                System.out.println("\nFile processing completed with errors.");
            } else {
                System.out.println("\nFile processing completed with no errors."); // Success message
            }
        } catch (IOException e) {
            System.out.println("Error reading the file. Please ensure the file path is correct."); // Error message for file issues
        }
    }
}
