package fozza;

import java.io.*;
import java.nio.file.*;
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

        BufferedReader reader = Files.newBufferedReader(filePath);
        String line;

        while ((line = reader.readLine()) != null) {
            if (!line.trim().isEmpty()) {
                tasks.add(parseTask(line));
            }
        }

        reader.close();
        return tasks;
    }

    public void save(ArrayList<Task> tasks) throws IOException {
        Files.createDirectories(filePath.getParent());
        BufferedWriter writer = Files.newBufferedWriter(filePath);

        for (Task t : tasks) {
            writer.write(t.toFileString());
            writer.newLine();
        }

        writer.close();
    }

    private Task parseTask(String line) {
        String[] parts = line.split(" \\| ");

        String type = parts[0];
        boolean isDone = parts[1].equals("1");

        switch (type) {
            case "T":
                return new Todo(parts[2], isDone);
            case "D":
                return new Deadline(parts[2], isDone, parts[3]);
            case "E":
                return new Event(parts[2], isDone, parts[3], parts[4]);
            default:
                throw new IllegalArgumentException("Corrupted data file");
        }
    }
}
