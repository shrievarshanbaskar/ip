package fozza;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a deadline task.
 */
public class Deadline extends Task {

    private LocalDateTime byDate;
    private String byText;

    private static final DateTimeFormatter INPUT_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd[ HH:mm]");

    private static final DateTimeFormatter OUTPUT_FORMAT =
            DateTimeFormatter.ofPattern("MMM dd yyyy h:mm a");

    private static final DateTimeFormatter FILE_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public Deadline(String name, boolean status, String by) {
        super(name, status);
        parseDate(by);
    }

    private void parseDate(String by) {
        try {
            this.byDate = LocalDateTime.parse(by + " 00:00", INPUT_FORMAT);
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
            return "D | " + (status ? "1" : "0")
                    + " | " + name
                    + " | " + byDate.format(FILE_FORMAT);
        }
        return "D | " + (status ? "1" : "0")
                + " | " + name
                + " | " + byText;
    }
}
