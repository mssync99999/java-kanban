package tickets;

import java.util.ArrayList;
//import java.util.Objects;


public class Epic extends Task {
    private ArrayList<Subtask> childSubtasks = new ArrayList<>();


    public Epic(String typeTicket, String nameTicket, String descTicket) {
        // сначала вызываем конструктор класса-родителя
        super(typeTicket, nameTicket, descTicket, Status.NEW);//вызываем родителя с дефолтным Status.NEW

        // затем инициализируем новые поля
    }

    public ArrayList<Subtask> getChildSubtasks() {
        return childSubtasks;
    }

    //запоминает список субтаксов, которые связаны с этим эпиков
    public void addSubtask(Subtask subtask) {
        //System.out.println(childSubtasks.contains(subtask));
        if (childSubtasks.contains(subtask) == false) {
            childSubtasks.add(subtask);
        }

        updateStatus(); //обновление статуса эпика

    }

    //удаляем связи с субтасками
    public void killSubtask(Subtask subtask) {
        childSubtasks.remove(subtask);
        updateStatus(); //обновление статуса эпика
    }

    //Обновление статуса эпиков на основе статусов субтасков
    public void updateStatus() {
        //обновляем статус этого эпика в зависимости от статусов субтасков
        int newCount = 0;
        int workCount = 0;
        int doneCount = 0;

        for (Subtask s: childSubtasks) {

            //contains(E e)
            if (s.getStatusTicket() == Status.NEW) {
                newCount++;
            } else if (s.getStatusTicket() == Status.IN_PROGRESS) {
                workCount++;
            } else {
                doneCount++;
            }

        }

        //присвоение общего статуса субтасков родительскому эпику
        if (doneCount == childSubtasks.size()) {
            //statusTicket = Status.DONE;
            setStatusTicket(Status.DONE);
        } else if (workCount == childSubtasks.size()) {
            //statusTicket = Status.IN_PROGRESS;
            setStatusTicket(Status.IN_PROGRESS);
        } else if (newCount == childSubtasks.size()) {
            //statusTicket = Status.NEW;
            setStatusTicket(Status.NEW);
        }


    }


}
