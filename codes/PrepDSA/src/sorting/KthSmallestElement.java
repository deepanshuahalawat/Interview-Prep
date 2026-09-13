package sorting;

public class KthSmallestElement {
    public static void main(String[] args) {
        int[] arr = {7,4,1,8,9,2,0,5,9};
        System.out.println("ele: "+kthSmallest(arr, 6));
        System.out.println("\n");
    }

    public static int kthSmallest(int[] arr, int k){
        int l =0, h = arr.length-1;
        while(l <= h){
            int p =  QuickSort.lomutoPartition(arr, l, h,h);
            if(p == k-1){
                return arr[p];
            }
            else if(p > k-1){
                h = p-1;
            }else{
                l = p+1;
            }
        }
        return -1;
    }
}
