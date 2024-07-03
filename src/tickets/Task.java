package tickets;
import java.util.Objects;

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

    //Также советуем применить знания о методах equals() и hashCode()
    @Override
    public String toString() {
        return "Task{" +
                "idTicket=" + idTicket +
                ", typeTicket='" + typeTicket + '\'' +
                ", nameTicket='" + nameTicket + '\'' +
                ", descTicket='" + descTicket + '\'' +
                ", statusTicket=" + statusTicket +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return idTicket == task.idTicket && Objects.equals(typeTicket, task.typeTicket) && Objects.equals(nameTicket, task.nameTicket) && Objects.equals(descTicket, task.descTicket) && statusTicket == task.statusTicket;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTicket, typeTicket, nameTicket, descTicket, statusTicket);
    }
}
