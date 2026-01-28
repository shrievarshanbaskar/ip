package fozza;
/**
 * Represents a simple todo task without dates.
 */

public class Todo extends Task {

    public Todo(String name, boolean status) {
        super(name, status);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public String toFileString() {
        return "T | " + (status ? "1" : "0") + " | " + name;
    }
}
