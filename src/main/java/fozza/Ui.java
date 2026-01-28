package fozza;

import java.util.Scanner;

public class Ui {
    private final Scanner sc = new Scanner(System.in);

    public void showLine() {
        System.out.println("-------------------------------------------------");
    }

    public void showWelcome() {
        showLine();
        System.out.println("Hello! I'm fozza.Fozza");
        System.out.println("What can I do for you?\n");
        showLine();
    }

    public String readCommand() {
        return sc.nextLine();
    }

    public void showBye() {
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println("------------------------------------------------");
    }

    public void showError(String message) {
        showLine();
        System.out.println("OOPS!! " + message);
        showLine();
    }

    public void showSaveError() {
        showLine();
        System.out.println("OOPS!! I had trouble saving your tasks.");
        showLine();
    }

    public void showLoadingError() {
        showLine();
        System.out.println("OOPS!! I had trouble loading your tasks.");
        showLine();
    }
}
