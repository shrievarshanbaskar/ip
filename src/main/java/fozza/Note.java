package fozza;

/**
 * Represents a note task.
 */
public class Note extends Task {

    public Note(String name, boolean status) {
        super(name, status);
    }

    @Override
    public String toString() {
        return "[N]" + super.toString();
    }

    @Override
    public String toFileString() {
        return "N | " + (status ? "1" : "0") + " | " + name;
    }
}
