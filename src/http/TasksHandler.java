package http;

import com.google.gson.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controllers.TaskManager;
import tickets.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TasksHandler implements HttpHandler {
    private TaskManager manager;
    private String typeTicket; // = jsonObject.get("typeTicket").getAsString();
    private String nameTicket; // = jsonObject.get("nameTicket").getAsString();
    private String descTicket; // = jsonObject.get("descTicket").getAsString();
    private String statusTicket; // = jsonObject.get("statusTicket").getAsString();
    private int durationMinutes; // = jsonObject.get("duration").getAsInt();
    //String startTimeString = jsonObject.get("startTime").getAsString();
    private LocalDateTime startTime; //
    private Integer parentEpicId;

    public TasksHandler(TaskManager manager) {
        this.manager = manager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();

        String endpoint = path.split("/")[1];
        int idQueryInt = -1;

        try {
            String[] idQueryStr = path.split("/");
            System.out.println("idQueryStr.length=" + idQueryStr.length);
            idQueryInt = Integer.parseInt(idQueryStr[2]);
            //endpoint = idQueryStr[1];
        } catch (NumberFormatException e) {
            idQueryInt = -1;
        } catch (ArrayIndexOutOfBoundsException e) {
            idQueryInt = 0;
        }

        System.out.println("method: " + method);
        System.out.println("path: " + path);
        System.out.println("endpoint: " + endpoint);
        System.out.println("query: " + idQueryInt);

        Gson gson = new GsonBuilder()
                .serializeNulls()
                .setPrettyPrinting()
                .create(); // завершаем построение объекта

        // считываем тело запроса и преобразуем в строку
        InputStream inputStream = exchange.getRequestBody();
        String bodyRequest = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

        //очищаем поля, затем парсим тело, если Json
        this.typeTicket = null; // = jsonObject.get("typeTicket").getAsString();
        this.nameTicket = null; // = jsonObject.get("nameTicket").getAsString();
        this.descTicket = null; // = jsonObject.get("descTicket").getAsString();
        this.statusTicket = null; // = jsonObject.get("statusTicket").getAsString();
        this.durationMinutes = 0; // = jsonObject.get("duration").getAsInt();
        //String startTimeString = jsonObject.get("startTime").getAsString();
        this.startTime = null; //
        this.parentEpicId = null;

        if (JsonParser.parseString(bodyRequest).isJsonObject()) {
            parseRequestJson(bodyRequest);
        }

//tasks
        if (endpoint.equals("tasks") && method.equals("GET") && idQueryInt == 0) {
            // =>/tasks GET
            ArrayList<Task> res = manager.getTasks();
            sendText(exchange, gson.toJson(res.toString()), 200);
        } else if (endpoint.equals("tasks") && method.equals("GET") && idQueryInt > 0) {
            // =>/tasks/{id} GET
            Task res = manager.getIdTask(idQueryInt);
            if (res == null) {
                sendText(exchange, "Тикет не найден", 404);
            } else {
                sendText(exchange, gson.toJson(res.toString()), 200);
            }
        } else if (endpoint.equals("tasks") && method.equals("POST") && idQueryInt == 0) {
            // =>/tasks POST
            Task newTicket = new Task(this.typeTicket, this.nameTicket, this.descTicket, Status.NEW, Duration.ofMinutes(this.durationMinutes), this.startTime); //+
            manager.createTask(newTicket);
            if (newTicket.getIdTicket() > 0) {
                sendText(exchange, gson.toJson("Создан новый тикет"), 201);
            } else {
                sendText(exchange, gson.toJson("Ошибка. Пересечение с другим тикетом"), 406);
            }
        } else if (endpoint.equals("tasks") && method.equals("POST") && idQueryInt > 0) {
            // =>/tasks/{id} POST
            Task upd = new Task(this.typeTicket, this.nameTicket, this.descTicket, Status.NEW, Duration.ofMinutes(this.durationMinutes), this.startTime);
            manager.updateTask(upd, manager.getIdTask(idQueryInt));
            if (upd.getIdTicket() > 0) {
                sendText(exchange, gson.toJson("Обновили тикет"), 201);
            } else {
                sendText(exchange, gson.toJson("Ошибка. Пересечение с другим тикетом"), 406);
            }
        } else if (endpoint.equals("tasks") && method.equals("DELETE") && idQueryInt > 0) {
            manager.killIdTask(idQueryInt);
            sendText(exchange, gson.toJson("Тикет удалён"), 200);
        }

//subtasks
        if (endpoint.equals("subtasks") && method.equals("GET") && idQueryInt == 0) {
            // =>/subtasks GET
            ArrayList<Subtask> res = manager.getSubtasks();
            sendText(exchange, gson.toJson(res.toString()), 200);
        } else if (endpoint.equals("subtasks") && method.equals("GET") && idQueryInt > 0) {
            // =>/subtasks/{id} GET
            Task res = manager.getIdSubtask(idQueryInt);
            if (res == null) {
                sendText(exchange, "Тикет не найден", 404);
            } else {
                sendText(exchange, gson.toJson(res.toString()), 200);
            }
        } else if (endpoint.equals("subtasks") && method.equals("POST") && idQueryInt == 0) {
            // =>/subtasks POST
            Subtask newTicket = new Subtask(this.typeTicket, this.nameTicket, this.descTicket, Status.NEW, Duration.ofMinutes(this.durationMinutes), this.startTime, manager.getIdEpic(this.parentEpicId)); //+
            manager.createSubtask(newTicket);
            if (newTicket.getIdTicket() > 0) {
                sendText(exchange, gson.toJson("Создан новый тикет"), 201);
            } else {
                sendText(exchange, gson.toJson("Ошибка. Пересечение с другим тикетом"), 406);
            }
        } else if (endpoint.equals("subtasks") && method.equals("POST") && idQueryInt > 0) {
            // =>/subtasks/{id} POST
            Subtask upd = new Subtask(this.typeTicket, this.nameTicket, this.descTicket, Status.NEW, Duration.ofMinutes(this.durationMinutes), this.startTime, manager.getIdEpic(this.parentEpicId));
            manager.updateSubtask(upd, manager.getIdSubtask(idQueryInt));
            if (upd.getIdTicket() > 0) {
                sendText(exchange, gson.toJson("Обновили тикет"), 201);
            } else {
                sendText(exchange, gson.toJson("Ошибка. Пересечение с другим тикетом"), 406);
            }
        } else if (endpoint.equals("subtasks") && method.equals("DELETE") && idQueryInt > 0) {
            manager.killIdSubtask(idQueryInt);
            sendText(exchange, gson.toJson("Тикет удалён"), 200);
        }

//epics
        if (endpoint.equals("epics") && method.equals("GET") && idQueryInt == 0) {
            // =>/epics GET
            ArrayList<Epic> res = manager.getEpics();
            sendText(exchange, gson.toJson(res.toString()), 200);
        } else if (endpoint.equals("epics") && method.equals("GET") && idQueryInt > 0 && !path.contains("subtasks")) {
            // =>/epics/{id} GET
            Epic res = manager.getIdEpic(idQueryInt);
            if (res == null) {
                sendText(exchange, "Тикет не найден", 404);
            } else {
                sendText(exchange, gson.toJson(res.toString()), 200);
            }
        } else if (endpoint.equals("epics") && method.equals("POST") && idQueryInt == 0) {
            // =>/epics POST
            Epic newTicket = new Epic(this.typeTicket, this.nameTicket, this.descTicket); //+
            manager.createEpic(newTicket);
            if (newTicket.getIdTicket() > 0) {
                sendText(exchange, gson.toJson("Создан новый тикет"), 201);
            } else {
                sendText(exchange, gson.toJson("Ошибка. Пересечение с другим тикетом"), 406);
            }
        } else if (endpoint.equals("epics") && method.equals("GET") && idQueryInt > 0 && path.contains("subtasks")) {
            // =>/epics/{id}/subtasks GET
            Epic e = manager.getIdEpic(idQueryInt);
            if (e == null) {
                sendText(exchange, "Тикет не найден", 404);
            } else {
                ArrayList<Subtask> res = manager.getSubtaskOfEpic(e);
                sendText(exchange, gson.toJson(res.toString()), 200);
            }
        } else if (endpoint.equals("epics") && method.equals("DELETE") && idQueryInt > 0) {
            manager.killIdEpic(idQueryInt);
            sendText(exchange, gson.toJson("Тикет удалён"), 200);
        }

//history
        if (endpoint.equals("history") && method.equals("GET") && idQueryInt == 0) {
            // =>/history GET
            List<Task> res = manager.getHistory();
            sendText(exchange, gson.toJson(res.toString()), 200);
        }

//prioritized
        if (endpoint.equals("prioritized") && method.equals("GET") && idQueryInt == 0) {
            // =>/prioritized GET
            List<Task> res = manager.getPrioritizedTasks();
            sendText(exchange, gson.toJson(res.toString()), 200);
        }

    }





    public void parseRequestJson(String bodyRequest) {
        JsonElement jsonElement = JsonParser.parseString(bodyRequest);
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        HashMap<String, Object> map = new Gson().fromJson(jsonElement, HashMap.class);
        //System.out.println("map=" + map);
        for (String entry: map.keySet()) {
            switch (entry) {
                case "typeTicket":
                    this.typeTicket = jsonObject.get("typeTicket").getAsString();
                    break;
                case "nameTicket":
                    this.nameTicket = jsonObject.get("nameTicket").getAsString();
                    break;
                case "descTicket":
                    this.descTicket = jsonObject.get("descTicket").getAsString();
                    break;
                case "statusTicket":
                    this.statusTicket = jsonObject.get("statusTicket").getAsString();
                    break;
                case "duration":
                    this.durationMinutes = jsonObject.get("duration").getAsInt();
                    break;
                case "startTime":
                    String startTimeString = jsonObject.get("startTime").getAsString();
                    this.startTime = LocalDateTime.parse(startTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
                    break;
                case "parentEpicId":
                    this.parentEpicId = (Integer) jsonObject.get("parentEpicId").getAsInt();
                    break;
                default:
                    //...
                    //break;
            }
        }


    }





    protected void sendText(HttpExchange h, String text, int code) throws IOException {
        byte[] resp = text.getBytes(StandardCharsets.UTF_8);
        h.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        h.sendResponseHeaders(code, resp.length);
        h.getResponseBody().write(resp);
        h.close();
    }
}
