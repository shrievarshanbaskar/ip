package fozza;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Storage {
    private final Path filePath;

    public Storage() {
        this.filePath = Paths.get("data", "fozza.txt");
    }

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

    public void save(ArrayList<Task> tasks) throws IOException {
        Files.createDirectories(filePath.getParent());

        try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
            for (Task t : tasks) {
                writer.write(t.toFileString());
                writer.newLine();
            }
        }
    }

    private Task parseTask(String line) {
        try {
            String[] parts = line.split(" \\| ");

            // ASSERTION 1: every stored task must have at least 3 components
            assert parts.length >= 3
                    : "Stored task line must have at least 3 parts";

            String type = parts[0];
            boolean isDone = parts[1].equals("1");

            switch (type) {
                case "T":
                    return new Todo(parts[2], isDone);

                case "D":
                    // ASSERTION 2: Deadline must have exactly 4 parts
                    assert parts.length == 4
                            : "Deadline line must have 4 parts";
                    return new Deadline(parts[2], isDone, parts[3]);

                case "E":
                    // ASSERTION 3: Event must have exactly 5 parts
                    assert parts.length == 5
                            : "Event line must have 5 parts";
                    return new Event(parts[2], isDone, parts[3], parts[4]);

                default:
                    throw new IllegalArgumentException();
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Corrupted data file");
        }
    }
}
