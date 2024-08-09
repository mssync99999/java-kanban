package http;

import com.sun.net.httpserver.HttpExchange;
import controllers.TaskManager;
import tickets.Task;

import java.io.IOException;
import java.util.List;

public class PrioritizedHttpHandler extends BaseHttpHandler {

    public PrioritizedHttpHandler(TaskManager manager) {
        super(manager);
    }

    @Override
    public void executeGET(int idQueryInt, HttpExchange exchange) throws IOException {
        if (idQueryInt == 0) {
            // =>/prioritized GET
            List<Task> res = manager.getPrioritizedTasks();
            sendText(exchange, gson.toJson(res.toString()), 200);
        }

    }

}
