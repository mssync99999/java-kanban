package tickets;

import controllers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;


class EpicTest {
    public Epic epicForTest;
    public TaskManager managerForTest;

    //тестовые эпик и менеджер
    @BeforeEach
    public void beforeEach() {

        managerForTest = Managers.getDefault();
        epicForTest = new Epic("Epic","Создаём эпик для теста","выполнить перед каждым тестом");
        managerForTest.createEpic(epicForTest); //регистрируем новый эпик
    }


    //только что созданный эпик не содержит дочерних субтасков
    @Test
    void getChildSubtasks() {
        ArrayList<Subtask> childSubtasks = epicForTest.getChildSubtasks();
        boolean t = childSubtasks.size() == 0;
        assertTrue(t);
    }

    //создаём субтаск, который привязывается к эпику, после чего проверим равенство ссылок друг на друга(связи)
    @Test
    void addSubtask() {

        Subtask t = new Subtask("Subtask","Создаём Subtask","Создаём единственный Subtask в эпике", Status.NEW, Duration.ofMinutes(35), LocalDateTime.of(2024, 6, 1, 11, 1, 33 ), epicForTest);
        managerForTest.createSubtask(t);
        ArrayList<Subtask> childSubtasks = epicForTest.getChildSubtasks();
        Epic a = childSubtasks.get(0).getParentEpic();
        Epic b = t.getParentEpic();
        assertEquals(a, b);
    }

    //создаём два субтаска с разными статусами(done new) и удаляем один из них (new), поверим влияние статус эпика
    @Test
    void killSubtask() {
        Subtask t = new Subtask("Subtask","Создаём Subtask для удаления","Создаём первый Subtask в эпике", Status.NEW, Duration.ofMinutes(35), LocalDateTime.of(2024, 6, 1, 11, 1, 33 ), epicForTest);
        managerForTest.createSubtask(t);
        Subtask tt = new Subtask("Subtask","Создаём Subtask для удаления","Создаём второй Subtask в эпике", Status.DONE, Duration.ofMinutes(35), LocalDateTime.of(2024, 6, 1, 11, 1, 33 ), epicForTest);
        managerForTest.createSubtask(tt);

        Status statusFirst = epicForTest.getStatusTicket();
        managerForTest.killIdSubtask(t.getIdTicket());
        Status statusSecond = epicForTest.getStatusTicket();

        assertTrue( statusFirst == Status.NEW && statusSecond == Status.DONE);
    }

    //при удалении эпика - удаляются все его сабтаски
    @Test
    void killEpic() {
        Subtask t = new Subtask("Subtask","При удалении эпика - исчезают сабтаски","Создаём первый Subtask в эпике", Status.NEW, Duration.ofMinutes(35), LocalDateTime.of(2024, 6, 1, 11, 1, 33 ), epicForTest);
        managerForTest.createSubtask(t);
        Subtask tt = new Subtask("Subtask","При удалении эпика - исчезают сабтаски","Создаём второй Subtask в эпике", Status.DONE, Duration.ofMinutes(35), LocalDateTime.of(2024, 6, 1, 11, 1, 33 ), epicForTest);
        managerForTest.createSubtask(tt);

        managerForTest.killIdEpic(epicForTest.getIdTicket());
        Subtask a = managerForTest.getIdSubtask(t.getIdTicket());
        Subtask b = managerForTest.getIdSubtask(tt.getIdTicket());

        assertTrue( a == null && b == null);
    }



    //создаём второй эпик и проверяем что они будут отличатся при сравнении
    @Test
    void testEquals() {
        Subtask t = new Subtask("Subtask","Сравниваем сабтаски","Создаём первый Subtask в эпике", Status.NEW, Duration.ofMinutes(35), LocalDateTime.of(2024, 6, 1, 11, 1, 33 ), epicForTest);
        managerForTest.createSubtask(t);
        Subtask tt = new Subtask("Subtask","Сравниваем сабтаски","Создаём второй Subtask в эпике", Status.DONE, Duration.ofMinutes(35), LocalDateTime.of(2024, 6, 1, 11, 1, 33 ), epicForTest);
        managerForTest.createSubtask(tt);

        assertNotEquals(t, tt);
    }

}