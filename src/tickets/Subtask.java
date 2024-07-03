package tickets;

public class Subtask extends Task {
    private Epic parentEpic; //Для каждой подзадачи известно, в рамках какого эпика она выполняется

    public Subtask(String typeTicket, String nameTicket, String descTicket, Status statusTicket, Epic epic) {
        // сначала вызываем конструктор класса-родителя
        super(typeTicket, nameTicket, descTicket, statusTicket);
        // затем инициализируем новые поля
        addEpic(epic); //внести изменения в родительский эпик
    }

    public void addEpic(Epic epic) {
        this.parentEpic = epic; //связывает себя с родительским эпиком
        this.parentEpic.addSubtask(this); //добавляет себя в ArrayList родительского эпик
    }

    public Epic getParentEpic() {
        return parentEpic;
    }






}
