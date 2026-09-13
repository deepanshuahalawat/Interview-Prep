package list;

public class DoubleList {
    public static DNode head;
    public static DNode end;

    public static DNode getEnd(DNode node){
        DNode end=node;
        while(node != null){
            end = node;
            node = node.next;
        }
        return end;
    }

    public static DNode getList(int[] arr){
        DNode lastNode = null;
        for(int i=0; i< arr.length; i++){
            DNode node = new DNode(arr[i]);
            if(i ==0){
                head = node;
            }else{
                lastNode.next = node;
                node.prev = lastNode;
            }
            lastNode = node;
        }
        end = lastNode;
        return head;
    }

    public static DNode getADList(){
        int[] arr = {1,2,3,4,5,6,7};
        return getList(arr);
    }

    public static void printDList(DNode node){
        while(node != null){
            System.out.print(node.data+"\t");
            node = node.next;
        }
        System.out.println("");
    }

    public static void printDListRev(DNode end){
        while(end != null){
            System.out.print(end.data+"\t");
            end = end.prev;
        }
        System.out.println("");
    }

}
