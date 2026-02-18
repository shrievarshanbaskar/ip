package fozza;

public class Parser {

    public static ParsedCommand parse(String input) throws FozzaException {
        input = input.trim();

        if (input.equals("bye")) {
            return new ParsedCommand(CommandType.BYE);
        }

        if (input.equals("list")) {
            return new ParsedCommand(CommandType.LIST);
        }

        if (input.startsWith("mark ")) {
            return new ParsedCommand(CommandType.MARK, input.substring(5).trim());
        }

        if (input.startsWith("unmark ")) {
            return new ParsedCommand(CommandType.UNMARK, input.substring(7).trim());
        }

        if (input.startsWith("find ")) {
            String keyword = input.substring(5).trim();
            if (keyword.isEmpty()) {
                throw new FozzaException("The find command needs a keyword.");
            }
            return new ParsedCommand(CommandType.FIND, keyword);
        }

        if (input.startsWith("delete ")) {
            return new ParsedCommand(CommandType.DELETE, input.substring(7).trim());
        }

        if (input.equals("todo") || input.equals("todo ")) {
            throw new FozzaException("The description of a todo cannot be empty.");
        }

        if (input.startsWith("todo ")) {
            return new ParsedCommand(CommandType.TODO, input.substring(5).trim());
        }

        // 🔵 NEW NOTE COMMAND
        if (input.startsWith("note ")) {
            String content = input.substring(5).trim();
            if (content.isEmpty()) {
                throw new FozzaException("The description of a note cannot be empty.");
            }
            return new ParsedCommand(CommandType.NOTE, content);
        }

        if (input.startsWith("deadline ")) {
            if (!input.contains(" /by ")) {
                throw new FozzaException("A deadline must have a /by.");
            }
            String[] parts = input.substring(9).split(" /by ");

            assert parts.length == 2
                    : "Deadline command must split into 2 parts";

            return new ParsedCommand(CommandType.DEADLINE,
                    parts[0].trim(),
                    parts[1].trim());
        }

        if (input.startsWith("event ")) {
            if (!input.contains(" /from ") || !input.contains(" /to ")) {
                throw new FozzaException("An event must have /from and /to.");
            }

            String[] first = input.substring(6).split(" /from ");

            assert first.length == 2
                    : "Event must contain /from";

            String[] second = first[1].split(" /to ");

            assert second.length == 2
                    : "Event must contain /to";

            return new ParsedCommand(CommandType.EVENT,
                    first[0].trim(),
                    second[0].trim(),
                    second[1].trim());
        }

        throw new FozzaException("I'm sorry, but I don't know what that means.");
    }
}
