package list;

public class DNode {
    public int data;
    public DNode next;
    public DNode prev;
    public DNode(int data){
        this.data = data;
    }
    public DNode(int data, DNode next, DNode prev){
        this.data = data;
        this.next = next;
        this.prev = prev;
    }
}
