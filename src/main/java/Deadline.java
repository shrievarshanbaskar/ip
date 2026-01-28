public class Deadline extends Task {
    private String by;

    public Deadline(String name, boolean status, String by) {
        super(name, status);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }

    @Override
    public String toFileString() {
        return "D | " + (status ? "1" : "0") + " | " + name + " | " + by;
    }


}
