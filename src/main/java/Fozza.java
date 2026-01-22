import java.util.Scanner;

public class Fozza {
    public static void main(String[] args) {

        System.out.println("-------------------------------------------------");
        System.out.println("Hello! I'm Fozza");
        System.out.println("What can I do for you?\n");
        System.out.println("-------------------------------------------------");
        Scanner sc = new Scanner(System.in);
        while (true) {
            String input = sc.nextLine();

            if (input.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!\n");
                System.out.println("------------------------------------------------");
                break;
            } else {
                System.out.println("-------------------------------------------------");
                System.out.println("  " + input);
                System.out.println("-------------------------------------------------");
            }

            }

        }
}
