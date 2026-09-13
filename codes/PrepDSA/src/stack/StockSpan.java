package stack;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class StockSpan {
    public static void main(String[] args) {
        int[] arr = {3, 7, 7, 6, 1, 2, 6, 3, 7, 5};
        List<Integer> ans = calculateSpan(arr);
        System.out.println(ans);
    }
    public static ArrayList<Integer> calculateSpan(int[] arr) {
        // code here
        ArrayList<Integer> stock = new ArrayList<>();
        Deque<Integer> stack = new ArrayDeque<>();

        for(int i=0; i< arr.length; i++){
            if(stack.isEmpty()){
                stock.add(1);
                stack.push(i);
            }else{
                int element = arr[stack.peek()];
                while(element < arr[i]){
                    stack.pop();

                    element = arr[stack.peek()];
                }
                int span = i-stack.peek();
                if(stock.isEmpty()){
                    span = i+1;
                }
                stock.add(span);

                stack.push(i);
            }

        }



        return stock;
    }
}
