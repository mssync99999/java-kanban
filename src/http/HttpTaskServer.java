package http;

import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;


import controllers.Managers;
import controllers.TaskManager;

import java.io.IOException;



public class HttpTaskServer {
    private static int PORT = 8080;


    public static void main(String[] args) throws IOException {
        TaskManager manager = Managers.getDefault(); //включено чтение и сохранение на диск

        HttpServer httpServer = HttpServer.create(new InetSocketAddress(PORT), 0);
/* оставил часть старого кода для наглядности было - стало
        TasksHandler tasksHandler = new TasksHandler(manager);
        httpServer.createContext("/tasks",tasksHandler);
        httpServer.createContext("/subtasks",tasksHandler);
        httpServer.createContext("/epics",tasksHandler);
        httpServer.createContext("/history",tasksHandler);
        httpServer.createContext("/prioritized",tasksHandler);
*/

        httpServer.createContext("/tasks", new TasksHttpHandler(manager));
        httpServer.createContext("/tasks",new SubtasksHttpHandler(manager));
        httpServer.createContext("/epics",new EpicsHttpHandler(manager));
        httpServer.createContext("/history",new HistoryHttpHandler(manager));
        httpServer.createContext("/prioritized",new PrioritizedHttpHandler(manager));

        httpServer.start();

        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");
    }



}
