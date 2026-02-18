package fozza;

import java.io.IOException;

/**
 * Main entry point of the Fozza chatbot application.
 * Handles program startup and the main event loop.
 */
public class Fozza {
    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;

    public Fozza() {
        this.ui = new Ui();
        this.storage = new Storage();

        TaskList loaded;
        try {
            loaded = new TaskList(storage.load());
        } catch (Exception e) {
            ui.showLoadingError();
            loaded = new TaskList();
        }
        this.tasks = loaded;
    }

    public String getResponse(String input) {
        try {
            ParsedCommand cmd = Parser.parse(input);

            // BYE
            if (cmd.type == CommandType.BYE) {
                return "Bye. Hope to see you again soon!";
            }

            // LIST
            if (cmd.type == CommandType.LIST) {
                StringBuilder sb = new StringBuilder();
                sb.append("Here are the tasks in your list:\n");
                for (int i = 0; i < tasks.size(); i++) {
                    sb.append(i + 1).append(". ").append(tasks.get(i)).append("\n");
                }
                return sb.toString().trim();
            }

            // FIND
            if (cmd.type == CommandType.FIND) {
                StringBuilder sb = new StringBuilder();
                sb.append("Here are the matching tasks in your list:\n");
                for (int i = 0; i < tasks.size(); i++) {
                    Task task = tasks.get(i);
                    if (task.toString().contains(cmd.a)) {
                        sb.append(i + 1).append(". ").append(task).append("\n");
                    }
                }
                return sb.toString().trim();
            }

            // MARK
            if (cmd.type == CommandType.MARK) {
                int index = Integer.parseInt(cmd.a) - 1;
                if (index < 0 || index >= tasks.size()) {
                    throw new FozzaException("That task number does not exist.");
                }

                tasks.get(index).setStatus(true);
                storage.save(tasks.getTasks());

                return "Nice! I've marked this task as done:\n  "
                        + tasks.get(index);
            }

            // UNMARK
            if (cmd.type == CommandType.UNMARK) {
                int index = Integer.parseInt(cmd.a) - 1;
                if (index < 0 || index >= tasks.size()) {
                    throw new FozzaException("That task number does not exist.");
                }

                tasks.get(index).setStatus(false);
                storage.save(tasks.getTasks());

                return "OK, I've marked this task as not done yet:\n  "
                        + tasks.get(index);
            }

            // DELETE
            if (cmd.type == CommandType.DELETE) {
                int index = Integer.parseInt(cmd.a) - 1;
                if (index < 0 || index >= tasks.size()) {
                    throw new FozzaException("That task number does not exist.");
                }

                Task removed = tasks.remove(index);
                storage.save(tasks.getTasks());

                return "Noted. I've removed this task:\n  "
                        + removed
                        + "\nNow you have " + tasks.size() + " tasks in the list.";
            }

            // TODO
            if (cmd.type == CommandType.TODO) {
                Task task = new Todo(cmd.a, false);
                tasks.add(task);
                storage.save(tasks.getTasks());

                return "Got it. I've added this task:\n  "
                        + task
                        + "\nNow you have " + tasks.size() + " tasks in the list.";
            }

            // DEADLINE (preserves your date/text handling)
            if (cmd.type == CommandType.DEADLINE) {
                Task task = new Deadline(cmd.a, false, cmd.b);
                tasks.add(task);
                storage.save(tasks.getTasks());

                return "Got it. I've added this task:\n  "
                        + task
                        + "\nNow you have " + tasks.size() + " tasks in the list.";
            }

            // EVENT (preserves /from /to + formatting)
            if (cmd.type == CommandType.EVENT) {
                Task task = new Event(cmd.a, false, cmd.b, cmd.c);
                tasks.add(task);
                storage.save(tasks.getTasks());

                return "Got it. I've added this task:\n  "
                        + task
                        + "\nNow you have " + tasks.size() + " tasks in the list.";
            }

            // SHOULD NEVER REACH HERE
            return "Command executed.";

        } catch (FozzaException e) {
            return "OOPS!! " + e.getMessage();
        } catch (IOException e) {
            return "OOPS!! I had trouble saving your tasks.";
        } catch (Exception e) {
            return "OOPS!! " + e.getMessage();
        }
    }



    /**
     * Runs the main command-processing loop of the chatbot.
     */
    public void run() {
        ui.showWelcome();

        while (true) {
            String input = ui.readCommand();

            try {
                ParsedCommand cmd = Parser.parse(input);

                if (cmd.type == CommandType.BYE) {
                    ui.showBye();
                    break;
                }

                if (cmd.type == CommandType.LIST) {
                    ui.showLine();
                    System.out.println("Here are the tasks in your list:");
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println((i + 1) + ". " + tasks.get(i));
                    }
                    ui.showLine();
                    continue;
                }

                if (cmd.type == CommandType.FIND) {
                    ui.showLine();
                    System.out.println("Here are the matching tasks in your list:");
                    for (int i = 0; i < tasks.size(); i++) {
                        Task task = tasks.get(i);
                        if (task.toString().contains(cmd.a)) {
                            System.out.println((i + 1) + ". " + task);
                        }
                    }
                    ui.showLine();
                    continue;
                }

                if (cmd.type == CommandType.MARK) {
                    int index = Integer.parseInt(cmd.a) - 1;
                    if (index < 0 || index >= tasks.size()) {
                        throw new FozzaException("That task number does not exist.");
                    }

                    tasks.get(index).setStatus(true);
                    storage.save(tasks.getTasks());

                    ui.showLine();
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println("  " + tasks.get(index));
                    ui.showLine();
                    continue;
                }

                if (cmd.type == CommandType.UNMARK) {
                    int index = Integer.parseInt(cmd.a) - 1;
                    if (index < 0 || index >= tasks.size()) {
                        throw new FozzaException("That task number does not exist.");
                    }

                    tasks.get(index).setStatus(false);
                    storage.save(tasks.getTasks());

                    ui.showLine();
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println("  " + tasks.get(index));
                    ui.showLine();
                    continue;
                }

                if (cmd.type == CommandType.DELETE) {
                    int index = Integer.parseInt(cmd.a) - 1;
                    if (index < 0 || index >= tasks.size()) {
                        throw new FozzaException("That task number does not exist.");
                    }

                    Task removed = tasks.remove(index);
                    storage.save(tasks.getTasks());

                    ui.showLine();
                    System.out.println("Noted. I've removed this task:");
                    System.out.println("  " + removed);
                    System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                    ui.showLine();
                    continue;
                }

                if (cmd.type == CommandType.TODO) {
                    Task task = new Todo(cmd.a, false);
                    tasks.add(task);
                    storage.save(tasks.getTasks());

                    ui.showLine();
                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + task);
                    System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                    ui.showLine();
                    continue;
                }

                if (cmd.type == CommandType.DEADLINE) {
                    Task task = new Deadline(cmd.a, false, cmd.b);
                    tasks.add(task);
                    storage.save(tasks.getTasks());

                    ui.showLine();
                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + task);
                    System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                    ui.showLine();
                    continue;
                }

                if (cmd.type == CommandType.EVENT) {
                    Task task = new Event(cmd.a, false, cmd.b, cmd.c);
                    tasks.add(task);
                    storage.save(tasks.getTasks());

                    ui.showLine();
                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + task);
                    System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                    ui.showLine();
                }

            } catch (FozzaException e) {
                ui.showError(e.getMessage());
            } catch (IOException e) {
                ui.showSaveError();
            }
        }
    }

    public static void main(String[] args) {
        new Fozza().run();
    }
}
