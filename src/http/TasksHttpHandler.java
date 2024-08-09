package http;

import com.sun.net.httpserver.HttpExchange;
import controllers.TaskManager;
import tickets.Status;
import tickets.Task;

import java.time.Duration;
import java.util.ArrayList;

import java.io.IOException;

public class TasksHttpHandler extends BaseHttpHandler {

    public TasksHttpHandler(TaskManager manager) {
        super(manager);
    }


    @Override
    public void executeGET(int idQueryInt, HttpExchange exchange) throws IOException {
        if (idQueryInt == 0) {
            // =>/tasks GET
            ArrayList<Task> res = manager.getTasks();
            sendText(exchange, gson.toJson(res.toString()), 200);
        }

        if (idQueryInt > 0) {
            // =>/tasks/{id} GET
            Task res = manager.getIdTask(idQueryInt);
            if (res == null) {
                sendText(exchange, "Тикет не найден", 404);
            } else {
                sendText(exchange, gson.toJson(res.toString()), 200);
            }
        }


    }

    @Override
    public void executePOST(int idQueryInt, HttpExchange exchange) throws IOException {
        if (idQueryInt == 0) {
            // =>/tasks POST
            Task newTicket = new Task(this.typeTicket, this.nameTicket, this.descTicket, Status.NEW, Duration.ofMinutes(this.durationMinutes), this.startTime); //+
            manager.createTask(newTicket);
            if (newTicket.getIdTicket() > 0) {
                sendText(exchange, gson.toJson("Создан новый тикет"), 201);
            } else {
                sendText(exchange, gson.toJson("Ошибка. Пересечение с другим тикетом"), 406);
            }
        }

        if (idQueryInt > 0) {
            // =>/tasks/{id} POST
            Task upd = new Task(this.typeTicket, this.nameTicket, this.descTicket, Status.NEW, Duration.ofMinutes(this.durationMinutes), this.startTime);
            manager.updateTask(upd, manager.getIdTask(idQueryInt));
            if (upd.getIdTicket() > 0) {
                sendText(exchange, gson.toJson("Обновили тикет"), 201);
            } else {
                sendText(exchange, gson.toJson("Ошибка. Пересечение с другим тикетом"), 406);
            }
        }

    }

    @Override
    public void executeDELETE(int idQueryInt, HttpExchange exchange) throws IOException {
        //tasks
        if (idQueryInt > 0) {
            manager.killIdTask(idQueryInt);
            sendText(exchange, gson.toJson("Тикет удалён"), 200);
        }
    }



}
