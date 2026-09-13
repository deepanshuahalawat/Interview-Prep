package sorting;

import java.util.Arrays;

public class MergeSort {
    public static void main(String[] args) {
        int[] arr = {1,4,2,6,9,3,6,7,2,4,5};
        mergeSort(arr,0,arr.length-1);
        System.out.println("\n");
        Arrays.stream(arr).forEach(e -> System.out.print(e+", "));
    }
    public static void mergeSort(int[] arr, int left, int right){
        if(right >left){
            int mid = left + (right - left)/2;
            mergeSort(arr,left,mid);
            mergeSort(arr,mid+1,right);
            merge(arr,left,mid,right);
        }
    }

    public static void merge(int[] arr, int start, int mid, int end){
        int leftArraySize = mid-start+1;
        int rightArraySize = end-mid;
        int[] left,right;
        left = new int[leftArraySize];
        right = new int[rightArraySize];

        //copy left array
        for(int i=start; i<=mid; i++){
            left[i-start] = arr[i];
        }
        //copy right array
        for(int i=mid+1; i<= end; i++){
            right[i-(mid+1)] = arr[i];
        }
        //merge part
        int lefti=0, righti=0;
        for(int i=start; i<=end; i++){
            if(lefti < leftArraySize && righti < rightArraySize){
                if(left[lefti] <= right[righti]){
                    arr[i] = left[lefti++];
                }else{
                    arr[i] = right[righti++];
                }
            }else if(lefti == leftArraySize){
                arr[i] = right[righti++];
            }else if(righti == rightArraySize){
                arr[i] = left[lefti++];
            }
        }
    }
}

