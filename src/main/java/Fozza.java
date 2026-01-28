import java.io.IOException;

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
                    System.out.println("------------------------------------------------");
                    System.out.println("Here are the tasks in your list:");
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println((i + 1) + ". " + tasks.get(i));
                    }
                    System.out.println("------------------------------------------------");
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
