package fozza;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Handles loading and saving tasks to persistent storage.
 */
public class Storage {

    private final Path filePath;

    // Creates storage pointing to data/fozza.txt
    public Storage() {
        this.filePath = Paths.get("data", "fozza.txt");
    }

    // Loads tasks from file into memory
    public ArrayList<Task> load() throws IOException {
        ArrayList<Task> tasks = new ArrayList<>();

        if (!Files.exists(filePath)) {
            Files.createDirectories(filePath.getParent());
            Files.createFile(filePath);
            return tasks;
        }

        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    tasks.add(parseTask(line));
                }
            }
        }

        return tasks;
    }

    // Saves tasks to file
    public void save(ArrayList<Task> tasks) throws IOException {
        Files.createDirectories(filePath.getParent());

        try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
            for (Task task : tasks) {
                writer.write(task.toFileString());
                writer.newLine();
            }
        }
    }

    // Parses a single stored line into a Task object
    private Task parseTask(String line) {

        String[] parts = line.split("\\|");

        if (parts.length < 3) {
            throw new IllegalArgumentException("Corrupted data file.");
        }

        String type = parts[0].trim();
        boolean isDone = parts[1].trim().equals("1");
        String description = parts[2].trim();

        switch (type) {
            case "T":
                return new Todo(description, isDone);

            case "D":
                if (parts.length != 4) {
                    throw new IllegalArgumentException("Corrupted deadline entry.");
                }
                return new Deadline(description, isDone, parts[3].trim());

            case "E":
                if (parts.length != 5) {
                    throw new IllegalArgumentException("Corrupted event entry.");
                }
                return new Event(description, isDone,
                        parts[3].trim(), parts[4].trim());

            case "N":
                return new Note(description, isDone);

            default:
                throw new IllegalArgumentException("Unknown task type in file.");
        }
    }
}
