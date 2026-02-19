package fozza;

/**
 * Abstract base class representing a task.
 */
public abstract class Task {

    protected String name;
    protected boolean isDone;

    // Creates a task with description and completion state
    public Task(String name, boolean isDone) {
        this.name = name;
        this.isDone = isDone;
    }

    // Returns whether the task is completed
    public boolean isDone() {
        return isDone;
    }

    // Sets the completion state of the task
    public void setStatus(boolean done) {
        this.isDone = done;
    }

    // Returns formatted display string
    @Override
    public String toString() {
        return (isDone ? "[X] " : "[ ] ") + name;
    }

    // Returns formatted string for file storage
    public abstract String toFileString();
}
