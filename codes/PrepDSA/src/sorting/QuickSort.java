package sorting;

import java.util.Arrays;

public class QuickSort {
    public static void main(String[] args) {
        int[] arr = {7,4,1,3,8,9,2,0,5,9};
        //quickSortLomuto(arr, 0, arr.length-1);
        quickSortHoare(arr, 0, arr.length-1);
        //System.out.println("hoare "+hoarePartition(arr,0, arr.length-1));
        System.out.println("\n");
        Arrays.stream(arr).forEach(e -> System.out.print(e+", "));
    }
    public static void quickSortHoare(int[] arr, int start, int end){
        if(start < end){
            int partition = hoarePartition(arr, start, end);
            quickSortHoare(arr, start, partition);
            quickSortHoare(arr,partition+1, end);
        }
    }


    public static int hoarePartition(int[] arr,int start, int end){
        int i= start-1, j = end+1;
        int pivot = arr[start];
        while (true){
            do{
                i++;
            }while(arr[i] < pivot);

            do{
                j--;
            }while(arr[j] > pivot);
            if(i >= j){
                return j;
            }
            swap(arr, i,j);
        }
    }

    public static void quickSortLomuto(int[] arr, int start, int end){
        if(start < end){
            int p = lomutoPartition(arr,start,end, end);
            quickSortLomuto(arr, start, p-1 );
            quickSortLomuto(arr, p+1, end);
        }
    }

    public static int lomutoPartition(int[] arr,int start, int end, int p){
        int i=start-1, j=start;
        int pivot = arr[p];
        //take pivot to the end
        swap(arr, p, end);

        for(j=start; j< end; j++){
            if(arr[j] <= pivot){
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i+1, end);
        return i+1;
    }
    static void swap(int[] arr, int a, int b){
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

}
