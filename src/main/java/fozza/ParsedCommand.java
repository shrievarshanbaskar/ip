public class ParsedCommand {
    public final CommandType type;
    public final String a;
    public final String b;
    public final String c;

    public ParsedCommand(CommandType type, String a, String b, String c) {
        this.type = type;
        this.a = a;
        this.b = b;
        this.c = c;
    }
}
