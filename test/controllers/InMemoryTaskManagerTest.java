package controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import controllers.Managers;
import controllers.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import tickets.*;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
    public Epic epicForTest;
    public Subtask subtaskForTest;
    public Task taskForTest;
    public TaskManager managerForTest;

    //создаём тестовый набо тикетов для проверки базовых методом
    @BeforeEach
    public void beforeEach() throws IOException {
        Files.delete(Paths.get("FileBackedTaskManager.csv"));
        //epic = new Epic("Epic 1", "description Epic 1");
        managerForTest = Managers.getDefault();
        epicForTest = new Epic("Epic","Создаём Epic для теста","выполнить перед каждым тестом");
        managerForTest.createEpic(epicForTest); //регистрируем новый эпик

        subtaskForTest = new Subtask("Subtask","Создаём Subtask  для теста","выполнить перед каждым тестом", Status.NEW, epicForTest);
        managerForTest.createSubtask(subtaskForTest);

        taskForTest = new Task("Task","Сравниваем Task  для теста","выполнить перед каждым тестом", Status.NEW);
        managerForTest.createTask(taskForTest);
    }


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

    //проверка очистки тасков
    @Test
    void clearTasksTest() {
        managerForTest.clearTasks();
        assertTrue(managerForTest.getTasks().size() == 0);
    }

    //проверка очистки эпиков
    @Test
    void clearEpicsTest() {
        managerForTest.clearEpics();
        assertTrue(managerForTest.getEpics().size() == 0);
    }

    //проверка очистки сабтасков
    @Test
    void clearSubTasksTest() {
        managerForTest.clearSubTasks();
        assertTrue(managerForTest.getSubtasks().size() == 0);
    }

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


}