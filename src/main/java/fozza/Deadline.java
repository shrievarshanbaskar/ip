package fozza;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    private LocalDateTime byDate;
    private String byText;

    private static final DateTimeFormatter INPUT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd[ HH:mm]");
    private static final DateTimeFormatter OUTPUT =
            DateTimeFormatter.ofPattern("MMM dd yyyy h:mm a");

    public Deadline(String name, boolean status, String by) {
        super(name, status);
        try {
            this.byDate = LocalDateTime.parse(by + " 00:00", INPUT);
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
                    + " (by: " + byDate.format(OUTPUT) + ")";
        }
        return "[D]" + super.toString()
                + " (by: " + byText + ")";
    }

    @Override
    public String toFileString() {
        if (byDate != null) {
            return "D | " + (status ? "1" : "0")
                    + " | " + name
                    + " | " + byDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        }
        return "D | " + (status ? "1" : "0")
                + " | " + name
                + " | " + byText;
    }
}
