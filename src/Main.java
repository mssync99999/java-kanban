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
        Task t1 = new Task("Task1","Desc1", Status.NEW);
        manager.createTicket(t1);
        Task t2 = new Task("Task2","Desc2", Status.NEW);
        manager.createTicket(t2);

        //Создайте также эпик с двумя подзадачами.
        Epic e1 = new Epic("Epic1","Desc3", Status.NEW);
        manager.createTicket(e1);
        Subtask s1 = new Subtask("Subtask1","Desc4", Status.NEW, e1);
        Subtask s2 = new Subtask("Subtask2","Desc5", Status.NEW, e1);
        manager.createTicket(s1);
        manager.createTicket(s2);

        //Создайте эпик с одной подзадачей.
        Epic e2 = new Epic("Epic2","Desc6", Status.NEW);
        manager.createTicket(e2);
        Subtask s3 = new Subtask("Subtask3","Desc7", Status.NEW, e2);
        manager.createTicket(s3);

        //Распечатайте списки эпиков, задач и подзадач через System.out.println(..).
        getTest(manager); //проверка

        //Измените статусы созданных объектов, распечатайте их.
        // Проверьте, что статус задачи и подзадачи сохранился,
        // а статус эпика рассчитался по статусам подзадач.
        Subtask upds1 = new Subtask("Subtask1 new","Desc4", Status.DONE, e1);
        Subtask upds2 = new Subtask("Subtask2 new","Desc5", Status.DONE, e1);
        manager.updateTicket(upds1, s1);
        manager.updateTicket(upds2, s2);
        getTest(manager); //проверка

        //И, наконец, попробуйте удалить одну из задач и один из эпиков.
        manager.killIdTicket(t1.getIdTicket());
        manager.killIdTicket(e1.getIdTicket());
        getTest(manager);
    }




    public static void getTest(TaskManager taskManager){
        HashMap<Integer, Object> h = taskManager.getFullTickets();
        ///String nameClass;
        System.out.println("-".repeat(30));

        for (Integer i : h.keySet()) { //цикл

            Task o = (Task) h.get(i); //nameClass = h.get(i).getClass().getSimpleName();



            System.out.println("Тип тикета: " + o.getTypeTicket());
            System.out.println("Уникальный номер: " + o.getIdTicket());
            if (o.getTypeTicket() == "Epic") { //для эпика необходима другая реализация
                Epic oo = (Epic) h.get(i);
                System.out.println("Статус_эпика: " + oo.getStatusTicket());
            } else {
                System.out.println("Статус: " + o.getStatusTicket());
            }

            System.out.println("Название: " + o.getNameTicket());
            System.out.println("Описание: " + o.getDescTicket());


            if (o.getTypeTicket() == "Epic") {
                Epic te = (Epic) o;

                ArrayList<Subtask> childSubtasks = te.getChildSubtasks();
                for (Subtask ts: childSubtasks) {
                    System.out.println("Дочерний субтакс имеет статус: " + ts.getStatusTicket());
                }
            }

            System.out.println("-".repeat(10));
        } //цикл
        System.out.println("-".repeat(30));
    }

}

