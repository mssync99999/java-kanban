package controllers;

import tickets.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager{
    private List<Task> historyTask = new ArrayList<>();
    private static final int maxSizeHistoryList = 10; //предельный размер стека для истории

    @Override
    public void add(Task task){
    //...Если размер списка исчерпан, из него нужно удалить самый старый элемент (в начале списка)
        if (historyTask.size() == maxSizeHistoryList) { //достигнут предел ?

            historyTask.remove(0); //удаляем крайний индекс

        }

        historyTask.add(task);
    }


    @Override
    public List<Task> getHistory(){
    //...Следует создать его копию, передав коллекцию в конструктор ArrayList'a, либо же
        // можно воспользоваться статическим методом copyOf из List)
        List<Task> copyOfHistoryTask = new ArrayList<>(historyTask);
        return copyOfHistoryTask; //...последние 10 просмотренных задач
    }

}
