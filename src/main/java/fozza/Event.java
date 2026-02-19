package fozza;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an event task.
 */
public class Event extends Task {

    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private String fromText;
    private String toText;

    private static final DateTimeFormatter INPUT_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd[ HH:mm]");

    private static final DateTimeFormatter OUTPUT_FORMAT =
            DateTimeFormatter.ofPattern("MMM dd yyyy h:mm a");

    private static final DateTimeFormatter FILE_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public Event(String name, boolean status, String from, String to) {
        super(name, status);
        parseDates(from, to);
    }

    private void parseDates(String from, String to) {
        try {
            this.fromDate = LocalDateTime.parse(from + " 00:00", INPUT_FORMAT);
        } catch (Exception e) {
            this.fromText = from;
        }

        try {
            this.toDate = LocalDateTime.parse(to + " 00:00", INPUT_FORMAT);
        } catch (Exception e) {
            this.toText = to;
        }
    }

    @Override
    public String toString() {
        if (fromDate != null && toDate != null) {
            return "[E]" + super.toString()
                    + " (from: " + fromDate.format(OUTPUT_FORMAT)
                    + " to: " + toDate.format(OUTPUT_FORMAT) + ")";
        }
        return "[E]" + super.toString()
                + " (from: " + fromText + " to: " + toText + ")";
    }

    @Override
    public String toFileString() {
        if (fromDate != null && toDate != null) {
            return "E | " + (status ? "1" : "0")
                    + " | " + name
                    + " | " + fromDate.format(FILE_FORMAT)
                    + " | " + toDate.format(FILE_FORMAT);
        }
        return "E | " + (status ? "1" : "0")
                + " | " + name
                + " | " + fromText
                + " | " + toText;
    }
}
