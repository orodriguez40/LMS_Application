// Otoniel Rodriguez-Perez
// CEN-3024C-24204
// 01/26/2025

// FileHandler Class:
// This class handles file operations and conditions for adding patrons.

// Imported Libraries
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class FileHandling {
    //Method is called to read and process file.
    public void addPatronsByFile(String filePath, List<Patron> patrons) {
        //BufferedReader will attempt to read the file.
        try (BufferedReader readFile = new BufferedReader(new FileReader(filePath))) {
            //Lists are specifically called to check that contents within file are correct.
            List<String> invalidEntries = new ArrayList<>();
            List<String> invalidIDs = new ArrayList<>();
            List<String> outOfRangeAmountOwed = new ArrayList<>();
            Map<Integer, Integer> idCountMap = new HashMap<>(); // Track ID counts
            List<Patron> validPatrons = new ArrayList<>();

            // Reads rows from the text file.
            String row;
            while ((row = readFile.readLine()) != null) {
                String[] details = row.split("-");
                if (details.length != 4) {
                    invalidEntries.add(row);
                    continue;
                }

                try {
                    int id = Integer.parseInt(details[0].trim());
                    // Checks for unique and duplicate IDs withing fie.
                    idCountMap.put(id, idCountMap.getOrDefault(id, 0) + 1);

                    // Checks for correct ID range.
                    if (id < 1000000 || id > 9999999) {
                        invalidIDs.add(row);
                        continue;
                    }

                    // Checks for amount owed range.
                    double amountOwed = Double.parseDouble(details[3].trim());
                    if (amountOwed < 0 || amountOwed > 250) {
                        outOfRangeAmountOwed.add(row);
                        continue;
                    }

                    // Temporary list to hold all valid patrons.
                    String name = details[1].trim();
                    String address = details[2].trim();
                    validPatrons.add(new Patron(id, name, address, amountOwed));
                } catch (NumberFormatException e) {
                    invalidEntries.add(row);
                }
            }

            // Identify if there are duplicates within the file.
            Set<Integer> duplicatesInFile = idCountMap.entrySet().stream()
                    .filter(entry -> entry.getValue() > 1)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toSet());

            // Checks for duplicates against the patrons in the ArrayList.
            Set<Integer> existingPatronIDs = patrons.stream()
                    .map(Patron::getId)
                    .collect(Collectors.toSet());

            // All duplicates are combined into one list for output.
            Set<Integer> allDuplicates = new HashSet<>(duplicatesInFile);
            allDuplicates.addAll(existingPatronIDs);

            // Filter valid patrons to remove any that are duplicates.
            validPatrons.removeIf(patron -> allDuplicates.contains(patron.getId()));

            // Add valid patrons to the main patrons Arraylist.
            patrons.addAll(validPatrons);

            // Display messages for any issues encountered.
            if (!allDuplicates.isEmpty()) {
                System.out.println("\nThe following IDs are duplicated within the file or in the application and were skipped:");
                for (Integer duplicateId : allDuplicates) {
                    System.out.println("ID: " + duplicateId);
                }
            }

            // Checks if there are invalid IDs to display.
            if (!invalidIDs.isEmpty() || !outOfRangeAmountOwed.isEmpty()) {
                if (!allDuplicates.isEmpty()) {
                    System.out.println();
                }

                if (!invalidIDs.isEmpty()) {
                    System.out.println("The following entries have invalid IDs (must be exactly 7 digits and start with a 1)\nor incorrect formatting and were skipped:");
                    invalidIDs.forEach(System.out::println);
                }

                if(!invalidEntries.isEmpty()){
                    System.out.println("The following entries have incorrect formatting and were skipped:");
                    invalidEntries.forEach(System.out::println);
                }

                if (!outOfRangeAmountOwed.isEmpty()) {
                    System.out.println("\nThe following entries have invalid amounts owed (must be between $0 and $250) and were skipped:");
                    outOfRangeAmountOwed.forEach(System.out::println);
                }
            }

            // Final message is displayed whether any errors occurred or not.
            System.out.println("\nFile processing complete.\n");

        } catch (IOException e) {
            System.out.println("\nError reading the file. Please ensure the file path is correct.");
        }
    }
}
