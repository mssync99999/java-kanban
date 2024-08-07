package controllers;
import tickets.Task;
import java.util.List;

public interface HistoryManager {
    void add(Task task);

    List<Task> getHistory();

    void remove(int id); //sprint 6
}
