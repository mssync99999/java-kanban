package http;

import com.google.gson.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controllers.TaskManager;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class BaseHttpHandler implements HttpHandler {
    protected TaskManager manager;
    protected Gson gson;
    protected String typeTicket;
    protected String nameTicket;
    protected String descTicket;
    protected String statusTicket;
    protected int durationMinutes;

    protected LocalDateTime startTime; //
    protected Integer parentEpicId;

    public BaseHttpHandler(TaskManager manager) {
        this.manager = manager;
        this.gson = manager.getGson();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();


        int idQueryInt = -1;

        try {
            String[] idQueryStr = path.split("/");
            System.out.println("idQueryStr.length=" + idQueryStr.length);
            idQueryInt = Integer.parseInt(idQueryStr[2]);

        } catch (NumberFormatException e) {
            idQueryInt = -1;
        } catch (ArrayIndexOutOfBoundsException e) {
            idQueryInt = 0;
        }


        // считываем тело запроса и преобразуем в строку
        InputStream inputStream = exchange.getRequestBody();
        String bodyRequest = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

        //очищаем поля, затем парсим тело, если Json
        this.typeTicket = null;
        this.nameTicket = null;
        this.descTicket = null;
        this.statusTicket = null;
        this.durationMinutes = 0;

        this.startTime = null; //
        this.parentEpicId = null;

        if (JsonParser.parseString(bodyRequest).isJsonObject()) {
            parseRequestJson(bodyRequest);
        }

        switch (method) {
            case "GET":
                executeGET(idQueryInt, exchange);
                break;
            case "POST":
                executePOST(idQueryInt, exchange);
                break;
            case "DELETE":
                executeDELETE(idQueryInt, exchange);
                break;
            default:

        }


    }

    public void executeGET(int idQueryInt, HttpExchange exchange) throws IOException {
    }

    public void executePOST(int idQueryInt, HttpExchange exchange) throws IOException {
    }

    public void executeDELETE(int idQueryInt, HttpExchange exchange) throws IOException {
    }

    public void parseRequestJson(String bodyRequest) {
        JsonElement jsonElement = JsonParser.parseString(bodyRequest);
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        HashMap<String, Object> map = new Gson().fromJson(jsonElement, HashMap.class);

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
