package controllers;

public class Managers {


    public static TaskManager getDefault() {
        //return new InMemoryTaskManager();
        return FileBackedTaskManager.loadFromFile("FileBackedTaskManager.csv");
    }


    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

}
