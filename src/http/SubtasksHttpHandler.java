package http;

import com.sun.net.httpserver.HttpExchange;
import controllers.TaskManager;
import tickets.Status;
import tickets.Subtask;
import tickets.Task;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;

public class SubtasksHttpHandler extends BaseHttpHandler {

    public SubtasksHttpHandler(TaskManager manager) {
        super(manager);
    }



    @Override
    public void executeGET(int idQueryInt, HttpExchange exchange) throws IOException {

        if (idQueryInt == 0) {
            // =>/subtasks GET
            ArrayList<Subtask> res = manager.getSubtasks();
            sendText(exchange, gson.toJson(res.toString()), 200);
        }

        if (idQueryInt > 0) {
            // =>/subtasks/{id} GET
            Task res = manager.getIdSubtask(idQueryInt);
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
            // =>/subtasks POST
            Subtask newTicket = new Subtask(this.typeTicket, this.nameTicket, this.descTicket, Status.NEW, Duration.ofMinutes(this.durationMinutes), this.startTime, manager.getIdEpic(this.parentEpicId)); //+
            manager.createSubtask(newTicket);
            if (newTicket.getIdTicket() > 0) {
                sendText(exchange, gson.toJson("Создан новый тикет"), 201);
            } else {
                sendText(exchange, gson.toJson("Ошибка. Пересечение с другим тикетом"), 406);
            }
        }


        if (idQueryInt > 0) {
            // =>/subtasks/{id} POST
            Subtask upd = new Subtask(this.typeTicket, this.nameTicket, this.descTicket, Status.NEW, Duration.ofMinutes(this.durationMinutes), this.startTime, manager.getIdEpic(this.parentEpicId));
            manager.updateSubtask(upd, manager.getIdSubtask(idQueryInt));
            if (upd.getIdTicket() > 0) {
                sendText(exchange, gson.toJson("Обновили тикет"), 201);
            } else {
                sendText(exchange, gson.toJson("Ошибка. Пересечение с другим тикетом"), 406);
            }
        }



    }

    @Override
    public void executeDELETE(int idQueryInt, HttpExchange exchange) throws IOException {
        if (idQueryInt > 0) {
            manager.killIdSubtask(idQueryInt);
            sendText(exchange, gson.toJson("Тикет удалён"), 200);
        }
    }



}
