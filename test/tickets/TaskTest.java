package tickets;


import controllers.*;
import org.junit.jupiter.api.BeforeAll;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


class TaskTest {
    public static TaskManager managerForTest;

    @BeforeAll
    public static void beforeAll() {
        managerForTest = Managers.getDefault();
    }

    //проверьте, что экземпляры класса Task равны друг другу, если равен их id;
    @Test
    void testEquals() {
        Task t = new Task("Task","Сравниваем таски","Создаём первый task", Status.NEW, Duration.ofMinutes(35), LocalDateTime.of(2024, 6, 1, 11, 1, 33 ));
        managerForTest.createTask(t);
        Task tt = new Task("Task","Сравниваем таски","Создаём второй task", Status.DONE, Duration.ofMinutes(35), LocalDateTime.of(2024, 6, 1, 11, 1, 33 ));
        managerForTest.createTask(tt);

        assertEquals(t, t);
        assertNotEquals(t, tt);
    }




}
