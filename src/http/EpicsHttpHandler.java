package http;

import com.sun.net.httpserver.HttpExchange;
import controllers.TaskManager;
import tickets.Epic;

import tickets.Subtask;


import java.io.IOException;

import java.util.ArrayList;

public class EpicsHttpHandler extends BaseHttpHandler {

    public EpicsHttpHandler(TaskManager manager) {
        super(manager);
    }


    @Override
    public void executeGET(int idQueryInt, HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();

        if (idQueryInt == 0) {
            // =>/epics GET
            ArrayList<Epic> res = manager.getEpics();
            sendText(exchange, gson.toJson(res.toString()), 200);
        }

        if (idQueryInt > 0 && !path.contains("subtasks")) {
            // =>/epics/{id} GET
            Epic res = manager.getIdEpic(idQueryInt);
            if (res == null) {
                sendText(exchange, "Тикет не найден", 404);
            } else {
                sendText(exchange, gson.toJson(res.toString()), 200);
            }
        }

        if (idQueryInt > 0 && path.contains("subtasks")) {
            // =>/epics/{id}/subtasks GET
            Epic e = manager.getIdEpic(idQueryInt);
            if (e == null) {
                sendText(exchange, "Тикет не найден", 404);
            } else {
                ArrayList<Subtask> res = manager.getSubtaskOfEpic(e);
                sendText(exchange, gson.toJson(res.toString()), 200);
            }
        }


    }

    @Override
    public void executePOST(int idQueryInt, HttpExchange exchange) throws IOException {
        if (idQueryInt == 0) {
            // =>/epics POST
            Epic newTicket = new Epic(this.typeTicket, this.nameTicket, this.descTicket); //+
            manager.createEpic(newTicket);
            if (newTicket.getIdTicket() > 0) {
                sendText(exchange, gson.toJson("Создан новый тикет"), 201);
            } else {
                sendText(exchange, gson.toJson("Ошибка. Пересечение с другим тикетом"), 406);
            }
        }


    }

    @Override
    public void executeDELETE(int idQueryInt, HttpExchange exchange) throws IOException {
        if (idQueryInt > 0) {
            manager.killIdEpic(idQueryInt);
            sendText(exchange, gson.toJson("Тикет удалён"), 200);
        }
    }



}
