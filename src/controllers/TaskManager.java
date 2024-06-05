package controllers;

import tickets.Epic;
import tickets.Subtask;
import tickets.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {

    //a. Получение списка всех задач.
    public ArrayList<Task> getTasks();

    public ArrayList<Subtask> getSubtasks();

    public ArrayList<Epic> getEpics() ;

    //b. Удаление всех задач.
    public void clearTasks();

    public void clearEpics();

    public void clearSubTasks();

    //c. Получение по идентификатору.
    public Task getIdTask(Integer t) ;

    public Epic getIdEpic(Integer t);

    public Subtask getIdSubtask(Integer t);

    //d. Создание. Сам объект должен передаваться в качестве параметра.
    public void createTask(Task o);

    public void createEpic(Epic o);

    public void createSubtask(Subtask o);

    //e. Обновление. Новая версия объекта с верным идентификатором передаётся в виде параметра.
    public void updateTask(Task upd, Task old);

    public void updateEpic(Epic upd, Epic old);

    public void updateSubtask(Subtask upd, Subtask old);

    //f. Удаление по идентификатору.
    public void killIdTask(Integer t);

    public void killIdEpic(Integer t);

    public void killIdSubtask(Integer t);

    //Дополнительные методы:
    //a. Получение списка всех подзадач определённого эпика.
    public ArrayList<Subtask> getSubtaskOfEpic(Epic e);

    //История просмотров задач - 10 последних задач
    public List<Task> getHistory();

}
