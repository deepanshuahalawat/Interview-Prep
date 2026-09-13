package list;

public class MergeSortedList {
    public static void main(String[] args) {
        Node head1 = SingleList.getAList();
        Node head2 = SingleList.getAList();
        SingleList.printList(head1);
        Node head = mergeTwoLists(head1, head2);
        SingleList.printList(head);
    }

    public static Node mergeTwoLists(Node head1, Node head2){
        if(head1 == null){
            return head1;
        }
        if(head2 == null){
            return head1;
        }
        Node curr1 = head1, curr2 = head2;
        Node prev = null;
        if(head1.data > head2.data){
            Node next = head2.next;
            head2.next = head1;
            head1 = head2;
            prev = head1;
            curr1 = head2;
            curr2 = next;
        }
        else{
            prev = head1;
            curr1 = head1.next;
        }

        while(curr1 != null && curr2 != null){
            if(curr2.data < curr1.data){
                Node next = curr2.next;

                prev.next = curr2;
                curr2.next = curr1;

                prev = curr2;
                curr2 = next;
            }else{
                prev = curr1;
                curr1 = curr1.next;
            }
        }
        if(curr1 == null){
            prev.next = curr2;
        }

        return head1;
    }

}
