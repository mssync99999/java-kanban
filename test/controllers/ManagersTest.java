package controllers;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ManagersTest {

    //убедитесь, что утилитарный класс всегда возвращает проинициализированные
    // и готовые к работе экземпляры менеджеров;
    @Test
    void getDefault() {
        TaskManager managerForTest = Managers.getDefault();

        assertTrue((managerForTest instanceof TaskManager) && managerForTest != null);
    }


    @Test
    void getDefaultHistory() {
        HistoryManager historyManagerForTest = Managers.getDefaultHistory();

        assertTrue((historyManagerForTest instanceof HistoryManager) && historyManagerForTest != null);
    }
}