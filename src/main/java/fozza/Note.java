package fozza;

/**
 * Represents a note task.
 */
public class Note extends Task {

    public Note(String name, boolean isDone) {
        super(name, isDone);
    }

    @Override
    public String toString() {
        return "[N]" + super.toString();
    }

    @Override
    public String toFileString() {
        return "N | " + (isDone ? "1" : "0") + " | " + name;
    }
}
