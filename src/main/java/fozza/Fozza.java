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

            if (cmd.getCommandType() == CommandType.BYE) {
                return "Bye. Hope to see you again soon!";
            }

            if (cmd.getCommandType() == CommandType.LIST) {
                StringBuilder sb = new StringBuilder();
                sb.append("Here are the tasks in your list:\n");
                for (int i = 0; i < tasks.size(); i++) {
                    sb.append(i + 1).append(". ").append(tasks.get(i)).append("\n");
                }
                return sb.toString().trim();
            }

            if (cmd.getCommandType() == CommandType.FIND) {
                StringBuilder sb = new StringBuilder();
                sb.append("Here are the matching tasks in your list:\n");
                for (int i = 0; i < tasks.size(); i++) {
                    Task task = tasks.get(i);
                    if (task.toString().contains(cmd.getArg1())) {
                        sb.append(i + 1).append(". ").append(task).append("\n");
                    }
                }
                return sb.toString().trim();
            }

            if (cmd.getCommandType() == CommandType.MARK) {
                int index = Integer.parseInt(cmd.getArg1()) - 1;
                if (index < 0 || index >= tasks.size()) {
                    throw new FozzaException("That task number does not exist.");
                }
                tasks.get(index).setStatus(true);
                storage.save(tasks.getTasks());
                return "Nice! I've marked this task as done:\n  " + tasks.get(index);
            }

            if (cmd.getCommandType() == CommandType.UNMARK) {
                int index = Integer.parseInt(cmd.getArg1()) - 1;
                if (index < 0 || index >= tasks.size()) {
                    throw new FozzaException("That task number does not exist.");
                }
                tasks.get(index).setStatus(false);
                storage.save(tasks.getTasks());
                return "OK, I've marked this task as not done yet:\n  " + tasks.get(index);
            }

            if (cmd.getCommandType() == CommandType.DELETE) {
                int index = Integer.parseInt(cmd.getArg1()) - 1;
                if (index < 0 || index >= tasks.size()) {
                    throw new FozzaException("That task number does not exist.");
                }
                Task removed = tasks.remove(index);
                storage.save(tasks.getTasks());
                return "Noted. I've removed this task:\n  "
                        + removed
                        + "\nNow you have " + tasks.size() + " tasks in the list.";
            }

            if (cmd.getCommandType() == CommandType.TODO) {
                Task task = new Todo(cmd.getArg1(), false);
                tasks.add(task);
                storage.save(tasks.getTasks());
                return "Got it. I've added this task:\n  "
                        + task
                        + "\nNow you have " + tasks.size() + " tasks in the list.";
            }

            // 🔵 NOTE SUPPORT
            if (cmd.getCommandType() == CommandType.NOTE) {
                Task task = new Note(cmd.getArg1(), false);
                tasks.add(task);
                storage.save(tasks.getTasks());
                return "Got it. I've added this note:\n  "
                        + task
                        + "\nNow you have " + tasks.size() + " tasks in the list.";
            }

            if (cmd.getCommandType() == CommandType.DEADLINE) {
                Task task = new Deadline(cmd.getArg1(), false, cmd.getArg2());
                tasks.add(task);
                storage.save(tasks.getTasks());
                return "Got it. I've added this task:\n  "
                        + task
                        + "\nNow you have " + tasks.size() + " tasks in the list.";
            }

            if (cmd.getCommandType() == CommandType.EVENT) {
                Task task = new Event(cmd.getArg1(), false,
                        cmd.getArg2(), cmd.getArg3());
                tasks.add(task);
                storage.save(tasks.getTasks());
                return "Got it. I've added this task:\n  "
                        + task
                        + "\nNow you have " + tasks.size() + " tasks in the list.";
            }

            return "Command executed.";

        } catch (FozzaException e) {
            return "OOPS!! " + e.getMessage();
        } catch (IOException e) {
            return "OOPS!! I had trouble saving your tasks.";
        } catch (Exception e) {
            return "OOPS!! " + e.getMessage();
        }
    }

    public void run() {
        ui.showWelcome();

        while (true) {
            String input = ui.readCommand();

            try {
                ParsedCommand cmd = Parser.parse(input);

                if (cmd.getCommandType() == CommandType.BYE) {
                    ui.showBye();
                    break;
                }

                if (cmd.getCommandType() == CommandType.LIST) {
                    ui.showLine();
                    System.out.println("Here are the tasks in your list:");
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println((i + 1) + ". " + tasks.get(i));
                    }
                    ui.showLine();
                    continue;
                }

                if (cmd.getCommandType() == CommandType.FIND) {
                    ui.showLine();
                    System.out.println("Here are the matching tasks in your list:");
                    for (int i = 0; i < tasks.size(); i++) {
                        Task task = tasks.get(i);
                        if (task.toString().contains(cmd.getArg1())) {
                            System.out.println((i + 1) + ". " + task);
                        }
                    }
                    ui.showLine();
                    continue;
                }

                if (cmd.getCommandType() == CommandType.MARK) {
                    int index = Integer.parseInt(cmd.getArg1()) - 1;
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

                if (cmd.getCommandType() == CommandType.UNMARK) {
                    int index = Integer.parseInt(cmd.getArg1()) - 1;
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

                if (cmd.getCommandType() == CommandType.DELETE) {
                    int index = Integer.parseInt(cmd.getArg1()) - 1;
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

                if (cmd.getCommandType() == CommandType.TODO) {
                    Task task = new Todo(cmd.getArg1(), false);
                    tasks.add(task);
                    storage.save(tasks.getTasks());
                    ui.showLine();
                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + task);
                    System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                    ui.showLine();
                    continue;
                }

                // 🔵 NOTE SUPPORT
                if (cmd.getCommandType() == CommandType.NOTE) {
                    Task task = new Note(cmd.getArg1(), false);
                    tasks.add(task);
                    storage.save(tasks.getTasks());
                    ui.showLine();
                    System.out.println("Got it. I've added this note:");
                    System.out.println("  " + task);
                    System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                    ui.showLine();
                    continue;
                }

                if (cmd.getCommandType() == CommandType.DEADLINE) {
                    Task task = new Deadline(cmd.getArg1(), false, cmd.getArg2());
                    tasks.add(task);
                    storage.save(tasks.getTasks());
                    ui.showLine();
                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + task);
                    System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                    ui.showLine();
                    continue;
                }

                if (cmd.getCommandType() == CommandType.EVENT) {
                    Task task = new Event(cmd.getArg1(), false,
                            cmd.getArg2(), cmd.getArg3());
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
