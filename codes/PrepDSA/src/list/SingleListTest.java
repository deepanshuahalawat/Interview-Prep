package list;

public class SingleListTest {
    public static void main(String[] args) {
        int[] arr = {1,2,3,4,5,6,7};
        Node head  = SingleList.getList(arr);
        SingleList.printList(head);
    }
}
