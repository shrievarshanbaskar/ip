package fozza;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a deadline task.
 */
public class Deadline extends Task {

    private static final DateTimeFormatter INPUT_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd[ HH:mm]");
    private static final DateTimeFormatter OUTPUT_FORMAT =
            DateTimeFormatter.ofPattern("MMM dd yyyy h:mm a");

    private LocalDateTime byDate;
    private String byText;

    public Deadline(String name, boolean isDone, String by) {
        super(name, isDone);

        try {
            this.byDate = LocalDateTime.parse(by + " 00:00", INPUT_FORMAT);
            this.byText = null;
        } catch (Exception e) {
            this.byDate = null;
            this.byText = by;
        }
    }

    @Override
    public String toString() {
        if (byDate != null) {
            return "[D]" + super.toString()
                    + " (by: " + byDate.format(OUTPUT_FORMAT) + ")";
        }
        return "[D]" + super.toString()
                + " (by: " + byText + ")";
    }

    @Override
    public String toFileString() {
        if (byDate != null) {
            return "D | " + (isDone ? "1" : "0")
                    + " | " + name
                    + " | " + byDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        }
        return "D | " + (isDone ? "1" : "0")
                + " | " + name
                + " | " + byText;
    }
}
