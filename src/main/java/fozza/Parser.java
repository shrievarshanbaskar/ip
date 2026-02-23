package fozza;

/**
 * Parses raw user input into structured ParsedCommand objects.
 */
public class Parser {

    // Converts a raw input string into a ParsedCommand
    public static ParsedCommand parse(String input) throws FozzaException {
        input = input.trim();

        // Exit command
        if (input.equals("bye")) {
            return new ParsedCommand(CommandType.BYE);
        }


        // List all tasks
        if (input.equals("list")) {
            return new ParsedCommand(CommandType.LIST);
        }


        // Mark task as done
        if (input.startsWith("mark ")) {
            return new ParsedCommand(
                    CommandType.MARK,
                    input.substring(5).trim()
            );
        }

        // Unmark task
        if (input.startsWith("unmark ")) {
            return new ParsedCommand(
                    CommandType.UNMARK,
                    input.substring(7).trim()
            );
        }

        // Find tasks by keyword
        if (input.startsWith("find ")) {
            String keyword = input.substring(5).trim();
            if (keyword.isEmpty()) {
                throw new FozzaException(
                        "The find command needs a keyword."
                );
            }
            return new ParsedCommand(CommandType.FIND, keyword);
        }


        // Delete task
        if (input.startsWith("delete ")) {
            return new ParsedCommand(
                    CommandType.DELETE,
                    input.substring(7).trim()
            );
        }

        // Validate empty todo command
        if (input.equals("todo") || input.equals("todo ")) {
            throw new FozzaException(
                    "The description of a todo cannot be empty."
            );
        }

        // Add todo task
        if (input.startsWith("todo ")) {
            return new ParsedCommand(
                    CommandType.TODO,
                    input.substring(5).trim()
            );
        }

        // Add note task
        if (input.startsWith("note ")) {
            String content = input.substring(5).trim();
            if (content.isEmpty()) {
                throw new FozzaException(
                        "The description of a note cannot be empty."
                );
            }
            return new ParsedCommand(CommandType.NOTE, content);
        }


        // Add deadline task
        if (input.startsWith("deadline ")) {
            if (!input.contains(" /by ")) {
                throw new FozzaException(
                        "A deadline must have a /by."
                );
            }

            String[] parts = input.substring(9).split(" /by ");

            if (parts.length != 2) {
                throw new FozzaException(
                        "Invalid deadline format."
                );
            }

            return new ParsedCommand(
                    CommandType.DEADLINE,
                    parts[0].trim(),
                    parts[1].trim()
            );
        }

        // Add event task
        if (input.startsWith("event ")) {
            if (!input.contains(" /from ")
                    || !input.contains(" /to ")) {
                throw new FozzaException(
                        "An event must have /from and /to."
                );
            }

            String[] first =
                    input.substring(6).split(" /from ");

            if (first.length != 2) {
                throw new FozzaException(
                        "Invalid event format."
                );
            }

            String[] second =
                    first[1].split(" /to ");

            if (second.length != 2) {
                throw new FozzaException(
                        "Invalid event format."
                );
            }

            return new ParsedCommand(
                    CommandType.EVENT,
                    first[0].trim(),
                    second[0].trim(),
                    second[1].trim()
            );
        }

        // Unknown command
        throw new FozzaException(
                "I'm sorry, but I don't know what that means."
        );
    }
}
