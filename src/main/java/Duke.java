import java.util.Scanner;
import java.util.ArrayList;

public class Duke {
    public static void main(String[] args) {

        System.out.println("-------------------------------------------------");
        System.out.println("Hello! I'm Fozza");
        System.out.println("What can I do for you?\n");
        System.out.println("-------------------------------------------------");

        Scanner sc = new Scanner(System.in);
        ArrayList<Task> list = new ArrayList<>();

        while (true) {
            String input = sc.nextLine();

            try {
                if (input.equals("bye")) {
                    System.out.println("Bye. Hope to see you again soon!");
                    System.out.println("------------------------------------------------");
                    break;
                }

                else if (input.equals("todo") || input.length() <= 5 && input.startsWith("todo")) {
                    throw new DukeException("The description of a todo cannot be empty.");
                }

                else if (input.startsWith("todo ")) {
                    String name = input.substring(5);
                    Task task = new Todo(name, false);
                    list.add(task);

                    System.out.println("-------------------------------------------------");
                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + task);
                    System.out.println("Now you have " + list.size() + " tasks in the list.");
                    System.out.println("-------------------------------------------------");
                }

                else if (input.startsWith("deadline ")) {
                    if (!input.contains(" /by ")) {
                        throw new DukeException("A deadline must have a /by.");
                    }

                    String[] parts = input.substring(9).split(" /by ");
                    Task task = new Deadline(parts[0], false, parts[1]);
                    list.add(task);

                    System.out.println("-------------------------------------------------");
                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + task);
                    System.out.println("Now you have " + list.size() + " tasks in the list.");
                    System.out.println("-------------------------------------------------");
                }

                else if (input.startsWith("event ")) {
                    if (!input.contains(" /from ") || !input.contains(" /to ")) {
                        throw new DukeException("An event must have /from and /to.");
                    }

                    String[] first = input.substring(6).split(" /from ");
                    String[] second = first[1].split(" /to ");

                    Task task = new Event(first[0], false, second[0], second[1]);
                    list.add(task);

                    System.out.println("-------------------------------------------------");
                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + task);
                    System.out.println("Now you have " + list.size() + " tasks in the list.");
                    System.out.println("-------------------------------------------------");
                }

                else if (input.equals("list")) {
                    System.out.println("------------------------------------------------");
                    System.out.println("Here are the tasks in your list:");
                    for (int i = 0; i < list.size(); i++) {
                        System.out.println((i + 1) + ". " + list.get(i));
                    }
                    System.out.println("------------------------------------------------");
                }

                else {
                    throw new DukeException("I'm sorry, but I don't know what that means.");
                }

            } catch (DukeException e) {
                System.out.println("-------------------------------------------------");
                System.out.println("OOPS!! " + e.getMessage());
                System.out.println("-------------------------------------------------");
            }
        }

        sc.close();
    }
}
