package fozza;
/**
 * Abstract base class representing a task in the task list.
 */

public abstract class Task {
    protected String name;
    protected boolean status;

    public Task(String name, boolean status) {
        this.name = name;
        this.status = status;
    }
    public abstract String toFileString();

    public boolean getStatus() {
        return this.status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    /**
     * Returns the string representation of the task for display.
     */
    @Override
    public String toString() {
        if (status) {
            return "[X] " + name;
        }
        return "[ ] " + name;
    }
}
