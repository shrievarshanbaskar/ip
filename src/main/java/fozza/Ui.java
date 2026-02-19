package fozza;

import java.util.Scanner;

/**
 * Handles command-line user interaction.
 */
public class Ui {

    private final Scanner sc = new Scanner(System.in);

    /**
     * Prints a separator line.
     */
    public void showLine() {
        System.out.println("-------------------------------------------------");
    }

    /**
     * Displays welcome message.
     */
    public void showWelcome() {
        showLine();
        System.out.println("Hello! I'm fozza.Fozza");
        System.out.println("What can I do for you?\n");
        showLine();
    }

    /**
     * Reads user input from console.
     *
     * @return user input string
     */
    public String readCommand() {
        return sc.nextLine();
    }

    /**
     * Displays goodbye message.
     */
    public void showBye() {
        System.out.println("Bye. Hope to see you again soon!");
        showLine();
    }

    /**
     * Displays an error message.
     *
     * @param message error details
     */
    public void showError(String message) {
        showLine();
        System.out.println("OOPS!! " + message);
        showLine();
    }

    /**
     * Displays save error message.
     */
    public void showSaveError() {
        showLine();
        System.out.println("OOPS!! I had trouble saving your tasks.");
        showLine();
    }

    /**
     * Displays loading error message.
     */
    public void showLoadingError() {
        showLine();
        System.out.println("OOPS!! I had trouble loading your tasks.");
        showLine();
    }
}
