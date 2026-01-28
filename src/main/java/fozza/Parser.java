package fozza;
/**
 * Parses raw user input strings into structured commands.
 */

public class Parser {

    /**
     * Parses a user command string and returns a ParsedCommand.
     *
     * @param input Full user input line
     * @return ParsedCommand representing the command
     * @throws FozzaException if the command format is invalid
     */

    public static ParsedCommand parse(String input) throws Fozza.FozzaException {
        input = input.trim();

        if (input.equals("bye")) {
            return new ParsedCommand(CommandType.BYE, null, null, null);
        }

        if (input.equals("list")) {
            return new ParsedCommand(CommandType.LIST, null, null, null);
        }

        if (input.startsWith("delete ")) {
            String num = input.substring(7).trim();
            return new ParsedCommand(CommandType.DELETE, num, null, null);
        }

        if (input.equals("todo") || (input.length() <= 5 && input.startsWith("todo"))) {
            throw new Fozza.FozzaException("The description of a todo cannot be empty.");
        }

        if (input.startsWith("todo ")) {
            String name = input.substring(5);
            return new ParsedCommand(CommandType.TODO, name, null, null);
        }

        if (input.startsWith("deadline ")) {
            if (!input.contains(" /by ")) {
                throw new Fozza.FozzaException("A deadline must have a /by.");
            }
            String[] parts = input.substring(9).split(" /by ");
            return new ParsedCommand(CommandType.DEADLINE, parts[0], parts[1], null);
        }

        if (input.startsWith("event ")) {
            if (!input.contains(" /from ") || !input.contains(" /to ")) {
                throw new Fozza.FozzaException("An event must have /from and /to.");
            }
            String[] first = input.substring(6).split(" /from ");
            String[] second = first[1].split(" /to ");
            return new ParsedCommand(CommandType.EVENT, first[0], second[0], second[1]);
        }

        throw new Fozza.FozzaException("I'm sorry, but I don't know what that means.");
    }
}
