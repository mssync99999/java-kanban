package controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tickets.Epic;
import tickets.Status;
import tickets.Task;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    public TaskManager managerForTest;

    @BeforeEach
    public void beforeEach() {

        managerForTest = Managers.getDefault();

    }
    //убедитесь, что задачи, добавляемые в HistoryManager, сохраняют предыдущую версию задачи и её данных.
    @Test
    void addHistoryTest() {
        Task t1 = new Task("Task","Сравниваем таски","Создаём первый task", Status.NEW);
        managerForTest.createTask(t1);
        Task t2 = new Task("Task","Сравниваем таски","Создаём второй task", Status.DONE);
        managerForTest.createTask(t2);

        managerForTest.getIdTask(t1.getIdTicket()); //имитация просмотра тикета
        managerForTest.getIdTask(t2.getIdTicket()); //имитация просмотра тикета


        assertTrue(managerForTest.getHistory().size() == 2);
    }


}