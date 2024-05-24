package tickets;

import java.util.Objects;

public class Subtask extends Task{

    private String typeTicket; // = "Subtask";
    private String nameTicket;
    private String descTicket;
    private Status statusTicket = Status.NEW;
    private Epic parentEpic; //Для каждой подзадачи известно, в рамках какого эпика она выполняется



    public Subtask(String nameTicket, String descTicket, Status statusTicket, Epic epic) {
        // сначала вызываем конструктор класса-родителя
        super(nameTicket, descTicket, statusTicket);
        // затем инициализируем новые поля
        this.typeTicket = "Subtask";
        this.nameTicket = nameTicket;
        this.descTicket = descTicket;
        this.statusTicket = statusTicket;
        addEpic(epic);
    }

    public void addEpic(Epic epic) {
        this.parentEpic = epic; //связывает себя с родительским эпиком
        this.parentEpic.addSubtask(this); //добавляет себя в ArrayList родительского эпик
    }

    public Epic getParentEpic() {
        return parentEpic;
    }

    @Override
    public String getTypeTicket() {
        return this.typeTicket;
    }

    //Также советуем применить знания о методах equals() и hashCode()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subtask subtask = (Subtask) o;
        return Objects.equals(typeTicket, subtask.typeTicket) && statusTicket == subtask.statusTicket && Objects.equals(parentEpic, subtask.parentEpic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeTicket, statusTicket, parentEpic);
    }
}
