package controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Managers {



    public static TaskManager getDefault() {
        //return new InMemoryTaskManager();
        return FileBackedTaskManager.loadFromFile("FileBackedTaskManager.csv");
    }


    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

}
