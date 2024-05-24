import tickets.Epic;
import tickets.Subtask;
import tickets.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {


    //Хранение задач
    HashMap<Integer, Object> tasks = new HashMap<>();
    int idTicket = 1; //инициализируем счётчик id

    //Констуктор
    public TaskManager() {
    }


    //a. Получение списка всех задач.
    public HashMap<Integer, Object> getFullTickets() {
        if (tasks.isEmpty()) {
            return null; //Тикеты не найден
        }

        return tasks;
    }


    //b. Удаление всех задач.
    public void killFullTickets() {
        tasks.clear();
    }


    //c. Получение по идентификатору.
    public Task getIdTicket(Integer t) {
        if (!tasks.containsKey(t)) {
            return null; //Тикет не найден
        }
        Task o = (Task) tasks.get(t);
        return o;
    }


    //d. Создание. Сам объект должен передаваться в качестве параметра.
    public void createTicket(Task o) {
        tasks.put(idTicket, o); //новый тикет в хэш
        o.setIdTicket(idTicket);
        idTicket++; //инкримент счётчика уникальных id
    }

    public void createTicket(Epic o) {
        tasks.put(idTicket, o); //новый тикет в хэш
        o.setIdTicket(idTicket);
        idTicket++; //инкримент счётчика уникальных id
    }

    public void createTicket(Subtask o) {//public void createTicket(Subtask o, Epic e)
        tasks.put(idTicket, o); //новый тикет в хэш
        o.setIdTicket(idTicket);

        idTicket++; //инкримент счётчика уникальных id
    }


    //e. Обновление. Новая версия объекта с верным идентификатором передаётся в виде параметра.
    public void updateTicket(Task upd, Task old) {
        System.out.println(old.getIdTicket());
        upd.setIdTicket(old.getIdTicket());
        tasks.put(old.getIdTicket(), upd);

        if (old.getTypeTicket() == "Subtask") { //удаление субтаксов из внутреннего аррайлист эпика
            Subtask st = (Subtask) old;
            Epic parentEpic = st.getParentEpic();

            parentEpic.getChildSubtasks().remove(old);
            parentEpic.updateStatus(); //Обновление статуса эпиков на основе статусов субтасков
        }
    }

    //f. Удаление по идентификатору.
    public void killIdTicket(Integer t) {

        if (!tasks.containsKey(t)) {
            return; //Тикет не найден
        }

        Task tx = (Task) tasks.get(t);
        if (tx.getTypeTicket() == "Epic") { //удаление субтаксов эпиков
            Epic te = (Epic) tx;
            ArrayList<Subtask> childSubtasks = te.getChildSubtasks();
            for (Subtask ts: childSubtasks) {
                tasks.remove(ts.getIdTicket());
            }

        } else if (tx.getTypeTicket() == "Subtask") {
            Subtask ts = (Subtask) tx;
            ts.getParentEpic().killSubtask(ts); //подчищаем связанных с эпиком субтасков
        }

        tasks.remove(t);
    }


    //Дополнительные методы:
    //a. Получение списка всех подзадач определённого эпика.
    public ArrayList<Subtask> getSubtaskOfEpic(Epic e) {
        if (e == null) return null;

        return e.getChildSubtasks();
    }



}
