package list;

public class SingleList {
    public static Node head;
    public static int size=0;

    private static  Node lastNode;
    public static Node getList(int[] arr){
        for(int i=0; i< arr.length; i++){
            Node node = new Node(arr[i]);
            if(i == 0){
                head = node;
            }else{
                lastNode.next = node;
            }
            lastNode = node;
            size++;
        }
        return head;
    }
    public static void printList(Node node){
        while(node != null){
            System.out.print(node.data+"\t");
            node = node.next;
        }
        System.out.println("");
    }
    public static Node getAList(){
        int[] arr = {1,2,3,4,5,6,7};
        return SingleList.getList(arr);
    }
}
