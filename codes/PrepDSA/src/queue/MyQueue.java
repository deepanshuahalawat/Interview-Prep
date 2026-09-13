package queue;
public class MyQueue {
    private int capacity;
    private int size;
    int[] arr;
    private int front=0, end=0;
    MyQueue(int capacity){
        this.capacity = capacity;
        arr = new int[capacity];
    }
    public boolean enQueue(int data){
        if(size >= capacity){
            return false;
        }

        return true;
    }
    public int deQueue(){
        if(size <= 0) {
            return -1;
        }
        int data = arr[end];
        return data;
    }
    public int size(){
        return size;
    }
    public int first(){
        if(size > 0){
            return 0;//arr[start];
        }
        return -1;
    }
    public int last(){
        if(size > 0 ){
            return arr[end];
        }
        return -1;
    }

}


