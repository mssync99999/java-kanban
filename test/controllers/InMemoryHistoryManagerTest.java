package controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tickets.Epic;
import tickets.Status;
import tickets.Task;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    public TaskManager managerForTest;

    @BeforeEach
    public void beforeEach() throws IOException {
        Files.delete(Paths.get("FileBackedTaskManager.csv"));
        managerForTest = Managers.getDefault();

    }
    //убедитесь, что задачи, добавляемые в HistoryManager, сохраняют предыдущую версию задачи и её данных.
    @Test
    void addHistoryTest() {
        Task t1 = new Task("Task","Сравниваем таски","Создаём первый task", Status.NEW, Duration.ofMinutes(35), LocalDateTime.of(2024, 6, 1, 11, 1, 33 ));
        managerForTest.createTask(t1);
        Task t2 = new Task("Task","Сравниваем таски","Создаём второй task", Status.DONE, Duration.ofMinutes(35), LocalDateTime.of(2024, 6, 1, 11, 1, 33 ));
        managerForTest.createTask(t2);

        managerForTest.getIdTask(t1.getIdTicket()); //имитация просмотра тикета
        managerForTest.getIdTask(t2.getIdTicket()); //имитация просмотра тикета


        assertTrue(managerForTest.getHistory().size() == 2);
    }


}