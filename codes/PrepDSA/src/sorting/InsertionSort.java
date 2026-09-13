package sorting;

import java.util.Arrays;

public class InsertionSort {
    public static void main(String[] args) {
        int[] arr = {1,5,7,3,2,9};
        Arrays.stream(arr).forEach(e -> System.out.print(e+", "));
        insertionSort(arr);
        System.out.println("\n");
        Arrays.stream(arr).forEach(e -> System.out.print(e+", "));

    }
    static void insertionSort(int[] arr){
        for(int i=1; i<arr.length;i++){
                int key  = arr[i];
                int j=i-1;
                while(j >=0 && arr[j] > key){
                    arr[j+1] = arr[j];
                    j--;
                }
                arr[j+1]  = key;
            }

    }
    public static void sort(int[] arr){
        for(int i=1; i< arr.length; i++){
            if(arr[i] < arr[i-1]){
                insert(arr, i);
            }
        }
    }
    static void insert(int[] arr, int i){
        System.out.println("Inserting: "+arr[i]);
        int j=0;
        while(arr[j] < arr[i]){
            j++;
        }
        int temp = arr[i];
        for(int k=i-1; k >=j; k--){
            arr[k+1] = arr[k];
        }
        arr[j] = temp;
    }
}
