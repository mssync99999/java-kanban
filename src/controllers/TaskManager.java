package controllers;
import tickets.Epic;
import tickets.Subtask;
import tickets.Task;
import java.util.ArrayList;
import java.util.List;

public interface TaskManager {

    //a. Получение списка всех задач.
    ArrayList<Task> getTasks();

    ArrayList<Subtask> getSubtasks();

    ArrayList<Epic> getEpics();

    //b. Удаление всех задач.
    void clearTasks();

    void clearEpics();

    void clearSubTasks();

    //c. Получение по идентификатору.
    Task getIdTask(Integer t);

    Epic getIdEpic(Integer t);

    Subtask getIdSubtask(Integer t);

    //d. Создание. Сам объект должен передаваться в качестве параметра.
    void createTask(Task o);

    void createEpic(Epic o);

    void createSubtask(Subtask o);

    //e. Обновление. Новая версия объекта с верным идентификатором передаётся в виде параметра.
    void updateTask(Task upd, Task old);

    void updateEpic(Epic upd, Epic old);

    void updateSubtask(Subtask upd, Subtask old);

    //f. Удаление по идентификатору.
    void killIdTask(Integer t);

    void killIdEpic(Integer t);

    void killIdSubtask(Integer t);

    //Дополнительные методы:
    //a. Получение списка всех подзадач определённого эпика.
    ArrayList<Subtask> getSubtaskOfEpic(Epic e);

    //История просмотров задач - 10 последних задач
    List<Task> getHistory();

}
