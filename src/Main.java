import controllers.TaskManager;
import tickets.Epic;
import tickets.Status;
import tickets.Subtask;
import tickets.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {

        System.out.println("Поехали !");
        TaskManager manager = new TaskManager();


        //Создайте две задачи.
        Task t1 = new Task("Task","Task1","Desc1", Status.NEW);
        manager.createTask(t1);
        Task t2 = new Task("Task","Task2","Desc2", Status.NEW);
        manager.createTask(t2);

        //Создайте также эпик с двумя подзадачами.
        Epic e1 = new Epic("Epic","Epic1","Desc3");
        manager.createEpic(e1);
        Subtask s1 = new Subtask("Subtask","Subtask1","Desc4", Status.NEW, e1);
        Subtask s2 = new Subtask("Subtask","Subtask2","Desc5", Status.NEW, e1);
        manager.createSubtask(s1);
        manager.createSubtask(s2);

        //Создайте эпик с одной подзадачей.
        Epic e2 = new Epic("Epic","Epic2","Desc6");
        manager.createEpic(e2);
        Subtask s3 = new Subtask("Subtask","Subtask3","Desc7", Status.NEW, e2);
        manager.createSubtask(s3);

        //Распечатайте списки эпиков, задач и подзадач через System.out.println(..).
        getTestTask(manager, 1); //проверка

        //Измените статусы созданных объектов, распечатайте их.
        // Проверьте, что статус задачи и подзадачи сохранился,
        // а статус эпика рассчитался по статусам подзадач.
        Subtask upds1 = new Subtask("Subtask","Subtask1 new","Desc4", Status.DONE, e1);
        Subtask upds2 = new Subtask("Subtask","Subtask2 new","Desc5", Status.DONE, e1);
        manager.updateSubtask(upds1, s1);
        manager.updateSubtask(upds2, s2);
        getTestTask(manager, 2); //проверка

        //И, наконец, попробуйте удалить одну из задач и один из эпиков.
        manager.killIdTask(t1.getIdTicket());
        manager.killIdEpic(e1.getIdTicket());
        getTestTask(manager, 3);
    }




    public static void getTestTask(TaskManager taskManager,int numberOfTest){
        ArrayList<Task> h = taskManager.getTasks();

        System.out.println("-".repeat(30) + "Номет теста: " + numberOfTest + "-".repeat(30));
        System.out.println("-".repeat(10) + "Список Task" + "-".repeat(10));

        for (Task o : h) { //цикл
            System.out.println("Тип тикета: " + o.getTypeTicket());
            System.out.println("Уникальный номер: " + o.getIdTicket());
            System.out.println("Статус: " + o.getStatusTicket());
            System.out.println("Название: " + o.getNameTicket());
            System.out.println("Описание: " + o.getDescTicket());

            System.out.println("-".repeat(5));
        } //цикл

        getTestEpic(taskManager);
    }

    public static void getTestEpic(TaskManager taskManager){
        ArrayList<Epic> h = taskManager.getEpics();

        System.out.println("-".repeat(10) + "Список Epic" + "-".repeat(10));

        for (Epic o : h) { //цикл
            System.out.println("Тип тикета: " + o.getTypeTicket());
            System.out.println("Уникальный номер: " + o.getIdTicket());
            System.out.println("Статус: " + o.getStatusTicket());
            System.out.println("Название: " + o.getNameTicket());
            System.out.println("Описание: " + o.getDescTicket());

            ArrayList<Subtask> childSubtasks = o.getChildSubtasks();
            for (Subtask ts: childSubtasks) {
                System.out.println("Дочерний субтакс имеет статус: " + ts.getStatusTicket());
            }

            System.out.println("-".repeat(5));
        } //цикл

        getTestSubtask(taskManager);
    }

    public static void getTestSubtask(TaskManager taskManager){
        ArrayList<Subtask> h = taskManager.getSubtasks();

        System.out.println("-".repeat(10) + "Список Subtask" + "-".repeat(10));

        for (Subtask o : h) { //цикл
            System.out.println("Тип тикета: " + o.getTypeTicket());
            System.out.println("Уникальный номер: " + o.getIdTicket());
            System.out.println("Статус: " + o.getStatusTicket());
            System.out.println("Название: " + o.getNameTicket());
            System.out.println("Описание: " + o.getDescTicket());

            System.out.println("-".repeat(5));
        } //цикл


    }

}

