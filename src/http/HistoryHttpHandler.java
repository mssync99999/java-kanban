package http;

import com.sun.net.httpserver.HttpExchange;
import controllers.TaskManager;

import tickets.Task;

import java.io.IOException;

import java.util.List;

public class HistoryHttpHandler extends BaseHttpHandler {

    public HistoryHttpHandler(TaskManager manager) {
        super(manager);
    }

    @Override
    public void executeGET(int idQueryInt, HttpExchange exchange) throws IOException {
        if (idQueryInt == 0) {
            // =>/history GET
            List<Task> res = manager.getHistory();
            sendText(exchange, gson.toJson(res.toString()), 200);
        }

    }




}
