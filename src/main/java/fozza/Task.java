package fozza;

/**
 * Represents a generic task.
 */
public abstract class Task {

    protected String name;
    protected boolean status;

    public Task(String name, boolean status) {
        this.name = name;
        this.status = status;
    }

    public boolean isDone() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public abstract String toFileString();

    @Override
    public String toString() {
        return (status ? "[X] " : "[ ] ") + name;
    }
}
