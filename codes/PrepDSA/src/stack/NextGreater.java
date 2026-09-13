package stack;

import java.util.*;
import java.util.concurrent.DelayQueue;

public class NextGreater {
    public static void main(String[] args) {
        int[] arr = {5, 15, 10, 8, 6,12 ,9, 18};
        int[] ng = nextGreater(arr);
        Arrays.stream(arr).forEach(e -> System.out.print(e+"\t"));
        System.out.println("");
        Arrays.stream(ng).forEach(e -> System.out.print(e+"\t"));
    }

    static int[] nextGreater(int[] arr){
        int[] ng = new int[arr.length];
        Deque<Integer> stack = new ArrayDeque<Integer>();
        for(int i=arr.length-1; i>=0; i--){
            while(!stack.isEmpty() && stack.peek() < arr[i]){
                stack.pop();
            }
            if(stack.isEmpty()){
                ng[i] = -1;
            }else{
                ng[i] = stack.peek();
            }
            stack.push(arr[i]);
        }
        return ng;
    }

}
