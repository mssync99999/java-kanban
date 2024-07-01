package controllers;

import tickets.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    //private List<Task> historyTask = new ArrayList<>();
    //private static final int maxSizeHistoryList = 10; //предельный размер стека для истории
    private Map<Integer, Node> mapNode = new LinkedHashMap<>();
    private Node firstNode;
    private Node lastNode;

    @Override
    public void add(Task task) {
        if (task == null) return;

        remove(task.getIdTicket()); //вначале удалим
        linkLast(task); //затем добавим

    }

    @Override
    public void remove(int id) {
        Node node = mapNode.get(id);
        if (node == null) return;
        removeNode(node); //удаляем ноду
    }


    public void removeNode(Node node) {
        //удаляем таск из хэша
        mapNode.remove(node.getData().getIdTicket());

        //замена связей в нодах слева и справа
        Node nodeA = node.getPrev(); // A<->b<->c
        Node nodeC = node.getNext(); // a<->b<->C

        if (node.getPrev() != null) nodeA.setNext(nodeC); //теперь нода левая смотрит на правую

        if (node.getNext() != null) nodeC.setPrev(nodeA); //теперь нода права смотрит на левую

        //обновим поля в объекте класса
        if (lastNode.equals(node)) lastNode = lastNode.getPrev();
        if (firstNode.equals(node)) firstNode = firstNode.getNext();

    }

    //будет добавлять задачу в конец этого списка
    private void linkLast(Task task) {
        Node node = new Node(task);
        if (mapNode.isEmpty()) firstNode = node; //если это самый первый элемент в хэше

        mapNode.put(task.getIdTicket(), node);

        if (lastNode != null) {
            lastNode.setNext(node);
            node.setPrev(lastNode);
        }

        lastNode = node;
    }


    @Override
    public List<Task> getHistory() {
        return getTasks();
    }



//собирать все задачи из него в обычный ArrayList
    private List<Task> getTasks() {
        LinkedList<Task> list = new LinkedList<>();

        for (Node n: mapNode.values()) {
            list.add(n.getData());
        }


        return new ArrayList<>(list);
    }







}
