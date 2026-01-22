import java.util.Scanner;
import java.util.ArrayList;

public class Duke{
    public static void main(String[] args) {

        System.out.println("-------------------------------------------------");
        System.out.println("Hello! I'm Fozza");
        System.out.println("What can I do for you?\n");
        System.out.println("-------------------------------------------------");

        Scanner sc = new Scanner(System.in);
        ArrayList<String> list = new ArrayList<>();


        while (true) {
            String input = sc.nextLine();

            if (input.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!\n");
                System.out.println("------------------------------------------------");
                break;
            } else if (input.equals("list")) {
                System.out.println("------------------------------------------------");
                for(int i = 0; i<list.size(); i++) {
                    System.out.println(i+1 +". " + list.get(i));
                }
                System.out.println("------------------------------------------------");

            } else {

                list.add(input);
                System.out.println("-------------------------------------------------");
                System.out.println("  " + input);
                System.out.println("-------------------------------------------------");
            }

            }

        }
}
