package fozza;

public class ParsedCommand {

    public CommandType type;
    public String a;
    public String b;
    public String c;

    public ParsedCommand(CommandType type) {
        this.type = type;
    }

    public ParsedCommand(CommandType type, String a) {
        this.type = type;
        this.a = a;
    }

    public ParsedCommand(CommandType type, String a, String b) {
        this.type = type;
        this.a = a;
        this.b = b;
    }

    public ParsedCommand(CommandType type, String a, String b, String c) {
        this.type = type;
        this.a = a;
        this.b = b;
        this.c = c;
    }

    // ===== getters for JUnit =====
    public CommandType getCommandType() {
        return type;
    }

    public String getA() {
        return a;
    }

    public String getB() {
        return b;
    }

    public String getC() {
        return c;
    }
}
