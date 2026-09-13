package sorting;

import java.util.Arrays;

public class BubbleSort {
    public static void main(String[] args) {
        int[] arr = {2,3,5,6,7,8,5,3,4,7,9};
        Arrays.stream(arr).forEach(e -> System.out.print(e+", "));
        sort(arr);
        System.out.println("\n");
        Arrays.stream(arr).forEach(e -> System.out.print(e+", "));
    }
    public static void sort(int[] arr){
        for(int i=0; i< arr.length-1; i++){
            for(int j=i+1; j<arr.length; j++){
                if(arr[i] > arr[j]){
                    int temp = arr[j];
                    arr[j] = arr[i];
                    arr[i] = temp;
                }
            }
        }
    }
}
