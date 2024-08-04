package controllers;

import exceptions.ManagerSaveException;
import tickets.Epic;
import tickets.Status;
import tickets.Subtask;
import tickets.Task;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

import java.time.Duration; //+
import java.time.LocalDateTime; //+

public class FileBackedTaskManager extends InMemoryTaskManager {
    private File fileTask;
    private static String headString = "IdTicket,TypeTicket,NameTicket,DescTicket,StatusTicket,duration,startTime,epicId";

    public FileBackedTaskManager(String fileTask) {
        this.fileTask = new File(fileTask);
    }

    //создайте статический метод, который будет восстанавливать данные менеджера из файла при запуске программы.
    public static FileBackedTaskManager loadFromFile(String fileTask)  {

        FileBackedTaskManager manager = new FileBackedTaskManager(fileTask);
        if (!manager.fileTask.exists()) {
            return manager;
          //  throw new IOException;

        }


        try (BufferedReader br = new BufferedReader(new FileReader(fileTask))) {


            while (br.ready()) {
                String tmp = br.readLine();
                if (FileBackedTaskManager.headString.equals(tmp)) continue;
                manager.fromString(tmp);

            }

        } catch (IOException e) {
            //throw new RuntimeException(e);
            throw new ManagerSaveException("Can't read form file: " + manager.fileTask.getName());
        }
        return manager;
    }


    //должен сохранять все задачи, подзадачи и эпики.
    public void save() /*throws ManagerSaveException*/ {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileTask))) {

            //вначале запишем заголовок
            String headString = this.headString; //"IdTicket,TypeTicket,NameTicket,DescTicket,StatusTicket,duration,startTime,epicId";
            bw.write(headString + "\n");

            //получаем список тасков и записываем в файл
            ArrayList<Task> t = super.getTasks();
            for (Task tmp: t) {
                bw.write(toString(tmp) + "\n");
            }

            //получаем список эпиков и записываем в файл
            ArrayList<Epic> e = super.getEpics();
            for (Task tmp: e) {
                bw.write(toString(tmp) + "\n");
            }

            //получаем список сабтасков и записываем в файл
            ArrayList<Subtask> st = super.getSubtasks();
            for (Task tmp: st) {
                bw.write(toString(tmp) + "\n");
            }


        } catch (IOException e) {
            throw new ManagerSaveException(e.getMessage());
        }

    }



    public String toString(Task task) {

        int durationMinutes = 0;
        if (task.getDuration() != null) durationMinutes = (int) task.getDuration().toMinutes();

        String s = task.getIdTicket() + "," + task.getTypeTicket() + "," + task.getNameTicket()
                + "," + task.getDescTicket() + "," + task.getStatusTicket()
                + "," + durationMinutes + "," + task.getStartTime(); //+ добавьте в методы сериализации/десериализации новые поля

        int epicId = 0;

        if ("Subtask".equals(task.getTypeTicket())) {
            epicId = ((Subtask) task).getParentEpic().getIdTicket();
        }

        s = s + "," + epicId;
        return s;
    }

    //получает на входе строку символов - на выходе создаёт объект тикет
    public Task fromString(String value) {
        String[] split = value.split(",");
        int idTicket = Integer.parseInt(split[0]);
        String typeTicket = split[1];
        String nameTicket = split[2];
        String descTicket = split[3];
        Status statusTicket = Status.valueOf(split[4]);
        Duration duration = Duration.ofMinutes(Integer.parseInt(split[5])); //+
        LocalDateTime startTime = LocalDateTime.parse(split[6]); //+
        int epicId = Integer.parseInt(split[7]);

        if ("Subtask".equals(typeTicket)) {
            Epic parentEpic = super.getIdEpic(epicId); //найдём родительский эпик
            Subtask tmp = new Subtask(typeTicket, nameTicket, descTicket, statusTicket, duration, startTime, parentEpic); //создаём объект
            super.setIdTicketManager(idTicket); //установим сквозной счётчик уникальных тикетов
            super.createSubtask(tmp); //регистрируем в коллекциях или хэшах
            return tmp;
        }

        if ("Task".equals(typeTicket)) {
            Task tmp = new Task(typeTicket, nameTicket, descTicket, statusTicket, duration, startTime); //создаём объект
            super.setIdTicketManager(idTicket); //установим сквозной счётчик уникальных тикетов
            super.createTask(tmp); //регистрируем в коллекциях или хэшах
            return tmp;
        }

        if ("Epic".equals(typeTicket)) {
            Epic tmp = new Epic(typeTicket, nameTicket, descTicket); //создаём объект
            super.setIdTicketManager(idTicket); //установим сквозной счётчик уникальных тикетов
            super.createEpic(tmp); //регистрируем в коллекциях или хэшах
            return tmp;
        }

        return null;
    }

    @Override
    public ArrayList<Task> getTasks() {
        ArrayList<Task> r = super.getTasks();
        save();
        return r;
    }

    @Override
    public ArrayList<Subtask> getSubtasks() {
        ArrayList<Subtask> r = super.getSubtasks();
        save();
        return r;
    }

    @Override
    public ArrayList<Epic> getEpics() {
        ArrayList<Epic> r = super.getEpics();
        save();
        return r;
    }

    @Override
    public void clearTasks() {
        super.clearTasks();
        save();
    }

    @Override
    public void clearEpics() {
        super.clearEpics();
        save();
    }

    @Override
    public void clearSubTasks() {
        super.clearSubTasks();
        save();
    }

    @Override
    public Task getIdTask(Integer t) {
        Task r = super.getIdTask(t);
        save();
        return r;
    }

    @Override
    public Epic getIdEpic(Integer t) {
        Epic r = super.getIdEpic(t);
        save();
        return r;
    }

    @Override
    public Subtask getIdSubtask(Integer t) {
        Subtask r = super.getIdSubtask(t);
        save();
        return r;
    }

    @Override
    public void createTask(Task o) {
        super.createTask(o);
        save();
    }

    @Override
    public void createEpic(Epic o) {
        super.createEpic(o);
        save();
    }

    @Override
    public void createSubtask(Subtask o) {
        super.createSubtask(o);
        save();
    }

    @Override
    public void updateTask(Task upd, Task old) {
        super.updateTask(upd, old);
        save();
    }

    @Override
    public void updateEpic(Epic upd, Epic old) {
        super.updateEpic(upd, old);
        save();
    }

    @Override
    public void updateSubtask(Subtask upd, Subtask old) {
        super.updateSubtask(upd, old);
        save();
    }

    @Override
    public void killIdTask(Integer t) {
        super.killIdTask(t);
        save();
    }

    @Override
    public void killIdEpic(Integer t) {
        super.killIdEpic(t);
        save();
    }

    @Override
    public void killIdSubtask(Integer t) {
        super.killIdSubtask(t);
        save();
    }



}
