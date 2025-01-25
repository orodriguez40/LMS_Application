// FileHandler Class:
// This class handles file operations for adding patrons.

//Imported Libraries
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class FileHandling {
    // Method asks the user to upload a file and add patrons from it.
    public void addPatronsByFile(String filePath, List<Patron> patrons) {
        // BufferedReader will try to read the file.
        try (BufferedReader readFile = new BufferedReader(new FileReader(filePath))) {
            // ArrayLists to track errors and duplicates.
            List<String> duplicateIDs = new ArrayList<>();
            List<String> invalidEntries = new ArrayList<>();
            List<String> invalidIDs = new ArrayList<>();
            List<String> outOfRangeAmountOwed = new ArrayList<>();

            // Creates a set of existing IDs for quick lookup.
            Set<Integer> existingIDs = patrons.stream().map(Patron::getId).collect(Collectors.toSet());
            Map<Integer, Integer> fileIDCounts = new HashMap<>();
            List<Patron> validPatrons = new ArrayList<>();
            boolean hasErrors = false; // Checks true if any errors occurred.

            // Reads the rows from the text file.
            String row;
            while ((row = readFile.readLine()) != null) {
                // Parse the row into details based on the hyphen.
                String[] details = row.split("-");
                if (details.length != 4) {
                    invalidEntries.add(row); // Adds to invalid entries if format is incorrect.
                    hasErrors = true; // Sets error and its flagged.
                    continue;
                }

                // Validates the ID and other details.
                try {
                    int id = Integer.parseInt(details[0].trim());
                    fileIDCounts.put(id, fileIDCounts.getOrDefault(id, 0) + 1); // Checks if there are multiple ID within the file.

                    // Checks is each ID is in the valid range.
                    if (id < 1 || id > 9999999) {
                        invalidIDs.add(row); // Add to invalid IDs if out of range.
                        hasErrors = true; // Sets error flag.
                        continue;
                    }
                    // Checks for duplicates in existing patrons.
                    if (existingIDs.contains(id)) {
                        duplicateIDs.add(row); // Add to duplicate section if already exists.
                        hasErrors = true; // Sets error flag.
                        continue;
                    }
                    // Stores valid patron information.
                    String name = details[1].trim();
                    String address = details[2].trim();

                    // Validates the amount owed.
                    double amountOwed = Double.parseDouble(details[3].trim());
                    if (amountOwed < 0 || amountOwed > 250) {
                        outOfRangeAmountOwed.add(row); // Add to out of range amounts.
                        hasErrors = true; // Sets error flag.
                        continue;
                    }

                    // Add the valid patron to a temporary List.
                    validPatrons.add(new Patron(id, name, address, amountOwed));
                } catch (NumberFormatException e) {
                    invalidEntries.add(row); // Add to invalids entries on format error.
                    hasErrors = true; // Sets error flag.
                }
            }

            // Checks for duplicates between the file and the patrons Arraylist.
            for (Map.Entry<Integer, Integer> entry : fileIDCounts.entrySet()) {
                if (entry.getValue() > 1) {
                    int duplicateID = entry.getKey();
                    duplicateIDs.addAll(
                            validPatrons.stream().filter(p -> p.getId() == duplicateID).map(Patron::toString).toList()
                    );
                    validPatrons.removeIf(p -> p.getId() == duplicateID); // Remove duplicates.
                }
            }

            // Adds all valid patrons to the main patrons Arraylist.
            patrons.addAll(validPatrons);

            // Displays messages for any issues encountered.
            if (!duplicateIDs.isEmpty()) {
                System.out.println("\nThe following entries have duplicate IDs and were skipped:");
                duplicateIDs.forEach(System.out::println); // Print duplicates.
            }

            if (!invalidIDs.isEmpty()) {
                System.out.println("\nThe following entries have invalid IDs (must be exactly 7 digits) and were skipped:");
                invalidIDs.forEach(System.out::println); // Print invalid IDs.
            }

            if (!outOfRangeAmountOwed.isEmpty()) {
                System.out.println("\nThe following entries have invalid amounts owed (must be between $0 and $250) and were skipped:");
                outOfRangeAmountOwed.forEach(System.out::println); // Print out of range amounts.
            }

            // Final message is displayed whether any errors occurred or not.
            if (hasErrors) {
                System.out.println("\nFile processing completed with errors.");
            } else {
                System.out.println("\nFile processing completed with no errors.");
            }
        } catch (IOException e) {
            System.out.println("Error reading the file. Please ensure the file path is correct.");
        }
    }
}
