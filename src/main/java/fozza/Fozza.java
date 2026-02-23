package fozza;

import java.io.IOException;
import javafx.application.Platform;

/**
 * Core logic handler for the Fozza chatbot.
 */
public class Fozza {

    private final Storage storage;
    private final TaskList tasks;

    // Initializes storage and loads existing tasks
    public Fozza() {
        this.storage = new Storage();
        this.tasks = loadTasks();
    }


    //Loads tasks from file or returns an empty list if loading fails
    private TaskList loadTasks() {
        try {
            return new TaskList(storage.load());
        } catch (Exception e) {
            return new TaskList();
        }
    }

    // Processes user input and returns the response message
    public String getResponse(String input) {
        try {
            ParsedCommand cmd = Parser.parse(input);
            return execute(cmd);
        } catch (FozzaException e) {
            return "OOPS!! " + e.getMessage();
        } catch (IOException e) {
            return "OOPS!! I had trouble saving your tasks.";
        }
    }

    // Executes the parsed command and delegates to the appropriate handler
    private String execute(ParsedCommand cmd)
            throws FozzaException, IOException {

        switch (cmd.getCommandType()) {
            case LIST:
                return tasks.buildListString();
            case FIND:
                return tasks.buildFindString(cmd.getArg1());
            case TODO:
                return add(new Todo(cmd.getArg1(), false));
            case NOTE:
                return add(new Note(cmd.getArg1(), false));
            case DEADLINE:
                return add(new Deadline(cmd.getArg1(), false, cmd.getArg2()));
            case EVENT:
                return add(new Event(cmd.getArg1(), false,
                        cmd.getArg2(), cmd.getArg3()));
            case DELETE:
                return delete(cmd);
            case MARK:
                return mark(cmd, true);
            case UNMARK:
                return mark(cmd, false);
            case BYE:
                return exit();
            default:
                throw new FozzaException("Unknown command.");
        }
    }


    // Handles application exit
    private String exit() {
        Platform.exit();
        return "Bye. Hope to see you again soon!";
    }

    // Adds a task to the list and saves it
    private String add(Task task) throws IOException {
        tasks.add(task);
        save();
        return "Got it. I've added this task:\n  "
                + task
                + "\nNow you have " + tasks.size()
                + " tasks in the list.";
    }

    // Deletes a task based on the provided index
    private String delete(ParsedCommand cmd)
            throws FozzaException, IOException {

        int index = parseIndex(cmd.getArg1());
        Task removed = tasks.remove(index);
        save();
        return "Noted. I've removed this task:\n  "
                + removed
                + "\nNow you have " + tasks.size()
                + " tasks in the list.";
    }

    // Marks or unmarks a task depending on the flag
    private String mark(ParsedCommand cmd, boolean done)
            throws FozzaException, IOException {

        int index = parseIndex(cmd.getArg1());
        Task task = tasks.mark(index, done);
        save();

        if (done) {
            return "Nice! I've marked this task as done:\n  " + task;
        }
        return "OK, I've marked this task as not done yet:\n  " + task;
    }


    // Converts user input into a zero-based index
    private int parseIndex(String value)
            throws FozzaException {

        try {
            return Integer.parseInt(value) - 1;
        } catch (NumberFormatException e) {
            throw new FozzaException("Invalid task number.");
        }
    }

    // Persists the current task list to storage
    private void save() throws IOException {
        storage.save(tasks.getTasks());
    }
}