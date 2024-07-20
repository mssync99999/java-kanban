import controllers.Managers;
import controllers.TaskManager;
import tickets.Epic;
import tickets.Status;
import tickets.Subtask;
import tickets.Task;

public class Main {

    public static int numberOfTest = 1; //для внутреннего тестирования

    public static void main(String[] args) {

        System.out.println("Поехали !");

        TaskManager manager = Managers.getDefault(); //включено чтение и сохранение на диск


        printAllTasks(manager, "Смотрим что загружено с диска");

        oldTestCase(manager); //проверка


    }

    public static void oldTestCase(TaskManager manager) {

        //Создайте две задачи
        Task t1 = new Task("Task","Task1","Desc1", Status.NEW);
        manager.createTask(t1);
        Task t2 = new Task("Task","Task2","Desc2", Status.NEW);
        manager.createTask(t2);

        //Создайте также эпик с тремя подзадачами
        Epic e1 = new Epic("Epic","Epic1","Desc3");
        manager.createEpic(e1);
        Subtask s1 = new Subtask("Subtask","Subtask1","Desc4", Status.NEW, e1);
        Subtask s2 = new Subtask("Subtask","Subtask2","Desc5", Status.NEW, e1);
        Subtask s3 = new Subtask("Subtask","Subtask3","Desc6", Status.NEW, e1);
        manager.createSubtask(s1);
        manager.createSubtask(s2);
        manager.createSubtask(s3);

        //Создайте эпик без подзадач.
        Epic e2 = new Epic("Epic","Epic2","Desc6");
        manager.createEpic(e2);

        //Распечатайте списки эпиков, задач и подзадач через System.out.println(..).
        printAllTasks(manager, "создали 1 таск, 1 эпик и 3 субтаск, 1 эпик без субтаск.");

        //имитация просмотров тикетов
        manager.getIdTask(t1.getIdTicket());
        manager.getIdTask(t2.getIdTicket());
        manager.getIdEpic(e1.getIdTicket());
        manager.getIdSubtask(s1.getIdTicket());
        manager.getIdSubtask(s2.getIdTicket());
        manager.getIdSubtask(s3.getIdTicket());
        manager.getIdEpic(e2.getIdTicket());
        printAllTasks(manager, "Выполнили просмотры тикетов");


        //Удалите задачу, которая есть в истории, и проверьте, что при печати она не будет выводиться..
        manager.killIdTask(t1.getIdTicket());
        manager.getIdEpic(e1.getIdTicket());
        manager.getIdEpic(e2.getIdTicket());
        printAllTasks(manager,"Удалите задачу, которая есть в истории, и проверьте, что при печати она не будет выводиться. Просмотрели все эпики");

        //Удалите эпик с тремя подзадачами и убедитесь, что из истории удалился как сам эпик, так и все его подзадачи
        manager.killIdEpic(e1.getIdTicket());

        manager.getIdTask(t2.getIdTicket());
        printAllTasks(manager,"Удалите эпик с тремя подзадачами и убедитесь, что из истории удалился как сам эпик, так и все его подзадачи. Просмотрели таск");


    }


    //новая версия внутреннего тестирования из спринта 5
    private static void printAllTasks(TaskManager manager,String descOfTest) {

        System.out.println(" ");
        System.out.println("-".repeat(5) + "Номет теста: " + Main.numberOfTest + ". Описание теста: " + descOfTest + "-".repeat(30));
        System.out.println(" ");
        System.out.println("Существуют Задачи:");
        for (Task task : manager.getTasks()) {
            System.out.println(task);
            //manager.getIdTask(task.getIdTicket()); //имитация просмотра тикета
        }
        System.out.println(" ");
        System.out.println("Существуют Эпики:");
        for (Epic epic : manager.getEpics()) {
            System.out.println(epic);
            //manager.getIdEpic(epic.getIdTicket()); //имитация просмотра эпика

            for (Task subtask : manager.getSubtaskOfEpic(epic)) {   //for (Task task : manager.getSubtaskOfEpic(epic.getIdEpic())) {
                System.out.println("-его подзадача-> " + subtask); //ArrayList<Subtask>
            }
        }
        System.out.println(" ");
        System.out.println("Существуют Подзадачи:");
        for (Task subtask : manager.getSubtasks()) {
            System.out.println(subtask);
            //manager.getIdSubtask(subtask.getIdTicket()); //имитация просмотра субтаска
        }

        System.out.println("История просмотров:");
        for (Task task : manager.getHistory()) {
            System.out.println(task);
        }

        Main.numberOfTest += 1;


    }
}

