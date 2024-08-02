package controllers;

import org.junit.jupiter.api.Test;
import java.io.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileBackedTasksManagerTest extends TasksManagerTest<FileBackedTaskManager> {

    @Test
    void loadFromFileTest() throws  IOException {
        FileBackedTaskManager.loadFromFile("FileBackedTaskManager.csv");
        assertTrue(managerForTest.getTasks().getFirst().getNameTicket().equals("Сравниваем Task  для теста"));
        assertTrue(managerForTest.getEpics().getFirst().getNameTicket().equals("Создаём Epic для теста"));
        assertTrue(managerForTest.getSubtasks().getFirst().getNameTicket().equals("Создаём Subtask  для теста"));


    }

}
