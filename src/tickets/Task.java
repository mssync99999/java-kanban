package tickets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Task  implements Comparable<Task> {


    private int idTicket;
    private String typeTicket;// = "Task1";
    private String nameTicket;
    private String descTicket;
    private Status statusTicket;// = Status.NEW;
    private Duration duration; // +продолжительность задачи, оценка того, сколько времени она займёт в минутах
    private LocalDateTime startTime; // +дата и время, когда предполагается приступить к выполнению задачи



    public Task(String typeTicket, String nameTicket, String descTicket, Status statusTicket, Duration duration, LocalDateTime startTime) {
        this.typeTicket = typeTicket; //
        this.nameTicket = nameTicket;
        this.descTicket = descTicket;
        this.statusTicket = statusTicket;
        this.startTime = startTime; //+
        this.duration = duration; //+
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    //+дата и время завершения задачи
    public LocalDateTime getEndTime() {
        if (startTime == null) {
            return null;
        }
        return startTime.plus(duration);
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    //+Проверяем пересечения с помощью Stream API
    /*
    public boolean isIntersection(Task task) {

        if (task == null || task.getStartTime() == null || this.getStartTime() == null) {
            return false;
        }
        return !this.getEndTime().isBefore(task.getStartTime()) && !this.getStartTime().isAfter(task.getEndTime());


    }
*/

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

    @Override
    public int compareTo(Task o) {
        LocalDateTime thisDT = LocalDateTime.MAX;
        LocalDateTime otherDT = LocalDateTime.MAX;

        if (this.getStartTime() != null) thisDT = this.getStartTime();
        if (o.getStartTime() != null) otherDT = o.getStartTime();


        return thisDT .compareTo(otherDT);
    }
}
