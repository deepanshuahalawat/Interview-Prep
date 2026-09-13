package list;

public class SingleListProgs {
    public static void main(String[] args) {
        Node head = SingleList.getAList();
        SingleList.printList(head);
        head = reverseAList(head);
        SingleList.printList(head);
    }
    public static Node reverseAList(Node node){
        Node prev=null, current, next=null;
        current = node;
        if(current != null){
            next = current.next;
        }
        while(current != null){
            current.next = prev;
            prev = current;
            current = next;
            if(next != null) {
                next = next.next;
            }
        }
        return prev;
    }
}
