package queue;

public class TestMyQueue {
    public static void main(String[] args) {
        MyQueue queue = new MyQueue(5);
        queue.enQueue(1);
        queue.enQueue(2);
        queue.enQueue(3);
        queue.enQueue(4);
        queue.enQueue(5);
        queue.enQueue(6);

        for(int i=0; i< queue.size(); i++){
            System.out.print(queue.deQueue()+"\t");
        }
    }
}
