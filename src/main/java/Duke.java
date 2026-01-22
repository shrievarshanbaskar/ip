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

            if (input.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                System.out.todotoprintln("------------------------------------------------");
                break;
            }

            else if (input.startsWith("mark ")) {
                int index = Integer.parseInt(input.substring(5)) - 1;
                Task task = list.get(index);
                task.setStatus(true);

                System.out.println("-------------------------------------------------");
                System.out.println("Nice! I've marked this task as done:");
                System.out.println("  " + task);
                System.out.println("-------------------------------------------------");
            }

            else if (input.startsWith("unmark ")) {
                int index = Integer.parseInt(input.substring(7)) - 1;
                Task task = list.get(index);
                task.setStatus(false);

                System.out.println("-------------------------------------------------");
                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println("  " + task);
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
                String[] parts = input.substring(9).split(" /by ");
                String name = parts[0];
                String by = parts[1];

                Task task = new Deadline(name, false, by);
                list.add(task);

                System.out.println("-------------------------------------------------");
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + task);
                System.out.println("Now you have " + list.size() + " tasks in the list.");
                System.out.println("-------------------------------------------------");
            }

            else if (input.startsWith("event ")) {
                String[] firstSplit = input.substring(6).split(" /from ");
                String name = firstSplit[0];

                String[] secondSplit = firstSplit[1].split(" /to ");
                String from = secondSplit[0];
                String to = secondSplit[1];

                Task task = new Event(name, false, from, to);
                list.add(task);

                System.out.println("-------------------------------------------------");
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + task);
                System.out.println("Now you have " + list.size() + " tasks in the list.");
                System.out.println("-------------------------------------------------");
            }
        }

        sc.close();
    }
}
