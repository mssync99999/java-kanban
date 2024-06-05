package controllers;

import tickets.Epic;
import tickets.Subtask;
import tickets.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {


    //Хранение задач
    HashMap<Integer, Task> tasks = new HashMap<>();
    HashMap<Integer, Epic> epics = new HashMap<>();
    HashMap<Integer, Subtask> subtasks = new HashMap<>();
    int idTicket = 1; //инициализируем счётчик id
    private HistoryManager history = Managers.getDefaultHistory();

    //Констуктор
    public InMemoryTaskManager() {
    }


    //a. Получение списка всех задач.
    @Override
    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public ArrayList<Subtask> getSubtasks() {
        ArrayList<Subtask> tasks = new ArrayList<>(subtasks.values());
        return tasks;
    }

    @Override
    public ArrayList<Epic> getEpics() {
        ArrayList<Epic> tasks = new ArrayList<>(epics.values());
        return tasks;
    }

    //b. Удаление всех задач.
    @Override
    public void clearTasks() {
        tasks.clear();
    }

    @Override
    public void clearEpics() {
        epics.clear();
    }

    @Override
    public void clearSubTasks() {
        subtasks.clear();
    }


    //c. Получение по идентификатору.
    @Override
    public Task getIdTask(Integer t) {
        if (!tasks.containsKey(t)) {
            return null; //Тикет не найден
        }

        history.add(tasks.get(t)); //сохраним историю просмотров
        return tasks.get(t);
    }

    @Override
    public Epic getIdEpic(Integer t) {
        if (!epics.containsKey(t)) {
            return null; //Тикет не найден
        }

        history.add(epics.get(t)); //сохраним историю просмотров
        return epics.get(t);
    }

    @Override
    public Subtask getIdSubtask(Integer t) {
        if (!subtasks.containsKey(t)) {
            return null; //Тикет не найден
        }

        history.add(subtasks.get(t)); //сохраним историю просмотров
        return subtasks.get(t);
    }

    //d. Создание. Сам объект должен передаваться в качестве параметра.
    @Override
    public void createTask(Task o) {
        tasks.put(idTicket, o); //новый тикет в хэш
        o.setIdTicket(idTicket);
        idTicket++; //инкримент счётчика уникальных id
    }

    @Override
    public void createEpic(Epic o) {
        epics.put(idTicket, o); //новый тикет в хэш
        o.setIdTicket(idTicket);
        idTicket++; //инкримент счётчика уникальных id
    }

    @Override
    public void createSubtask(Subtask o) {//public void createTicket(Subtask o, Epic e)
        subtasks.put(idTicket, o); //новый тикет в хэш
        o.setIdTicket(idTicket);
        o.getParentEpic().addSubtask(o); //добавить подзадачу в список родительского Epic и пересчитать статус Epic
        idTicket++; //инкримент счётчика уникальных id
    }

    //e. Обновление. Новая версия объекта с верным идентификатором передаётся в виде параметра.
    @Override
    public void updateTask(Task upd, Task old) {
        //System.out.println(old.getIdTicket());
        upd.setIdTicket(old.getIdTicket());
        tasks.put(old.getIdTicket(), upd);
    }

    @Override
    public void updateEpic(Epic upd, Epic old) {
        //System.out.println(old.getIdTicket());
        upd.setIdTicket(old.getIdTicket());
        epics.put(old.getIdTicket(), upd);
    }

    @Override
    public void updateSubtask(Subtask upd, Subtask old) {
        //System.out.println(old.getIdTicket());
        upd.setIdTicket(old.getIdTicket());
        subtasks.put(old.getIdTicket(), upd);

        Epic parentEpic = old.getParentEpic();

        parentEpic.getChildSubtasks().remove(old);
        parentEpic.updateStatus(); //Обновление статуса эпиков на основе статусов субтасков

    }

    //f. Удаление по идентификатору.
    @Override
    public void killIdTask(Integer t) {
        if (!tasks.containsKey(t)) {
            return; //Тикет не найден
        }
        tasks.remove(t);
    }

    @Override
    public void killIdEpic(Integer t) {
        if (!epics.containsKey(t)) {
            return; //Тикет не найден
        }

        ArrayList<Subtask> childSubtasks = getSubtaskOfEpic(epics.get(t));
        for(Subtask s: childSubtasks){ //удаление субтаксов эпиков
            subtasks.remove(s.getIdTicket());
        }

        epics.remove(t);
    }

    @Override
    public void killIdSubtask(Integer t) {

        if (!subtasks.containsKey(t)) {
            return; //Тикет не найден
        }

        Subtask s = subtasks.get(t);
        s.getParentEpic().killSubtask(s); //подчищаем связанных с эпиком субтасков


        subtasks.remove(t);
    }

    //Дополнительные методы:
    //a. Получение списка всех подзадач определённого эпика.
    @Override
    public ArrayList<Subtask> getSubtaskOfEpic(Epic e) {
        if (e == null) return null;

        return e.getChildSubtasks();
    }

    //Работа с историей просмотров
    @Override
    public List<Task> getHistory() {
        return history.getHistory();
    }

}
