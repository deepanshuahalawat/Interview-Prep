package list;

import java.util.HashMap;

public class DoubleListProgs {
    public static void main(String[] args) {
        DNode head = DoubleList.getADList();
        DoubleList.printDList(head);
        System.out.println("Reversing...");
        head = reverseList(head);
        DoubleList.printDList(head);
        DoubleList.printDListRev(DoubleList.getEnd(head));


        HashMap<Node, Node> map = new HashMap<>();
    }

    public static DNode reverseList(DNode head){
        DNode prev = null, current, next= null;
        current = head;
        if(current != null){
            next = current.next;
        }

        while(current != null){
            current.next  = prev;
            current.prev = next;

            prev = current;
            current = next;
            if(next != null){
                next = next.next;
            }
        }
        return prev;
    }
}
