package fozza;

/**
 * Represents a parsed user command and its associated arguments.
 */
public class ParsedCommand {

    private CommandType commandType;
    private String arg1;
    private String arg2;
    private String arg3;

    /**
     * Creates a ParsedCommand with only a command type.
     */
    public ParsedCommand(CommandType commandType) {
        this.commandType = commandType;
    }

    /**
     * Creates a ParsedCommand with one argument.
     */
    public ParsedCommand(CommandType commandType, String arg1) {
        this.commandType = commandType;
        this.arg1 = arg1;
    }

    /**
     * Creates a ParsedCommand with two arguments.
     */
    public ParsedCommand(CommandType commandType, String arg1, String arg2) {
        this.commandType = commandType;
        this.arg1 = arg1;
        this.arg2 = arg2;
    }

    /**
     * Creates a ParsedCommand with three arguments.
     */
    public ParsedCommand(CommandType commandType, String arg1, String arg2, String arg3) {
        this.commandType = commandType;
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.arg3 = arg3;
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public String getArg1() {
        return arg1;
    }

    public String getArg2() {
        return arg2;
    }

    public String getArg3() {
        return arg3;
    }
}
