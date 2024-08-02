package tickets;

import controllers.Managers;
import controllers.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SubtaskTest {
    public Epic epicForTest;
    public TaskManager managerForTest;

    @BeforeEach
    public void beforeEach() {

        managerForTest = Managers.getDefault();
        epicForTest = new Epic("Epic","Создаём эпик для теста","выполнить перед каждым тестом");
        managerForTest.createEpic(epicForTest); //регистрируем новый эпик
    }


    @Test
    void addEpic() {
        Subtask t = new Subtask("Subtask","Создаём Subtask","Создаём единственный Subtask в эпике", Status.NEW, Duration.ofMinutes(35), LocalDateTime.of(2024, 6, 1, 11, 1, 33 ), epicForTest);
        managerForTest.createSubtask(t);
        assertEquals(epicForTest, t.getParentEpic());
    }


}