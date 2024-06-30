package controllers;
import tickets.Task;
import java.util.Objects;

public class Node {
    private Node prev;
    private Task data;
    private Node next;

    public Node getPrev() {
        return prev;
    }

    public void setPrev(Node prev) {
        this.prev = prev;
    }

    public Task getData() {
        return data;
    }

    public void setData(Task data) {
        this.data = data;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }





    public Node(Task data) {
        this.prev = null;
        this.data = data;
        this.next = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(prev, node.prev) && Objects.equals(data, node.data) && Objects.equals(next, node.next);
    }

    @Override
    public int hashCode() {
        return Objects.hash(prev, data, next);
    }
}
