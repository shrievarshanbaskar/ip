package fozza;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Handles reading from and writing to the data file.
 */
public class Storage {

    private final Path filePath;

    // Initializes storage with default file path
    public Storage() {
        this.filePath = Paths.get("data", "fozza.txt");
    }

    // Loads tasks from the data file
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

    // Saves all tasks to the data file
    public void save(ArrayList<Task> tasks) throws IOException {
        Files.createDirectories(filePath.getParent());

        try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
            for (Task task : tasks) {
                writer.write(task.toFileString());
                writer.newLine();
            }
        }
    }

    // Converts a single line from file into a Task object
    private Task parseTask(String line) {
        try {
            String[] parts = line.split("\\|");

            if (parts.length < 3) {
                throw new IllegalArgumentException();
            }

            String type = parts[0].trim();
            boolean isDone = parts[1].trim().equals("1");
            String description = parts[2].trim();

            switch (type) {
                case "T":
                    return new Todo(description, isDone);

                case "D":
                    if (parts.length != 4) {
                        throw new IllegalArgumentException();
                    }
                    return new Deadline(
                            description,
                            isDone,
                            parts[3].trim()
                    );

                case "E":
                    if (parts.length != 5) {
                        throw new IllegalArgumentException();
                    }
                    return new Event(
                            description,
                            isDone,
                            parts[3].trim(),
                            parts[4].trim()
                    );

                case "N":
                    if (parts.length != 3) {
                        throw new IllegalArgumentException();
                    }
                    return new Note(description, isDone);

                default:
                    throw new IllegalArgumentException();
            }

        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "Corrupted data file."
            );
        }
    }
}
