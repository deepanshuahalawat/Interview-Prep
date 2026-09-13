package stack;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class HistogramMaxArea {
    public static void main(String[] args) {
        int[] arr = {2,1,5,6,2,3};
        int ans = Solution.largestRectangleArea(arr);
        System.out.println("largest: "+ans);
    }
    public static int[] nse(int[] arr){
        int[] nse = new int[arr.length];
        Deque<Integer> stack = new ArrayDeque<>();
        for(int i= arr.length-1; i>=0; i--){
            while(!stack.isEmpty() && arr[i] < arr[stack.peek()]){
                stack.pop();
            }
            if(stack.isEmpty()){
                nse[i] = -1;
            }else{
                nse[i] = stack.peek();
            }
            stack.push(i);
        }
        return nse;
    }
    public static int[] pse(int[] arr){
        int[] pse = new int[arr.length];
        Deque<Integer> stack = new ArrayDeque<>();
        for(int i=0; i< arr.length; i++){
            while(!stack.isEmpty() && arr[i] < arr[stack.peek()]){
                stack.pop();
            }
            pse[i] = stack.isEmpty() ? -1:stack.peek();
            stack.push(i);
        }
        return pse;
    }
}

class Solution {
    public static int largestRectangleArea(int[] heights) {
        int maxArea=0;
        Deque<Integer> stack = new ArrayDeque<>();
        for(int i=0; i< heights.length; i++){
            while(!stack.isEmpty() && heights[i] < heights[stack.peek()]){
                int top = stack.pop();
                int width = stack.isEmpty() ? i:(i- stack.peek()-1);
                int area = width * heights[top];
                System.out.println(area);
                maxArea = maxArea > area ? maxArea:area;
            }
            stack.push(i);
        }
        while(!stack.isEmpty()){
            int top = stack.pop();
            int width = stack.isEmpty() ? heights.length :(heights.length - stack.peek() -1);
            int area = width*heights[top];
            System.out.println(area);
            maxArea = maxArea > area ? maxArea:area;
        }
        return maxArea;
    }
}
