package fozza;

import java.util.ArrayList;

/**
 * Manages a collection of tasks.
 */
public class TaskList {

    private final ArrayList<Task> tasks;

    // Creates an empty task list
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    // Creates a task list from existing tasks
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    // Adds a task to the list
    public void add(Task task) {
        tasks.add(task);
    }

    // Removes a task by index after validation
    public Task remove(int index) throws FozzaException {
        validateIndex(index);
        return tasks.remove(index);
    }

    // Marks or unmarks a task based on the flag
    public Task mark(int index, boolean done)
            throws FozzaException {

        validateIndex(index);
        Task task = tasks.get(index);
        task.setStatus(done);
        return task;
    }

    // Ensures the provided index is within bounds
    private void validateIndex(int index)
            throws FozzaException {

        if (index < 0 || index >= tasks.size()) {
            throw new FozzaException(
                    "That task number does not exist."
            );
        }
    }

    // Returns the number of tasks in the list
    public int size() {
        return tasks.size();
    }

    // Returns the underlying task collection
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    // Builds the formatted string for listing all tasks
    public String buildListString() {
        StringBuilder sb = new StringBuilder(
                "Here are the tasks in your list:\n");

        for (int i = 0; i < tasks.size(); i++) {
            sb.append(i + 1)
                    .append(". ")
                    .append(tasks.get(i))
                    .append("\n");
        }
        return sb.toString().trim();
    }

    // Builds the formatted string for search results
    public String buildFindString(String keyword) {
        StringBuilder sb = new StringBuilder(
                "Here are the matching tasks in your list:\n");

        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).toString().contains(keyword)) {
                sb.append(i + 1)
                        .append(". ")
                        .append(tasks.get(i))
                        .append("\n");
            }
        }
        return sb.toString().trim();
    }
}
