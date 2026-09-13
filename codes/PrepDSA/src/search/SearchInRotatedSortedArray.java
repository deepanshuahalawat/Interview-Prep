package search;

public class SearchInRotatedSortedArray {
    public static void main(String[] args) {
        int[] arr = {10,11,12,14,15,16,3,4,5,6};
        System.out.println("Index: "+search(arr,14));
    }
    static int search(int[] arr, int k){
        int index = 0;
        int start = 0;
        int end = arr.length-1;
        int mid = (start+end)/2;
        while(start <= end){
            if(arr[mid] == k){
                return mid;
            }else if(k < arr[mid]){
                if(k < arr[start]){
                    start = mid+1;
                }else{
                    end = mid-1;
                }
            }else{
                start = mid+1;
            }
            mid = (start+end)/2;
        }
        return index;
    }
}
