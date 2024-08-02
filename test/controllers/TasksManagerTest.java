package controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tickets.Epic;
import tickets.Status;
import tickets.Subtask;
import tickets.Task;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

abstract class TasksManagerTest<T extends TaskManager> {
    public Epic epicForTest;
    public Subtask subtaskForTest;
    public Task taskForTest;
    public TaskManager managerForTest;

    ////создаём тестовый набо тикетов для проверки базовых методом
    @BeforeEach
    public void beforeEach() throws IOException {
        Files.delete(Paths.get("FileBackedTaskManager.csv"));
        //epic = new Epic("Epic 1", "description Epic 1");
        managerForTest = Managers.getDefault();
        epicForTest = new Epic("Epic","Создаём Epic для теста","выполнить перед каждым тестом");
        managerForTest.createEpic(epicForTest); //регистрируем новый эпик

        subtaskForTest = new Subtask("Subtask","Создаём Subtask  для теста","выполнить перед каждым тестом", Status.NEW, Duration.ofMinutes(35), LocalDateTime.of(2024, 6, 1, 11, 1, 33 ), epicForTest);
        managerForTest.createSubtask(subtaskForTest);

        taskForTest = new Task("Task","Сравниваем Task  для теста","выполнить перед каждым тестом", Status.NEW, Duration.ofMinutes(35), LocalDateTime.of(2024, 7, 1, 11, 1, 33 ));
        managerForTest.createTask(taskForTest);
    }

    //a. Получение списка всех задач.
    //проверка получения списка тасков
    @Test
    void getTasksTest() {
        assertEquals(taskForTest, managerForTest.getTasks().getFirst());
    }

    //проверка получения списка сабтасков
    @Test
    void getSubtasksTest() {
        assertEquals(subtaskForTest, managerForTest.getSubtasks().getFirst());
    }

    //проверка получения списка эпиков
    @Test
    void getEpicsTest() {
        assertEquals(epicForTest, managerForTest.getEpics().getFirst());
    }

    //b. Удаление всех задач.
    //проверка очистки тасков
    @Test
    void clearTasksTest() {
        managerForTest.clearTasks();
        assertTrue(managerForTest.getTasks().size() == 0);
    }

    ////проверка очистки эпиков
    @Test
    void clearEpicsTest() {
        managerForTest.clearEpics();
        assertTrue(managerForTest.getEpics().size() == 0);
    }

    ////проверка очистки сабтасков
    @Test
    void clearSubTasksTest() {
        managerForTest.clearSubTasks();
        assertTrue(managerForTest.getSubtasks().size() == 0);
    }

    //c. Получение по идентификатору.
    //проверьте, что InMemoryTaskManager
    //действительно добавляет задачи разного типа и может найти их по id;
    @Test
    void getIdTaskTest() {
        assertEquals(taskForTest, managerForTest.getIdTask(3));
    }

    //проверка получения эпика по номеру
    @Test
    void getIdEpicTest() {
        assertEquals(epicForTest, managerForTest.getIdEpic(1));
    }

    //поверка получения сабтаска по номеру
    @Test
    void getIdSubtaskTest() {
        assertEquals(subtaskForTest, managerForTest.getIdSubtask(2));
    }

    //d. Создание. Сам объект должен передаваться в качестве параметра.
    @Test
    void createTaskTest() {
        managerForTest.clearTasks();
        assertTrue(managerForTest.getTasks().size() == 0);
        taskForTest = new Task("Task","Создаём Task  для теста","выполнить перед каждым тестом", Status.NEW, Duration.ofMinutes(35), LocalDateTime.of(2024, 7, 1, 11, 1, 33 ));
        managerForTest.createTask(taskForTest);
        assertTrue(managerForTest.getTasks().size() == 1);
    }

    @Test
    void createEpicTest() {
        managerForTest.clearEpics();
        assertTrue(managerForTest.getEpics().size() == 0);
        epicForTest = new Epic("Epic","Создаём Task  для теста","выполнить перед каждым тестом"); //, Duration.ofMinutes(35), LocalDateTime.of(2024, 7, 1, 11, 1, 33 ));
        managerForTest.createEpic(epicForTest);
        assertTrue(managerForTest.getEpics().size() == 1);

    }

    @Test
    void createSubtaskTest() {
        managerForTest.clearSubTasks();
        assertTrue(managerForTest.getSubtasks().size() == 0);
        subtaskForTest = new Subtask("Task","Создаём Task  для теста","выполнить перед каждым тестом", Status.NEW, Duration.ofMinutes(35), LocalDateTime.of(2024, 7, 1, 11, 1, 33 ), epicForTest);
        managerForTest.createSubtask(subtaskForTest);
        assertTrue(managerForTest.getSubtasks().size() == 1);
    }

    //e. Обновление. Новая версия объекта с верным идентификатором передаётся в виде параметра.
    @Test
    void updateTaskTest() {
        Task upd = new Task("Task","Создаём Task на замену","выполнить перед каждым тестом", Status.NEW, Duration.ofMinutes(35), LocalDateTime.of(2024, 7, 1, 11, 1, 33 ));
        managerForTest.updateTask(upd, taskForTest);
        assertTrue(managerForTest.getTasks().getFirst().getNameTicket().equals("Создаём Task на замену"));
    }

    @Test
    void updateEpicTest() {
        Epic upd = new Epic("Epic","Создаём Epic на замену","выполнить перед каждым тестом");
        managerForTest.updateEpic(upd, epicForTest);
        assertTrue(managerForTest.getEpics().getFirst().getNameTicket().equals("Создаём Epic на замену"));
    }

    @Test
    void updateSubtaskTest() {
        Subtask upd = new Subtask("Subtask","Создаём Subtask на замену","выполнить перед каждым тестом", Status.NEW, Duration.ofMinutes(35), LocalDateTime.of(2023, 6, 1, 11, 1, 33 ), epicForTest);
        managerForTest.updateSubtask(upd, subtaskForTest);
        System.out.println(managerForTest.getSubtasks().getFirst().getNameTicket());
        assertTrue(managerForTest.getSubtasks().getFirst().getNameTicket().equals("Создаём Subtask на замену"));
    }

    //f. Удаление по идентификатору.
    @Test
    void killIdTaskTest() {
        Integer id = managerForTest.getTasks().getFirst().getIdTicket();
        managerForTest.killIdTask(id);
        assertTrue(managerForTest.getTasks().size() == 0);
    }

    @Test
    void killIdEpicTest() {
        Integer id = managerForTest.getEpics().getFirst().getIdTicket();
        managerForTest.killIdEpic(id);
        assertTrue(managerForTest.getEpics().size() == 0);
    }

    @Test
    void killIdSubtaskTest() {
        Integer id = managerForTest.getSubtasks().getFirst().getIdTicket();
        managerForTest.killIdSubtask(id);
        assertTrue(managerForTest.getSubtasks().size() == 0);
    }

    //Дополнительные методы:
    //a. Получение списка всех подзадач определённого эпика.
    @Test
    void getSubtaskOfEpicTest() {
        //epicForTest.getChildSubtasks();
        assertTrue(epicForTest.getChildSubtasks().getFirst().getNameTicket().equals("Создаём Subtask  для теста"));
    }

    //История просмотров задач - 10 последних задач
    @Test
    void getHistoryTest() {
        managerForTest.getIdTask(taskForTest.getIdTicket());
        managerForTest.getIdEpic(epicForTest.getIdTicket());
        managerForTest.getIdSubtask(subtaskForTest.getIdTicket());
        assertTrue(managerForTest.getHistory().size() == 3);

    }

    //+Выводим список задач в порядке приоритета
    @Test
    void getPrioritizedTasksTest() {
        assertEquals(List.of(subtaskForTest,taskForTest), managerForTest.getPrioritizedTasks());
    }


    @Test
    void isIntersectionTest() {
        Task taskForTestOther = new Task("Task2","Сравниваем пересечения Task","выполнить перед каждым тестом", Status.NEW, Duration.ofMinutes(35), LocalDateTime.of(2024, 7, 1, 11, 10, 1 ));
        managerForTest.createTask(taskForTestOther);
        assertTrue(managerForTest.isIntersection(taskForTestOther));
    }


}
