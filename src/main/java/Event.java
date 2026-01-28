public class Event extends Task {
    private String from;
    private String to;

    public Event(String name, boolean status, String from, String to) {
        super(name, status);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString()
                + " (from: " + from + " to: " + to + ")";
    }
    @Override
    public String toFileString() {
        return "E | " + (status ? "1" : "0") + " | " + name + " | " + from + " | " + to;
    }


}
