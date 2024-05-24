package tickets;

import java.util.ArrayList;
import java.util.Objects;


public class Epic extends Task{
    private String typeTicket = "Epic";
    private Status statusTicket = Status.NEW;

    private ArrayList<Subtask> childSubtasks = new ArrayList<>();


    public Epic(String nameTicket, String descTicket, Status statusTicket) {
        // сначала вызываем конструктор класса-родителя
        super(nameTicket, descTicket, statusTicket);
        // затем инициализируем новые поля
    }

    public ArrayList<Subtask> getChildSubtasks() {
        return childSubtasks;
    }

    //запоминае список субтаксов, которые связаны с этим эпиков
    public void addSubtask(Subtask subtask) {
        childSubtasks.add(subtask);
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

        for (Subtask s: childSubtasks){

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
            statusTicket = Status.DONE;
        } else if (workCount == childSubtasks.size()) {
            statusTicket = Status.IN_PROGRESS;
        } else if (newCount == childSubtasks.size()) {
            statusTicket = Status.NEW;
        }


    }

    @Override
    public String getTypeTicket() {
        return this.typeTicket;
    }

    @Override
    public Status getStatusTicket() {
        return statusTicket;
    }


    //Также советуем применить знания о методах equals() и hashCode()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Epic epic = (Epic) o;
        return Objects.equals(typeTicket, epic.typeTicket) && statusTicket == epic.statusTicket && Objects.equals(childSubtasks, epic.childSubtasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeTicket, statusTicket, childSubtasks);
    }


}
