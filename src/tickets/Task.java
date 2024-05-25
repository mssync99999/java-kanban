package tickets;

public class Task {


    private int idTicket;
    private String typeTicket;// = "Task1";
    private String nameTicket;
    private String descTicket;
    private Status statusTicket;// = Status.NEW;

    public Task(String typeTicket, String nameTicket, String descTicket, Status statusTicket) {
        this.typeTicket = typeTicket; //
        this.nameTicket = nameTicket;
        this.descTicket = descTicket;
        this.statusTicket = statusTicket;

    }


    public void setStatusTicket(Status statusTicket) {
        this.statusTicket = statusTicket;
    }

    public void setIdTicket(int idTicket) {
        this.idTicket = idTicket;
    }

    public int getIdTicket() {
        return idTicket;
    }

    public String getTypeTicket() {
        return this.typeTicket;
    }

    public String getNameTicket() {
        return nameTicket;
    }

    public String getDescTicket() {
        return descTicket;
    }

    public Status getStatusTicket() {
        return statusTicket;
    }

}
