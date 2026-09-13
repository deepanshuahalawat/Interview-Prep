package stack;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class LastGreaterElement {
    public static void main(String[] args) {
        int arr[] = {9,3,4,2,6,8,7,3,2};
        int[] lge = lastGreaterElement(arr);
        Arrays.stream(arr).forEach(e -> System.out.print(e+"\t"));
        System.out.println("");
        Arrays.stream(lge).forEach(e -> System.out.print(e+"\t"));
    }

    public static int[] lastGreaterElement(int[] arr){
        int[] lge = new int[arr.length];
        Deque<Integer> stack = new ArrayDeque<>();
        for(int i=0; i< arr.length; i++){
            if(stack.isEmpty()){
                lge[i] = arr[i];
                stack.push(arr[i]);
            }else{
                int element = stack.peek();
                while(element < arr[i]){
                    stack.pop();
                    element = stack.peek();
                }
                lge[i] = element;
                stack.push(arr[i]);
            }
        }


        return lge;
    }
}
