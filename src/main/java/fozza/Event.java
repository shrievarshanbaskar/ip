package fozza;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private String fromText;
    private String toText;

    private static final DateTimeFormatter INPUT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd[ HH:mm]");
    private static final DateTimeFormatter OUTPUT =
            DateTimeFormatter.ofPattern("MMM dd yyyy h:mm a");

    public Event(String name, boolean status, String from, String to) {
        super(name, status);

        try {
            this.fromDate = LocalDateTime.parse(from + " 00:00", INPUT);
            this.fromText = null;
        } catch (Exception e) {
            this.fromDate = null;
            this.fromText = from;
        }

        try {
            this.toDate = LocalDateTime.parse(to + " 00:00", INPUT);
            this.toText = null;
        } catch (Exception e) {
            this.toDate = null;
            this.toText = to;
        }
    }

    @Override
    public String toString() {
        if (fromDate != null && toDate != null) {
            return "[E]" + super.toString()
                    + " (from: " + fromDate.format(OUTPUT)
                    + " to: " + toDate.format(OUTPUT) + ")";
        }
        return "[E]" + super.toString()
                + " (from: " + fromText + " to: " + toText + ")";
    }

    @Override
    public String toFileString() {
        if (fromDate != null && toDate != null) {
            return "E | " + (status ? "1" : "0")
                    + " | " + name
                    + " | " + fromDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
                    + " | " + toDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        }
        return "E | " + (status ? "1" : "0")
                + " | " + name
                + " | " + fromText
                + " | " + toText;
    }
}
