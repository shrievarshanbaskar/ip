import java.util.Scanner;
import java.util.ArrayList;

public class Duke{
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
                System.out.println("Bye. Hope to see you again soon!\n");
                System.out.println("------------------------------------------------");
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
                for(int i = 0; i<list.size(); i++) {
                    System.out.println(i+1 +". " + list.get(i).toString());
                }
                System.out.println("------------------------------------------------");

            } else {

                list.add(new Task(input, false));
                System.out.println("-------------------------------------------------");
                System.out.println("  " + input);
                System.out.println("-------------------------------------------------");
            }

            }

        }
}

