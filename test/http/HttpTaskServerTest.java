package http;
import com.sun.net.httpserver.HttpServer;
import controllers.Managers;
import controllers.TaskManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import tickets.Epic;
import tickets.Status;
import tickets.Subtask;
import tickets.Task;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class HttpTaskServerTest {
    public HttpServer httpServer;
    public Epic epicForTest1;
    public Subtask subtaskForTest1;
    public Task taskForTest1;
    public Epic epicForTest2;
    public Subtask subtaskForTest2;
    public Task taskForTest2;

    public TaskManager managerForTest;

    @BeforeEach
    public void beforeEach() throws IOException {
        Files.delete(Paths.get("FileBackedTaskManager.csv"));
        //epic = new Epic("Epic 1", "description Epic 1");
        managerForTest = Managers.getDefault();

        httpServer = HttpServer.create(new InetSocketAddress(8080), 0);
        TasksHandler tasksHandler = new TasksHandler(managerForTest);
        httpServer.createContext("/tasks",tasksHandler);
        httpServer.createContext("/subtasks",tasksHandler);
        httpServer.createContext("/epics",tasksHandler);
        httpServer.createContext("/history",tasksHandler);
        httpServer.createContext("/prioritized",tasksHandler);
        httpServer.start();

        System.out.println("тестовый HTTP-сервер запущен !");


        taskForTest1 = new Task("Task","Создаём Task1","Создаём Task1 перед каждым тестом", Status.NEW, Duration.ofMinutes(35), LocalDateTime.of(2024, 7, 1, 11, 1, 33 ));
        managerForTest.createTask(taskForTest1);

        taskForTest2 = new Task("Task","Создаём Task2","Создаём Task2 перед каждым тестом", Status.NEW, Duration.ofMinutes(35), LocalDateTime.of(2024, 7, 1, 11, 1, 33 ));
        managerForTest.createTask(taskForTest2);

        epicForTest1 = new Epic("Epic","Создаём Epic1","Создаём Epic1 перед каждым тестом");
        managerForTest.createEpic(epicForTest1); //регистрируем новый эпик

        subtaskForTest1 = new Subtask("Subtask","Создаём Subtask1","Создаём Subtask1 перед каждым тестом", Status.NEW, Duration.ofMinutes(35), LocalDateTime.of(2024, 6, 1, 11, 1, 33 ), epicForTest1);
        managerForTest.createSubtask(subtaskForTest1);

        subtaskForTest2 = new Subtask("Subtask","Создаём Subtask2","Создаём Subtask2 перед каждым тестом", Status.NEW, Duration.ofMinutes(35), LocalDateTime.of(2024, 6, 1, 11, 1, 33 ), epicForTest1);
        managerForTest.createSubtask(subtaskForTest2);

        epicForTest2 = new Epic("Epic","Создаём Epic2","Создаём пустой Epic2 перед каждым тестом");
        managerForTest.createEpic(epicForTest2); //регистрируем новый эпик

    }

    @AfterEach
    public void shutDown() {
        httpServer.stop(1);
        System.out.println("тестовый HTTP-сервер остановлен !");
    }

    //Test 1
    @Test
    public void getTaskAllTest() throws IOException, InterruptedException {
        // создаём HTTP-клиент и запрос
        HttpClient client = HttpClient.newHttpClient();

        String requestText = ""; //body Json

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tasks"))   //endpoint
                .GET()   //method POST(HttpRequest.BodyPublishers.ofString(requestText))
                .build();

        // вызываем рест, отвечающий за создание задач
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // проверяем код ответа
        assertEquals(200, response.statusCode());

    }

    //Test 2
    @Test
    public void getTaskIdTest() throws IOException, InterruptedException {
        // создаём HTTP-клиент и запрос
        HttpClient client = HttpClient.newHttpClient();

        String requestText = ""; //body Json

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tasks/1"))   //endpoint
                .GET()   //method POST(HttpRequest.BodyPublishers.ofString(requestText))
                .build();

        // вызываем рест, отвечающий за создание задач
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // проверяем код ответа
        assertEquals(200, response.statusCode());


    }

    //Test 3
    @Test
    public void postTaskNewTest() throws IOException, InterruptedException {
        // создаём HTTP-клиент и запрос
        HttpClient client = HttpClient.newHttpClient();

        String requestText = " {\n" +
                "\t\"typeTicket\": \"Task\",\n" +
                "  \"nameTicket\": \"Проверка Insomnia_2\",\n" +
                "  \"descTicket\": \"Проверить POST http://localhost:8080/tasks/id\",\n" +
                "  \"statusTicket\": \"NEW\",\n" +
                "  \"startTime\": \"2024-02-02T13:01:02\",\n" +
                "  \"duration\": 200\n" +
                "}"; //body Json

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tasks"))   //endpoint
                .POST(HttpRequest.BodyPublishers.ofString(requestText))   //method POST(HttpRequest.BodyPublishers.ofString(requestText))
                .build();

        // вызываем рест, отвечающий за создание задач
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // проверяем код ответа
        assertEquals(201, response.statusCode());
    }

    //Test 4
    @Test
    public void postTaskUpdateTest() throws IOException, InterruptedException {
        // создаём HTTP-клиент и запрос
        HttpClient client = HttpClient.newHttpClient();

        String requestText = "{\n" +
                "\t\"typeTicket\": \"Task\",\n" +
                "  \"nameTicket\": \"Изменим название\",\n" +
                "  \"descTicket\": \"Проверить POST http://localhost:8080/tasks/id\",\n" +
                "  \"statusTicket\": \"NEW\",\n" +
                "  \"startTime\": \"2023-02-02T10:01:02\",\n" +
                "  \"duration\": 20\n" +
                "}"; //body Json

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tasks/1"))   //endpoint
                .POST(HttpRequest.BodyPublishers.ofString(requestText))   //method POST(HttpRequest.BodyPublishers.ofString(requestText))
                .build();

        // вызываем рест, отвечающий за создание задач
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // проверяем код ответа
        assertEquals(201, response.statusCode());
    }

    //Test 5
    @Test
    public void deleteTaskIdTest() throws IOException, InterruptedException {
        // создаём HTTP-клиент и запрос
        HttpClient client = HttpClient.newHttpClient();

        String requestText = ""; //body Json

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tasks/1"))   //endpoint
                .DELETE()   //method POST(HttpRequest.BodyPublishers.ofString(requestText))
                .build();

        // вызываем рест, отвечающий за создание задач
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // проверяем код ответа
        assertEquals(200, response.statusCode());
    }


    //Test 6
    @Test
    public void getSubtaskAllTest() throws IOException, InterruptedException {
        // создаём HTTP-клиент и запрос
        HttpClient client = HttpClient.newHttpClient();

        String requestText = ""; //body Json

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/subtasks"))   //endpoint
                .GET()   //method POST(HttpRequest.BodyPublishers.ofString(requestText))
                .build();

        // вызываем рест, отвечающий за создание задач
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // проверяем код ответа
        assertEquals(200, response.statusCode());

    }

    //Test 7
    @Test
    public void getSubtaskIdTest() throws IOException, InterruptedException {
        // создаём HTTP-клиент и запрос
        HttpClient client = HttpClient.newHttpClient();

        String requestText = ""; //body Json

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/subtasks/4"))   //endpoint
                .GET()   //method POST(HttpRequest.BodyPublishers.ofString(requestText))
                .build();

        // вызываем рест, отвечающий за создание задач
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // проверяем код ответа
        assertEquals(200, response.statusCode());
    }

    //Test 8
    @Test
    public void postSubtaskNewTest() throws IOException, InterruptedException {
        // создаём HTTP-клиент и запрос
        HttpClient client = HttpClient.newHttpClient();

        String requestText = " {\n" +
                "\t\"typeTicket\": \"Subtask\",\n" +
                "  \"nameTicket\": \"Создаём новый Subtask через Insomnia\",\n" +
                "  \"descTicket\": \"Проверить POST http://localhost:8080/subtasks\",\n" +
                "  \"statusTicket\": \"NEW\",\n" +
                "  \"startTime\": \"2023-01-02T13:01:02\",\n" +
                "  \"duration\": 100,\n" +
                "\t\"parentEpicId\": 3\n" +
                "}"; //body Json

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/subtasks"))   //endpoint
                .POST(HttpRequest.BodyPublishers.ofString(requestText))   //method POST(HttpRequest.BodyPublishers.ofString(requestText))
                .build();

        // вызываем рест, отвечающий за создание задач
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // проверяем код ответа
        assertEquals(201, response.statusCode());
    }

    //Test 9
    @Test
    public void postSubtaskUpdateTest() throws IOException, InterruptedException {
        // создаём HTTP-клиент и запрос
        HttpClient client = HttpClient.newHttpClient();

        String requestText = " {\n" +
                "\t\"typeTicket\": \"Subtask\",\n" +
                "  \"nameTicket\": \"Изменяем Subtask через Insomnia\",\n" +
                "  \"descTicket\": \"Проверить POST http://localhost:8080/subtasks/id\",\n" +
                "  \"statusTicket\": \"NEW\",\n" +
                "  \"startTime\": \"2024-01-02T13:01:02\",\n" +
                "  \"duration\": 200,\n" +
                "\t\"parentEpicId\": 3\n" +
                "}"; //body Json

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/subtasks/4"))   //endpoint
                .POST(HttpRequest.BodyPublishers.ofString(requestText))   //method POST(HttpRequest.BodyPublishers.ofString(requestText))
                .build();

        // вызываем рест, отвечающий за создание задач
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // проверяем код ответа
        assertEquals(201, response.statusCode());
    }

    //Test 10
    @Test
    public void deleteSubtaskIdTest() throws IOException, InterruptedException {
        // создаём HTTP-клиент и запрос
        HttpClient client = HttpClient.newHttpClient();

        String requestText = ""; //body Json

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/subtasks/5"))   //endpoint
                .DELETE()   //method POST(HttpRequest.BodyPublishers.ofString(requestText))
                .build();

        // вызываем рест, отвечающий за создание задач
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // проверяем код ответа
        assertEquals(200, response.statusCode());
    }



    //Test 11
    @Test
    public void getEpicAllTest() throws IOException, InterruptedException {
        // создаём HTTP-клиент и запрос
        HttpClient client = HttpClient.newHttpClient();

        String requestText = ""; //body Json

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/epics"))   //endpoint
                .GET()   //method POST(HttpRequest.BodyPublishers.ofString(requestText))
                .build();

        // вызываем рест, отвечающий за создание задач
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // проверяем код ответа
        assertEquals(200, response.statusCode());

    }

    //Test 12
    @Test
    public void getEpicIdTest() throws IOException, InterruptedException {
        // создаём HTTP-клиент и запрос
        HttpClient client = HttpClient.newHttpClient();

        String requestText = ""; //body Json

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/epics/3"))   //endpoint
                .GET()   //method POST(HttpRequest.BodyPublishers.ofString(requestText))
                .build();

        // вызываем рест, отвечающий за создание задач
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // проверяем код ответа
        assertEquals(200, response.statusCode());
    }

    //Test 13
    @Test
    public void postEpicNewTest() throws IOException, InterruptedException {
        // создаём HTTP-клиент и запрос
        HttpClient client = HttpClient.newHttpClient();

        String requestText = " {\n" +
                "\t\"typeTicket\": \"Epic\",\n" +
                "  \"nameTicket\": \"Создание нового эпика через Insomnia\",\n" +
                "  \"descTicket\": \"Проверить POST http://localhost:8080/epics\"\n" +
                "}"; //body Json

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/epics"))   //endpoint
                .POST(HttpRequest.BodyPublishers.ofString(requestText))   //method POST(HttpRequest.BodyPublishers.ofString(requestText))
                .build();

        // вызываем рест, отвечающий за создание задач
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // проверяем код ответа
        assertEquals(201, response.statusCode());
    }

    //Test 14 /epics/{id}/subtasks GET
    @Test
    public void getSubtasksOfEpicTest() throws IOException, InterruptedException {
        // создаём HTTP-клиент и запрос
        HttpClient client = HttpClient.newHttpClient();

        String requestText = ""; //body Json

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/epics/3/subtasks"))   //endpoint
                .GET()   //method POST(HttpRequest.BodyPublishers.ofString(requestText))
                .build();

        // вызываем рест, отвечающий за создание задач
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // проверяем код ответа
        assertEquals(200, response.statusCode());
    }

    //Test 15
    @Test
    public void deleteEpicIdTest() throws IOException, InterruptedException {
        // создаём HTTP-клиент и запрос
        HttpClient client = HttpClient.newHttpClient();

        String requestText = ""; //body Json

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/epics/3"))   //endpoint
                .DELETE()   //method POST(HttpRequest.BodyPublishers.ofString(requestText))
                .build();

        // вызываем рест, отвечающий за создание задач
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // проверяем код ответа
        assertEquals(200, response.statusCode());
    }

    //Test 16
    @Test
    public void getHistoryTest() throws IOException, InterruptedException {
        // создаём HTTP-клиент и запрос
        HttpClient client = HttpClient.newHttpClient();

        String requestText = ""; //body Json

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/history"))   //endpoint
                .GET()   //method POST(HttpRequest.BodyPublishers.ofString(requestText))
                .build();

        // вызываем рест, отвечающий за создание задач
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // проверяем код ответа
        assertEquals(200, response.statusCode());
    }


    //Test 17
    @Test
    public void getPrioritizedTest() throws IOException, InterruptedException {
        // создаём HTTP-клиент и запрос
        HttpClient client = HttpClient.newHttpClient();

        String requestText = ""; //body Json

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/prioritized"))   //endpoint
                .GET()   //method POST(HttpRequest.BodyPublishers.ofString(requestText))
                .build();

        // вызываем рест, отвечающий за создание задач
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // проверяем код ответа
        assertEquals(200, response.statusCode());
    }

}
