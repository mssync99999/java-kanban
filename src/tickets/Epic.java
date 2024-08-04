package tickets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;


public class Epic extends Task {
    private ArrayList<Subtask> childSubtasks = new ArrayList<>();
    private LocalDateTime endTime; //+Для реализации getEndTime() удобно добавить поле endTime в Epic и рассчитать его вместе с другими полями.

    public Epic(String typeTicket, String nameTicket, String descTicket) {
        // сначала вызываем конструктор класса-родителя
        super(typeTicket, nameTicket, descTicket, Status.NEW, Duration.ofMinutes(0), LocalDateTime.of(2099, 5, 15, 11, 21, 41));//вызываем родителя с дефолтным Status.NEW

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

        int durationInMinutes = 0;
        for (Subtask s: childSubtasks) {

            //+Продолжительность эпика — сумма продолжительностей всех его подзадач.
            durationInMinutes += s.getDuration().toMinutes();

            //+Время начала — дата старта самой ранней подзадачи
            if (getStartTime() == null || getStartTime().isAfter(s.getStartTime())) {
                setStartTime(s.getStartTime());
            }

            //LocalDateTime endTimeSubtask = getEndTime();
            //+время завершения — время окончания самой поздней из подзадач
            if (getEndTime() == null || getEndTime().isBefore(s.getEndTime())) {
                endTime = s.getEndTime();
            }

            //contains(E e)
            if (s.getStatusTicket() == Status.NEW) {
                newCount++;
            } else if (s.getStatusTicket() == Status.IN_PROGRESS) {
                workCount++;
            } else {
                doneCount++;
            }

        }

        //+Продолжительность эпика — сумма продолжительностей всех его подзадач.
        setDuration(Duration.of(durationInMinutes, ChronoUnit.MINUTES));

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

    //+дата и время завершения задачи
    @Override
    public LocalDateTime getEndTime() {
        return this.endTime;
    }
}
