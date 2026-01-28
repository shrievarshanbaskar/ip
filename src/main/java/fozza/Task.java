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

    @Override
    public String toString() {
        if (status) {
            return "[X] " + name;
        }
        return "[ ] " + name;
    }
}
