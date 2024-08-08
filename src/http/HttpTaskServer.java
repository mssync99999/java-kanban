package http;

import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;


import controllers.Managers;
import controllers.TaskManager;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;

public class HttpTaskServer {
    private static int PORT = 8080;
    //private static TaskManager manager; //включено чтение и сохранение на диск


    public static void main(String[] args) throws IOException {
        //Files.delete(Paths.get("FileBackedTaskManager.csv"));
        TaskManager manager = Managers.getDefault(); //включено чтение и сохранение на диск

        HttpServer httpServer = HttpServer.create(new InetSocketAddress(PORT), 0);
        TasksHandler tasksHandler = new TasksHandler(manager);
        httpServer.createContext("/tasks",tasksHandler);
        httpServer.createContext("/subtasks",tasksHandler);
        httpServer.createContext("/epics",tasksHandler);
        httpServer.createContext("/history",tasksHandler);
        httpServer.createContext("/prioritized",tasksHandler);
        httpServer.start();

        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");
    }



}
